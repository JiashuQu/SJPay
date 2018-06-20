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
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetHistoryPayBankCardListResponse;
import com.sujin.sjpay.protocol.GetPayBankQuotaList;
import com.sujin.sjpay.protocol.PayCardListResponse;
import com.sujin.sjpay.protocol.RegisterResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.BankCellView;
import com.sujin.sjpay.view.TitleBarView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreditCardActivity extends BaseActivity {

    @BindView(R.id.et_bank_card_number)
    EditText etBankCardNumber;
    @BindView(R.id.bankcell_band_card)
    BankCellView bankcellBandCard;
    @BindView(R.id.et_bank_phone_number)
    EditText etBankPhoneNumber;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.et_bank_date)
    EditText etBankDate;
    @BindView(R.id.et_bank_cvn)
    EditText etBankCvn;
    @BindView(R.id.title_bar)
    TitleBarView titleBar;

    private String bankName, bankCardNumber, phoneNum, bankDate, bankCvn, title, bankCode;
    private int bankId = 0;
    private int state = -1;
    private GetHistoryPayBankCardListResponse.DataBean.ListBean creditCardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        creditCardInfo = (GetHistoryPayBankCardListResponse.DataBean.ListBean)intent.getSerializableExtra("CreditCardInfo");
        title = intent.getStringExtra("title");
        initView();
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(title)) {
            titleBar.setTitle(title);
            tvConfirm.setText("修改");
        }
        if (creditCardInfo != null) {
            String bankCard = creditCardInfo.getBankCard();
            String bankName = creditCardInfo.getBankName();
            bankCode = creditCardInfo.getBankCode();
            bankId = creditCardInfo.getID();
            String cvn2 = creditCardInfo.getCVN2();
            String expiresMouth = creditCardInfo.getExpiresMouth();
            String expiresYear = creditCardInfo.getExpiresYear();
            String mobile = creditCardInfo.getMobile();
            state = creditCardInfo.getState();
            etBankCardNumber.setText(bankCard);
            bankcellBandCard.setData(bankName, "", "");
            etBankDate.setText(expiresMouth + expiresYear);
            etBankCvn.setText(cvn2);
            etBankPhoneNumber.setText(mobile);
            if (state == 1) {
                etBankCardNumber.setEnabled(false);
                bankcellBandCard.setEnabled(false);
                etBankDate.setEnabled(false);
                etBankCvn.setEnabled(false);
            }
        }
    }

    @OnClick({R.id.bankcell_band_card, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bankcell_band_card:
                Intent intent = new Intent(this, SelectPayBankActivity.class);
                startActivityForResult(intent, AppConstants.INTENT_REQUEST_CODE_FROM_BAND_CARD);
                break;
            case R.id.tv_confirm:
                bankCardNumber = etBankCardNumber.getText().toString().trim();
                bankDate = etBankDate.getText().toString().trim();
                bankCvn = etBankCvn.getText().toString().trim();
                if (!StringUtil.checkBankCardNumber(bankCardNumber)) {
                    return;
                }
                if (bankDate.length() != 4) {
                    ToastUtil.show("日期不正确");
                    return;
                }
                if (bankCvn.length() != 3) {
                    ToastUtil.show("CVN2码不正确");
                    return;
                }
                if (!StringUtil.checkBankCardNumber(bankCardNumber)) {
                    return;
                }
                phoneNum = etBankPhoneNumber.getText().toString();
                if (StringUtil.checkPhoneNumber(phoneNum)) {
                    tvConfirm.setEnabled(false);
//                    if (bankId != 0) {
                    if (state == 1) {//只能修改手机号
                        changeCreditCard(phoneNum, bankId);
                    }else if(state == 0){
                        bankCreditCard(bankCardNumber, phoneNum, bankDate, bankCvn, bankCode, bankId);
                    }else{
                        bankCreditCard(bankCardNumber, phoneNum, bankDate, bankCvn, bankCode, 0);
                    }
                }
                break;
        }
    }

    /**
     * 绑定信用卡
     *
     * @param bankCardNumber
     * @param phoneNum
     * @param cvn2
     * @param expires
     */
    private void bankCreditCard(String bankCardNumber, String phoneNum, String expires, String cvn2, String bankCode, int bankId) {
        String userId = SJApplication.getInstance().getUserId();
        String expiresMouth = expires.substring(0, 2);
        String expiresYear = expires.substring(2, 4);
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getBankBind, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&mobile=" + phoneNum + "&bankcard=" + bankCardNumber +
                "&bankcode=" + bankCode + "&typeid=" + "1" + "&cvn2=" + cvn2 + "&expiresMouth=" + expiresMouth + "&expiresYear=" + expiresYear + "&BankID=" + bankId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.BankBind, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("mobile", phoneNum);
        request.add("bankcard", bankCardNumber);
        request.add("bankcode", bankCode);
        request.add("typeid", "1");
        request.add("cvn2", cvn2);
        request.add("expiresMouth", expiresMouth);
        request.add("expiresYear", expiresYear);
        request.add("BankID", bankId);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId + "&mobile=" + phoneNum + "&bankcard=" + bankCardNumber + "&bankcode=" + bankId + "&typeid=" + 1 + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, false, true);
    }

    /**
     * 修改银行卡手机号
     * @param phoneNum
     * @param bankId
     */
    private void changeCreditCard(String phoneNum, int bankId) {
        String userId = SJApplication.getInstance().getUserId();
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getUpdateBankMobile, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&mobile=" + phoneNum + "&BankID=" + bankId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.UpdateBankMobile, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("mobile", phoneNum);
        request.add("BankID", bankId);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId + "&mobile=" + phoneNum + bankId + "---" + s + "---" + md5);
        request(1, request, httpListener, md5, false, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            tvConfirm.setEnabled(true);
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    RegisterResponse registerResponse = getGson().fromJson(registerJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", registerResponse.getBackStatus());
                    if (TextUtils.equals(registerResponse.getBackStatus(), "0")) {
                        ToastUtil.show(registerResponse.getMessage());
                        finish();
                    } else {
                        ToastUtil.show(registerResponse.getMessage());
                    }
                    break;
                case 1:
                    String updateJson = response.get();
                    RegisterResponse updateResponse = getGson().fromJson(updateJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", updateResponse.getBackStatus());
                    if (TextUtils.equals(updateResponse.getBackStatus(), "0")) {
                        ToastUtil.show(updateResponse.getMessage());
                        finish();
                    } else {
                        ToastUtil.show(updateResponse.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            tvConfirm.setEnabled(true);
            String json = response.get();
            LogUtils.d("SJHttp", getResources().getString(R.string.net_error));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.INTENT_REQUEST_CODE_FROM_BAND_CARD:
                    Serializable serializableExtra = data.getSerializableExtra(AppConstants.INTENT_KEY_BANK_CELL);
                    if (serializableExtra != null) {
                        PayCardListResponse.DataBean bankCell = (PayCardListResponse.DataBean) serializableExtra;
                        bankCode = bankCell.getBankCode();
                        bankName = bankCell.getBankName();
                        bankcellBandCard.setData(bankName, "", "");
                    }
                    break;
            }
        }
    }
}
