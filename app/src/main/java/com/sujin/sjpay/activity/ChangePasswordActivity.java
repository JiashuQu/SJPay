package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
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

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.et_old_password)
    PasswordEditText etOldPassword;
    @BindView(R.id.et_new_password)
    PasswordEditText etNewPassword;
    @BindView(R.id.et_new_password_again)
    PasswordEditText etNewPasswordAgain;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        String old = etOldPassword.getText().toString().trim();
        String p = etNewPassword.getText().toString().trim();
        String p1 = etNewPasswordAgain.getText().toString().trim();

        if (TextUtils.isEmpty(old)) {
            ToastUtil.show(R.string.hint_login_password);
            return;
        }
        if (old.length() < 6) {
            ToastUtil.show(R.string.hint_login_error_tip);
            return;
        }

        if (p.length() < 6 || p.length() > 16 || p1.length() < 6 || p1.length() > 16) {
            ToastUtil.show("密码必须为6~16字母，数字，部分符号");
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

        String userId = SJApplication.getInstance().getUserId();
        setPassword(userId, old, p);
    }

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    private void setPassword(String userId, String oldPwd, String newPwd) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getUpdateNewPassword, RequestMethod.GET);
        String oldPassword = StringUtil.MD5(oldPwd);
        String newPassword = StringUtil.MD5(newPwd);
        char[] chars = ("UserId=" + userId + "&oldPwd=" + oldPassword + "&newPwd=" + newPassword).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.UpdatePassword, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("oldPwd", oldPassword);
        request.add("newPwd", newPassword);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId + "&oldPwd=" + oldPassword + "&newPwd=" + newPassword + "---" + s + "---"  + md5);
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
                        ToastUtil.show(registerResponse.getMessage());
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

            LogUtils.d("SJHttp", json);
        }
    };
}
