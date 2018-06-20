package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.RegisterResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.PasswordEditText;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity {

    @BindView(R.id.et_new_password)
    PasswordEditText etNewPassword;
    @BindView(R.id.et_new_password_again)
    PasswordEditText etNewPasswordAgain;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private String forgetGuid = StringUtil.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
        forgetGuid = getIntent().getStringExtra("forgetGuid");
        initView();
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        String p = etNewPassword.getText().toString().trim();
        String p1 = etNewPasswordAgain.getText().toString().trim();
        if (p.length() < 6 || p.length() > 16 || p1.length() < 6 || p1.length() > 16) {
            ToastUtil.show("密码必须为6~16位字母，数字，部分符号");
            return;
        }
        if (TextUtils.isEmpty(p) || TextUtils.isEmpty(p1)) {
            ToastUtil.show("请输入您的新密码");
            return;
        }
        if (!TextUtils.equals(p, p1)) {
            ToastUtil.show("两次密码不一致");
            return;
        }

        setPassword(p);
    }

    //设置密码
    private void setPassword(String p) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getUpdatePassword, RequestMethod.GET);
        String password = StringUtil.MD5(p);
        char[] chars = ("forgetGuid=" + forgetGuid + "&password=" + password).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.UpdatePassword, s, ApiConstants.API_FORGET);
        request.add("forgetGuid", forgetGuid);
        request.add("password", password);
        com.lidroid.xutils.util.LogUtils.d(forgetGuid + "---" + s + "---"  + md5);
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
                        ToastUtil.show("重置成功");
                        finish();
                    }else {
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
