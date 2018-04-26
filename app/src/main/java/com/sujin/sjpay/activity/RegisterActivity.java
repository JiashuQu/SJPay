package com.sujin.sjpay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.bumptech.glide.Glide;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetSMSResponse;
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

public class RegisterActivity extends BaseActivity implements SJApplication.OnTimeCountDownListener{

    @BindView(R.id.img_register)
    ImageView imgRegister;
    @BindView(R.id.edit_register_phone_num)
    EditText editRegisterPhoneNum;
    @BindView(R.id.edit_register_sms)
    EditText editRegisterSms;
    @BindView(R.id.tv_register_sms_get)
    TextView tvRegisterSmsGet;
    @BindView(R.id.edit_register_invite_code)
    EditText editRegisterInviteCode;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.edit_register_img_code)
    EditText editRegisterImgCode;
    @BindView(R.id.tv_register_code_get)
    ImageView tvRegisterCodeGet;
    @BindView(R.id.edit_register_password)
    PasswordEditText editRegisterPassword;

    private String phoneNum, password, registerSms, imgCode, uuId, regGuid, baseUserId, afterGetAgainStr;

    private boolean isGetSms = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        SJApplication.getInstance().setOnTimeCountDownListener(this);
        afterGetAgainStr = getString(R.string.after_get_again);
        getImgCode();
    }

    //获取图验
    private void getImgCode() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getImgCode, RequestMethod.GET);
        uuId = StringUtil.getUUId();
        char[] chars = ("guid=" + uuId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.GetImgCode, s, ApiConstants.API_USERS);
        request.add("itormName", "itormandroid");
        request.add("sign", md5);
        request.add("guid", uuId);
        request.add("version", "1.0.0");
