package com.sujin.sjpay.activity;

import android.content.Intent;
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
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.FindPasswordResponse;
import com.sujin.sjpay.protocol.GetSMSResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPasswordActivity extends BaseActivity implements SJApplication.OnTimeCountDownListener{

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.edit_register_img_code)
    EditText editRegisterImgCode;
    @BindView(R.id.tv_register_code_get)
    ImageView tvRegisterCodeGet;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_get_sms_code)
    TextView tvGetSmsCode;
    @BindView(R.id.tv_next)
    TextView tvNext;

    private boolean isGetSms = false;
    private String phoneNum, imgCode, uuId, forgetGuid, forgetSms, afterGetAgainStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
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
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getForgetImgCode, RequestMethod.GET);
        uuId = StringUtil.getUUId();
        char[] chars = ("guid=" + uuId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.GetImgCode, s, ApiConstants.API_FORGET);
        request.add("itormName", "itormandroid");
        request.add("sign", md5);
        request.add("guid", uuId);
        request.add("version", "1.0.0");
//        request(0, request, httpListener, md5, true, true);
        Glide.with(this).load(request.url()).into(tvRegisterCodeGet);
        com.lidroid.xutils.util.LogUtils.d("guid=" + uuId + "\n" + uuId + "\n" + s + "\n"  + md5);
    }

    //获取短验
    private void getSmsCode(String phoneNum, String imgCode, String guid) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getForgetSmsCode, RequestMethod.GET);
        char[] chars = ("mobile=" + phoneNum + "&vcode=" + imgCode + "&guid=" + uuId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.SendMsgCode, s, ApiConstants.API_FORGET);
        request.add("mobile", phoneNum);
        request.add("vcode", imgCode);
        request.add("guid", uuId);
        com.lidroid.xutils.util.LogUtils.d(uuId + "---" + s + "---"  + md5);
        request(0, request, httpListener, md5, true, true);
    }

    //校验短验
    private void validateMobileCode(String phoneNum, String mcode) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getForgetValidateMobileCode, RequestMethod.GET);
        char[] chars = ("mobile=" + phoneNum + "&mcode=" + mcode + "&forgetGuid=" + forgetGuid).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.ValidateMobileCode, s, ApiConstants.API_FORGET);
        request.add("mobile", phoneNum);
        request.add("mcode", mcode);
        request.add("forgetGuid", forgetGuid);
        com.lidroid.xutils.util.LogUtils.d(forgetGuid + "---" + s + "---"  + md5);
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
                        forgetGuid = data.getForgetGuid();
                    } else {
                        ToastUtil.show(getSMSResponse.getMessage());
                    }
                    break;
                case 1:
                    String findPasswordJson = response.get();
                    FindPasswordResponse findPassword = getGson().fromJson(findPasswordJson, FindPasswordResponse.class);
                    LogUtils.d("SJHttp", findPassword.getBackStatus());
                    if (TextUtils.equals(findPassword.getBackStatus(), "0")) {
                        ToastUtil.show(findPassword.getMessage());
                        FindPasswordResponse.Find data = findPassword.getData();
                        forgetGuid = data.getForgetGuid();
                        Intent intent = new Intent(FindPasswordActivity.this, SetPasswordActivity.class);
                        intent.putExtra("forgetGuid", forgetGuid);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.show(findPassword.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
        }

    };

    @OnClick({R.id.tv_register_code_get, R.id.tv_get_sms_code, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_code_get:
                getImgCode();
                break;
            case R.id.tv_get_sms_code:
                phoneNum = etPhoneNumber.getText().toString();
                imgCode = editRegisterImgCode.getText().toString();
                if (StringUtil.checkPhoneNumber(phoneNum)) {
                    if (imgCode.length() == 4) {
                        isGetSms = true;
                        getSmsCode(phoneNum, imgCode, uuId);
                    }else {
                        ToastUtil.show("图形验证码有误");
                    }
                }
                break;
            case R.id.tv_next:
                if (isGetSms) {
                    phoneNum = etPhoneNumber.getText().toString();
                    forgetSms = etSmsCode.getText().toString();

                    if (StringUtil.checkPhoneNumber(phoneNum)) {
                        if (forgetSms.length() == 6) {
                            validateMobileCode(phoneNum, forgetSms);
                        }else {
                            ToastUtil.show("验证码有误");
                        }
                    }else {
                        ToastUtil.show("请先获取验证码");
                    }
                }
                break;
        }
    }

    @Override
    public void onTimeCount(int counter) {
        if (counter <= 0) {
            tvGetSmsCode.setEnabled(true);
            tvGetSmsCode.setText(getString(R.string.get_again));
            tvGetSmsCode.setTextColor(getResources().getColor(R.color.blue_3897F0));
        } else {
            tvGetSmsCode.setEnabled(false);
            tvGetSmsCode.setText(String.format(afterGetAgainStr, counter));
            tvGetSmsCode.setTextColor(getResources().getColor(R.color.gray_A3A3A3));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SJApplication.getInstance().cancelCountDown();
        SJApplication.getInstance().unregisterTimeCountDownListener();
    }
}
