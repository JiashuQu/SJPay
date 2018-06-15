package com.sujin.sjpay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.RegisterResponse;
import com.sujin.sjpay.util.CheckNetWorkUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.PasswordEditText;
import com.sujin.sjpay.view.TitleBarView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_login_phone_num)
    EditText editLoginPhoneNum;
    @BindView(R.id.edit_login_password)
    PasswordEditText editLoginPassword;
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;
    @BindView(R.id.tv_login_login)
    TextView tvLoginLogin;
    @BindView(R.id.tv_login_quick)
    TextView tvLoginQuick;
    @BindView(R.id.tb_login_title)
    TitleBarView tbLoginTitle;

    private String phoneNum, password, uuId;
    private boolean isExit = false;

    Handler exitHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        tvLoginQuick.setText(StringUtil.changeStringColor(this, getResources().getString(R.string.quick_register), 6, R.color.black_333333, R.color.blue_3BA8FF));
        tbLoginTitle.hideBackButton();
        ImageView imgTitlebarBack = tbLoginTitle.getImgTitlebarBack();
        imgTitlebarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

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

    @OnClick({R.id.tv_login_forget, R.id.tv_login_login, R.id.tv_login_quick})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_login_forget:
                intent = new Intent(this, FindPasswordActivity.class);
                break;
            case R.id.tv_login_login:
                phoneNum = editLoginPhoneNum.getText().toString();
                password = editLoginPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.show(R.string.hint_login_password);
                    return;
                }
                if (password.length() < 6) {
                    ToastUtil.show("密码必须为6~16位字母，数字，部分符号");
                    return;
                }
                // FIXME
//                if (StringUtil.checkPhoneNumber(phoneNum)) {
                    login(phoneNum, password);
//                }
                break;
            case R.id.tv_login_quick:
                intent = new Intent(this, RegisterActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }


    //登录
    private void login(String phoneNum, String password) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getLogin, RequestMethod.GET);
        String md5Password = StringUtil.MD5(password);
        uuId = StringUtil.getUUId();
        char[] chars = ("mobile=" + phoneNum + "&password=" + md5Password + "&guid=" + uuId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Login, s, ApiConstants.API_USERS);
        request.add("mobile", phoneNum);
        request.add("password", md5Password);
        request.add("guid", uuId);
        com.lidroid.xutils.util.LogUtils.d(uuId + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    RegisterResponse registerResponse = getGson().fromJson(registerJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", registerResponse.getBackStatus());
                    if (TextUtils.equals(registerResponse.getBackStatus(), "0")) {
                        RegisterResponse.Register data = registerResponse.getData();
                        String userId = data.getUserId();
                        SharedPreferences.Editor spUserInfo = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).edit();
                        spUserInfo.putString(AppConstants.SP_DATA_USER_ID, userId);
                        spUserInfo.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("forgetGuid", forgetGuid);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.show(registerResponse.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();

            LogUtils.d("SJHttp", getResources().getString(R.string.net_error));
        }
    };
}
