package com.sujin.sjpay.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.entity.TabData;
import com.sujin.sjpay.fragment.BaseFragment;
import com.sujin.sjpay.fragment.HomeFragment;
import com.sujin.sjpay.fragment.MyFragment;
import com.sujin.sjpay.fragment.PayFragment;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.MyInfoResponse;
import com.sujin.sjpay.util.CheckNetWorkUtil;
import com.sujin.sjpay.util.DownloadUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TabLayoutView;
import com.sujin.sjpay.view.dialog.UpdateDialog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements TabLayoutView.OnTabLayoutClickListener{
    @BindView(R.id.tabLayout)
    TabLayoutView tabLayout;

    private ArrayList<TabData> tabData;
    private FragmentManager manager;
    private BaseFragment oldFragment;
    private static String curreentTag;
    private UpdateDialog updateDialog;

    private boolean isExit = false;
    private boolean appVersion = false;
    private String versionCode;
    private String des;
    private String appUrl;
    private int isMustUpdate;
    private int isRealState;

    Handler exitHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences spVersion = getSharedPreferences(AppConstants.SP_APP_VERSION, Context.MODE_PRIVATE);
        appVersion = spVersion.getBoolean(AppConstants.SP_APP_VERSION, false);
        versionCode = spVersion.getString(AppConstants.SP_APP_VERSION_CODE, "");
        des = spVersion.getString(AppConstants.SP_APP_DES, "");
        appUrl = spVersion.getString(AppConstants.SP_APP_URL, "");
        isMustUpdate = spVersion.getInt(AppConstants.SP_APP_IS_MUST_UPDATE, 1);
        initView();

        if (savedInstanceState != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            @SuppressLint("RestrictedApi") List<Fragment> fragments = manager.getFragments();
            for (int i = 0; i < fragments.size(); i++) {
                transaction.hide(fragments.get(i));
            }
            transaction.commit();
        }
        selectTab(AppConstants.INTENT_KEY_TAB_TAG);

        SharedPreferences spUserInfo = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE);
        String userId = spUserInfo.getString(AppConstants.SP_DATA_USER_ID, "");
        getMyInfo(userId);
    }

    @Override
    protected void initView() {
        manager = getSupportFragmentManager();
        tabData = new ArrayList<>();
        tabData.add(new TabData(AppConstants.TAB_TAG_HOME, AppConstants.TAB_NAME_HOME, R.drawable.icon_home, R.drawable.icon_home_se));
        tabData.add(new TabData(AppConstants.TAB_TAG_PAY, AppConstants.TAB_NAME_PAY, R.drawable.icon_pay, R.drawable.icon_pay_se));
        tabData.add(new TabData(AppConstants.TAB_TAG_MY, AppConstants.TAB_NAME_MY, R.drawable.icon_my, R.drawable.icon_my_se));

        tabLayout.initTabView(tabData);
        tabLayout.setOnTabLayoutClickListener(this);

        if (appVersion) {
            showUpdateDialog();
        }
    }

    /**
     * 显示升级弹窗
     */
    private void showUpdateDialog() {
        updateDialog = new UpdateDialog(MainActivity.this, des);
        updateDialog.setCancelable(false);
        updateDialog.setClicklistener(new UpdateDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {

                AndPermission.with(MainActivity.this)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                downloadApp();
                                if (updateDialog != null) {
                                    updateDialog.dismiss();
                                }
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.show("拒绝访问将不能更新");
                    }
                }).start();
            }

            @Override
            public void doCancel() {
                if (updateDialog != null) {
                    if (isMustUpdate == 1) {
                        exitApp();
                    } else {
                        updateDialog.dismiss();
                    }
                }
            }
        });
        if (updateDialog != null) {
            updateDialog.show();
        }
    }

    /**
     * 下载app
     */
    private void downloadApp() {
        DownloadUtil downloadUtil = new DownloadUtil();
        if (isMustUpdate == 1) {
            downloadUtil.download(MainActivity.this, appUrl, "", false);
        } else {
            downloadUtil.download(MainActivity.this, appUrl, "", true);
        }
    }

    @Override
    public boolean onTabLayoutClick(TabData tabData) {
        changeFragment(tabData);
        return true;
    }

    public void changeFragment(TabData data) {
        if (data == null)
            return;

        FragmentTransaction transaction = manager.beginTransaction();
        BaseFragment newFragment = (BaseFragment) manager.findFragmentByTag(data.getTag());
        if (oldFragment != null) {
            transaction.hide(oldFragment);
            oldFragment.onInvisible();
        }
        if (newFragment == null) {
            newFragment = createFragment(data);
            transaction.add(R.id.fl_fragment_container, newFragment, data.getTag());
            newFragment.setVisible(true);
        } else {
            transaction.show(newFragment);
            newFragment.onVisible();
        }
        try {
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.commitAllowingStateLoss();
        }
        oldFragment = newFragment;
        curreentTag = data.getTag();
    }

    private BaseFragment createFragment(TabData tabData) {
        String tag = tabData.getTag();
        if (TextUtils.equals(tag, AppConstants.TAB_TAG_HOME)) {
            return new HomeFragment();
        } else if (TextUtils.equals(tag, AppConstants.TAB_TAG_PAY)) {
            return new PayFragment();
        } else {
            return new MyFragment();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tag = curreentTag;
        if (intent != null) {
            tag = intent.getStringExtra(AppConstants.INTENT_KEY_TAB_TAG);
        }
        if (TextUtils.isEmpty(tag)) {
            tag = AppConstants.TAB_TAG_HOME;
        }
        selectTab(tag);
    }

    public void selectTab(String tag) {
        if (!TextUtils.isEmpty(tag) && tabData != null && tabData.size() > 0) {
            for (int i = 0; i < tabData.size(); i++) {
                if (TextUtils.equals(tag, tabData.get(i).getTag())) {
                    selectTab(i);
                    return;
                }
            }
        }
        selectTab(0);
    }

    private void selectTab(int i) {
        if (tabData != null && tabData.size() > 0 && i < tabData.size()) {
            if (onTabLayoutClick(tabData.get(i))) {
                tabLayout.selectTab(i);
            }
        }
    }

    private void getMyInfo(String userId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getSingleInfo, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Single, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---"  + md5);
        request(0, request, httpListener, md5, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    MyInfoResponse myInfoResponse = getGson().fromJson(registerJson, MyInfoResponse.class);
                    if (TextUtils.equals(myInfoResponse.getBackStatus(), "0")) {
                        MyInfoResponse.MyInfo data = myInfoResponse.getData();
                        isRealState = data.getIsRealState();
                        SharedPreferences.Editor spUserInfo = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).edit();
                        spUserInfo.putInt(AppConstants.SP_DATA_IS_REAL_STATE, isRealState);
                        spUserInfo.commit();
                        SJApplication.getInstance().setUser(data);
                    }else {
                        ToastUtil.show(myInfoResponse.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();

            LogUtils.d("SJHttp", json);
        }
    };

    /**
     * 双击返回键退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.show("再按一次退出程序");
            exitHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            exitApp();
        }
    }

    public void exitApp() {
        SJApplication.getInstance().removeALLActivity_();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        CheckNetWorkUtil.getInstance().notRun();
        finish();
    }
}
