package com.sujin.sjpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetHistoryPayBankCardListResponse;
import com.sujin.sjpay.protocol.PayFeeToolResponse;
import com.sujin.sjpay.protocol.PayTypeResponse;
import com.sujin.sjpay.protocol.ReceiveApiResponse;
import com.sujin.sjpay.protocol.RegisterResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sujin.sjpay.android.AppConstants.INTENT_KEY_BANK_CELL;

public class QuickAgreementActivity extends BaseActivity implements SJApplication.OnTimeCountDownListener {

    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bankcard)
    TextView tvBankcard;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_get_sms_code)
    TextView tvGetSmsCode;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private GetHistoryPayBankCardListResponse.DataBean.ListBean bankCell;
    private String afterGetAgainStr, userId, payMoney;
    private int bankId, channelType, payType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_agreement);
        ButterKnife.bind(this);
        SJApplication.getInstance().startCountDown();
        userId = SJApplication.getInstance().getUserId();
        Intent intent = getIntent();
        bankCell = (GetHistoryPayBankCardListResponse.DataBean.ListBean)intent.getSerializableExtra(INTENT_KEY_BANK_CELL);
        channelType = intent.getIntExtra("ChannelType", -1);
        payType = intent.getIntExtra("PayType", -1);
        payMoney = intent.getStringExtra("payMoney");
        bankId = bankCell.getID();
        initView();
    }

    @Override
    protected void initView() {
        SJApplication.getInstance().setOnTimeCountDownListener(this);
        afterGetAgainStr = getString(R.string.after_get_again);
        tvBankcard.setText(bankCell.getBankCard());
        tvBankName.setText(bankCell.getBankName());
    }

    @OnClick({R.id.tv_get_sms_code, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms_code:
                getBankCardActivate(userId, bankCell.getID());
                break;
            case R.id.tv_next:
                String forgetSms = etSmsCode.getText().toString();
                if (forgetSms.length() == 6) {
                    getBankCardSubmitActivateCode(userId, channelType, forgetSms, bankId);
                }else {
                    ToastUtil.show(getResources().getString(R.string.sms_code_error));
                }
                break;
        }
    }

    /**
     * 获取快捷短验
     *
     * @param userId
     */
    private void getBankCardActivate(String userId, int bankID) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getBankCardActivate, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&BankID=" + bankID + "&ChannelType=" + channelType).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.BankCardActivate, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("BankID", bankID);
        request.add("ChannelType", channelType);
        LogUtils.d("SJHttp", "UserId=" + userId + "&BankID=" + bankID + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, true, true);
    }

    /**
     * 开通快捷
     *
     * @param userId
     * @param bankId
     */
    private void getBankCardSubmitActivateCode(String userId, int bankID, String code, int bankId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getBankCardSubmitActivateCode, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&BankID=" + bankId + "&ChannelType=" + channelType + "&Code=" + code).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.BankCardSubmitActivateCode, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("BankID", bankId);
        request.add("ChannelType", channelType);
        request.add("Code", code);
        LogUtils.d("SJHttp", "UserId=" + userId + "&BankID=" + bankId + "---" + s + "---" + md5);
        request(1, request, httpListener, md5, true, true);
    }

    /**
     * 收款
     *
     * @param userId
     * @param amount
     */
    private void getReceiveApi(String userId, String amount, int bankID, int payType) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getReceiveApi, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&Amount=" + amount + "&BankID=" + bankID + "&PayType=" + payType).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.ReceiveApi, s, ApiConstants.API_YEEPAY);
        request.add("UserId", userId);
        request.add("Amount", amount);
        request.add("BankID", bankID);
        request.add("PayType", payType);
        LogUtils.d("SJHttp", "UserId=" + userId + "&Amount=" + amount + "&BankID=" + bankID + "---" + s + "---" + md5);
        request(2, request, httpListener, md5, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String bankCardActivateJson = response.get();
                    RegisterResponse registerResponse = getGson().fromJson(bankCardActivateJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", registerResponse.getBackStatus() + "");
                    if (registerResponse.getBackStatus().equals("0")) {
                        ToastUtil.show("短信已发送");
                        SJApplication.getInstance().startCountDown();
                    } else {
                        ToastUtil.show(registerResponse.getMessage());
                    }
                    break;
                case 1:
                    String bankCardSubmitActivateCode = response.get();
                    RegisterResponse bankCardSubmitActivateCodeResponse = getGson().fromJson(bankCardSubmitActivateCode , RegisterResponse.class);
                    LogUtils.d("SJHttp", bankCardSubmitActivateCodeResponse.getBackStatus() + "");
                    if (bankCardSubmitActivateCodeResponse.getBackStatus().equals("0")) {
                        ToastUtil.show("开通成功");
                        getReceiveApi(userId, payMoney, bankCell.getID(), payType);
                    } else {
                        ToastUtil.show(bankCardSubmitActivateCodeResponse.getMessage());
                    }
                    break;
                case 2:
                    String receiveApiJson = response.get();
                    ReceiveApiResponse receiveApiResponse = getGson().fromJson(receiveApiJson, ReceiveApiResponse.class);
                    LogUtils.d("SJHttp", receiveApiResponse.getBackStatus());
                    if (TextUtils.equals(receiveApiResponse.getBackStatus(), "0")) {
                        ReceiveApiResponse.ReceiveApi data = receiveApiResponse.getData();
                        String payUrl = data.getPayUrl();
                        Intent intent = new Intent(QuickAgreementActivity.this, WebActivity.class);
                        intent.putExtra("payUrl", payUrl);
                        intent.putExtra("title", "付款");
                        startActivity(intent);
                        finish();
                    } else if(TextUtils.equals(receiveApiResponse.getBackStatus(), "-8401")){
                        ToastUtil.show("快捷支付开通成功，通道额度已用完:(");
                        finish();
                    } else {
                        ToastUtil.show(receiveApiResponse.getMessage());
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