//        request(0, request, httpListener, md5, true, true);
        Glide.with(this).load(request.url()).into(tvRegisterCodeGet);
        com.lidroid.xutils.util.LogUtils.d(uuId + "---" + s + "---"  + md5 + "===" + request.url());
    }

    //获取短验
    private void getSmsCode(String phoneNum, String imgCode, String guid) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getSmsCode, RequestMethod.GET);
        char[] chars = ("mobile=" + phoneNum + "&vcode=" + imgCode + "&guid=" + uuId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.SendMsgCode, s, ApiConstants.API_USERS);
        request.add("mobile", phoneNum);
        request.add("vcode", imgCode);
        request.add("guid", uuId);
        com.lidroid.xutils.util.LogUtils.d(uuId + "---" + s + "---"  + md5);
        request(0, request, httpListener, md5, false, true);
    }

    /**
     * 校验邀请码
     * @param baseUserId
     */
    private void checkInvite(String baseUserId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getCheckBaseUserIdByMobile, RequestMethod.GET);
        char[] chars = ("mobile=" + baseUserId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.CheckBaseUserIdByMobile, s, ApiConstants.API_USERS);
        request.add("mobile", baseUserId);
        com.lidroid.xutils.util.LogUtils.d("mobile=" + baseUserId + "\n---" + regGuid + "\n---" + s + "\n---"  + md5);
        request(2, request, httpListener, md5, true, true);
    }

    //注册
    private void register(String phoneNum, String password, String registerSms, String regGuid, String baseUserId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getRegister, RequestMethod.GET);
        String md5Password = StringUtil.MD5(password);
        char[] chars = ("mobile=" + phoneNum + "&password=" + md5Password + "&mcode=" + registerSms + "&regGuid=" + regGuid + "&baseUserId=" + baseUserId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Register, s, ApiConstants.API_USERS);
        request.add("mobile", phoneNum);
        request.add("password", md5Password);
        request.add("mcode", registerSms);
        request.add("regGuid", regGuid);
        request.add("baseUserId", baseUserId);
        com.lidroid.xutils.util.LogUtils.d("mobile=" + phoneNum + "&password=" + md5Password + "&mcode=" + registerSms + "&regGuid=" + regGuid + "&baseUserId=" + baseUserId + "\n---" + regGuid + "\n---" + s + "\n---"  + md5);
        request(1, request, httpListener, md5, true, true);
    }
    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String json = response.get();
                    GetSMSResponse getSMSResponse = getGson().fromJson(json, GetSMSResponse.class);
                    LogUtils.d("SJHttp", getSMSResponse.getBackStatus());
                    if (TextUtils.equals(getSMSResponse.getBackStatus(), "0")) {
                        SJApplication.getInstance().startCountDown();
                        ToastUtil.show(getSMSResponse.getMessage());
                        GetSMSResponse.GetData data = getSMSResponse.getData();
                        regGuid = data.getRegGuid();
                    }else {
                        ToastUtil.show(getSMSResponse.getMessage());
                    }
                    break;
                case 1:
                    String registerJson = response.get();
                    RegisterResponse registerResponse = getGson().fromJson(registerJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", registerResponse.getBackStatus());
                    if (TextUtils.equals(registerResponse.getBackStatus(), "0")) {
                        ToastUtil.show(registerResponse.getMessage());
                        RegisterResponse.Register data = registerResponse.getData();
                        String userId = data.getUserId();
                        SharedPreferences.Editor spUserInfo = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).edit();
                        spUserInfo.putString(AppConstants.SP_DATA_USER_ID, userId);
                        spUserInfo.commit();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }else {
                        ToastUtil.show(registerResponse.getMessage());
                    }
                    getImgCode();
                    break;
                case 2:
                    String checkJson = response.get();
                    RegisterResponse checkResponse = getGson().fromJson(checkJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", checkResponse.getBackStatus());
                    if (TextUtils.equals(checkResponse.getBackStatus(), "0")) {
                        ToastUtil.show(checkResponse.getMessage());
                        RegisterResponse.Register data = checkResponse.getData();
                        String userId = data.getUserId();
                        register(phoneNum, password, registerSms, regGuid, userId);
                    }else {
                        ToastUtil.show(checkResponse.getMessage());
                    }
                    getImgCode();
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();

            LogUtils.d("SJHttp", json);
        }
    };

    @OnClick({R.id.tv_register_code_get, R.id.tv_register_sms_get, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_code_get:
                getImgCode();
                break;
            case R.id.tv_register_sms_get:
                phoneNum = editRegisterPhoneNum.getText().toString();
                imgCode = editRegisterImgCode.getText().toString();
                if (StringUtil.checkPhoneNumber(phoneNum)) {
                    if (imgCode.length() == 4) {
                        isGetSms = true;
                        getSmsCode(phoneNum, imgCode, uuId);
                    }else {
                        ToastUtil.show(getResources().getString(R.string.image_code_error));
                    }
                }

                break;
            case R.id.tv_register:
                if (isGetSms) {
                    phoneNum = editRegisterPhoneNum.getText().toString();
                    registerSms = editRegisterSms.getText().toString();
                    password = editRegisterPassword.getText().toString();
                    if (editRegisterInviteCode.getText().toString().trim().length() == 0) {
                        ToastUtil.show("邀请码不能为空");
                        return;
                    }else {
                        baseUserId = editRegisterInviteCode.getText().toString();
                    }

                    if (StringUtil.checkPhoneNumber(phoneNum)) {
                        if (registerSms.length() == 6) {
                            if (TextUtils.isEmpty(password)) {
                                ToastUtil.show(R.string.hint_login_password);
                                return;
                            }
                            if (password.length() < 6 || password.length() > 16) {
                                ToastUtil.show("密码必须为6~16位字母，数字，部分符号");
                                return;
                            }
                            checkInvite(baseUserId);
                        }else {
                            ToastUtil.show(getResources().getString(R.string.sms_code_error));
                        }
                    }
                }else {
                    ToastUtil.show("请先获取验证码");
                }
                break;
        }
    }

    @Override
    public void onTimeCount(int counter) {
        if (counter <= 0) {
            tvRegisterSmsGet.setEnabled(true);
            tvRegisterSmsGet.setText(getString(R.string.get_again));
            tvRegisterSmsGet.setTextColor(getResources().getColor(R.color.blue_3897F0));
        } else {
            tvRegisterSmsGet.setEnabled(false);
            tvRegisterSmsGet.setText(String.format(afterGetAgainStr, counter));
            tvRegisterSmsGet.setTextColor(getResources().getColor(R.color.gray_A3A3A3));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SJApplication.getInstance().cancelCountDown();
        SJApplication.getInstance().unregisterTimeCountDownListener();
    }
}
