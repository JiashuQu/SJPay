package com.sujin.sjpay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.MyInfoResponse;
import com.sujin.sjpay.protocol.VersionResponse;
import com.sujin.sjpay.util.AppVersionUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by QJS on 2016/7/26.
 */
public class OpenActivity extends BaseActivity {

    private static final String TAG = "OpenActivity";// 手势密码
    @BindView(R.id.iv_open)
    ImageView ivOpen;
    @BindView(R.id.tv_open_jump_ad)
    TextView tvOpenJumpAd;

    private PrepareTask prepareTask;
    private TimeCount time = null;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private boolean version;
    private String localVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        ButterKnife.bind(this);
//        checkIsFirstOpenAndUpdate();
//        checkVersion();
        initView();
//        startCount();
        getVersion();
    }

    private void getVersion() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getVersion, RequestMethod.GET);
        String md5 = StringUtil.MD5(ApiConstants.Version, "", ApiConstants.API_CONFIG);
//        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---"  + md5);
        request(0, request, httpListener, md5, false, false);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            prepareTask = new PrepareTask();
            prepareTask.execute();
            switch (what) {
                case 0:
                    String versionJson = response.get();
                    VersionResponse versionResponse = getGson().fromJson(versionJson, VersionResponse.class);
                    if (versionResponse.getBackStatus() == 0) {
                        localVersion = AppVersionUtil.getAppVersion(OpenActivity.this);
                        VersionResponse.DataBean data = versionResponse.getData();
                        String netVersion = data.getVersion();
                        boolean version = AppVersionUtil.compareAppVersion(netVersion, localVersion);
                        SharedPreferences spVersion = getSharedPreferences(AppConstants.SP_APP_VERSION, Context.MODE_PRIVATE);
                        SharedPreferences.Editor versionEdit = spVersion.edit();
                        versionEdit.putBoolean(AppConstants.SP_APP_VERSION, version);
                        versionEdit.putString(AppConstants.SP_APP_VERSION_CODE, netVersion);
                        versionEdit.putString(AppConstants.SP_APP_DES, data.getUpdate());
                        versionEdit.putString(AppConstants.SP_APP_URL, data.getDownload());
                        versionEdit.putInt(AppConstants.SP_APP_IS_MUST_UPDATE, data.getIsupgrade());
                        versionEdit.commit();
                    }else {
                        ToastUtil.show(versionResponse.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            prepareTask = new PrepareTask();
            prepareTask.execute();
            String json = response.get();

            LogUtils.d("SJHttp", json + "");
        }
    };



    private void checkIsFirstOpenAndUpdate() {
        localVersion = AppVersionUtil.getAppVersion(OpenActivity.this);
        sp = getSharedPreferences("first_open_and_update", Context.MODE_PRIVATE);
        edit = sp.edit();
        //如果is_first_show = false，说明是第一次启动app，则去请求广告页，否则跳转到引导页
        LogUtils.d(TAG, "is_first_show = " + sp.getBoolean("is_first_show", false));
        if(sp.getBoolean("is_first_show", false)) {
            checkNetwork();
            edit.putBoolean("is_first_show", false);
            edit.commit();
        }else {
            edit.putBoolean("is_first_update", true);
            edit.putBoolean("is_first_show", true);
            edit.commit();
        }
    }

    @Override
    protected void initView() {
//        tvOpenJumpAd.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.tv_open_jump_ad)
    public void onClick() {
        if (time != null) {
            time.cancel();
        }
        jumpToMain();
    }

    /**
     * 检查手机网络
     */
    private void checkNetwork() {
        requestOpenAd();
    }

    /**
     * 检测版本
     */
    private void checkVersion() {

    }

    /**
     * 请求银行卡Icon
     */
    private void downloadBankIcon() {
//        DownloadUtil downloadUtil = new DownloadUtil();
//        downloadUtil.download(ApiHostUtil.BANK_ICON_VERSION_HOST, FileUtil.getRootPath().getAbsolutePath(), AppConstants.APP_FILE_NAME_APP_ICON_XML, DownloadUtil.defaultListener);
    }

    /**
     * 下载下拉刷新背景图
     */
    private void downloadPic() {
    }

    /**
     * 获取图片版本文件
     */
    private void getAppIconVersion() {
    }

    /**
     * 请求广告页信息
     */
    private void requestOpenAd() {
    }


    /**
     * 倒计时
     */
    private void startCount() {
        time = new TimeCount(4000, 1000);
        time.start();
    }

    /**
     * 跳到首页
     */
    private void jumpToMain() {
        // TODO 判断是否登录
        Intent intent;
        if (SJApplication.getInstance().isLogin()) {
            intent = new Intent(OpenActivity.this, MainActivity.class);
        }else {
            intent = new Intent(OpenActivity.this, LoginActivity.class);
        }
        cancelPrepareTask();
        startActivity(intent);
        finish();
    }

    /**
     * 取消PrepareTask
     */
    private void cancelPrepareTask() {
        if (prepareTask != null && prepareTask.getStatus() != AsyncTask.Status.FINISHED) {
            prepareTask.cancel(true);
        }
    }

    /**
     * 启动准备
     */
    private class PrepareTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            if(!TextUtils.equals(sp.getString("version_code", ""), localVersion)) {
//                startActivity(new Intent(OpenActivity.this, SplashActivity.class));
//                finish();
//            }else {
                jumpToMain();
//            }
//            edit.putString("version_code", localVersion).commit();
        }

    }

    class TimeCount extends CountDownTimer {

        /**
         * 计时
         *
         * @param millisInFuture    总时长
         * @param countDownInterval 计时的时间间隔
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {//计时完毕时触发
            jumpToMain();
        }

        int count = 4;

        @Override
        public void onTick(long millisUntilFinished) {
            count--;
            tvOpenJumpAd.setVisibility(View.VISIBLE);
            tvOpenJumpAd.setText(count + "秒跳过");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode)
//        {
//            case REQUEST_GESTURE:
//                if (resultCode == GestureLoginActivity.SUCCESS) {
//                    Intent intent = new Intent(OpenActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    finish();
//                }
//                break;
//            case AppConstants.INTENT_REQUEST_CODE_FINISH:
//                if (resultCode == RESULT_OK) {
//                    jumpToMain();
//                }
//                break;
//            default:
//                finish();
//                break;
//        }
    }

    @Override
    public void onBackPressed() {
        return;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time != null) {
            time.cancel();
        }
    }
}
