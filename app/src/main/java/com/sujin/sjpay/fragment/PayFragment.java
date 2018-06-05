package com.sujin.sjpay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.AuthenticateActivity;
import com.sujin.sjpay.activity.ChoseBankCardActivity;
import com.sujin.sjpay.activity.PayListActivity;
import com.sujin.sjpay.activity.QuickAgreementActivity;
import com.sujin.sjpay.activity.WebActivity;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetHistoryPayBankCardListResponse;
import com.sujin.sjpay.protocol.MyInfoResponse;
import com.sujin.sjpay.protocol.PayFeeToolResponse;
import com.sujin.sjpay.protocol.PayTypeResponse;
import com.sujin.sjpay.protocol.ReceiveApiResponse;
import com.sujin.sjpay.protocol.RegisterResponse;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.sujin.sjpay.android.AppConstants.INTENT_KEY_BANK_CELL;

public class PayFragment extends BaseFragment {


    @BindView(R.id.tb_pay_title)
    TitleBarView tbPayTitle;
    @BindView(R.id.et_pay_money)
    EditText etPayMoney;
    @BindView(R.id.et_bank_card_number)
    EditText etBankCardNumber;
    @BindView(R.id.iv_add_card)
    ImageView ivAddCard;
    @BindView(R.id.ll_add_card)
    LinearLayout llAddCard;
    @BindView(R.id.tv_no_integral_fee_money)
    TextView tvNoIntegralFeeMoney;
    @BindView(R.id.tv_no_integral_quota)
    TextView tvNoIntegralQuota;
    @BindView(R.id.tv_no_integral_fee)
    TextView tvNoIntegralFee;
    @BindView(R.id.tv_no_integral_clearing)
    TextView tvNoIntegralClearing;
    @BindView(R.id.tv_no_integral_time)
    TextView tvNoIntegralTime;
    @BindView(R.id.tv_no_integral_tip)
    TextView tvNoIntegralTip;
    @BindView(R.id.ll_no_integral_aisle)
    LinearLayout llNoIntegralAisle;
    @BindView(R.id.tv_integral_fee_money)
    TextView tvIntegralFeeMoney;
    @BindView(R.id.tv_integral_quota)
    TextView tvIntegralQuota;
    @BindView(R.id.tv_integral_fee)
    TextView tvIntegralFee;
    @BindView(R.id.tv_integral_clearing)
    TextView tvIntegralClearing;
    @BindView(R.id.tv_integral_time)
    TextView tvIntegralTime;
    @BindView(R.id.tv_integral_tip)
    TextView tvIntegralTip;
    @BindView(R.id.ll_integral_aisle)
    LinearLayout llIntegralAisle;
    Unbinder unbinder;
    @BindView(R.id.tv_integral_name)
    TextView tvIntegralName;
    @BindView(R.id.tv_no_integral_name)
    TextView tvNoIntegralName;
    private String userId, timeIntegral, timeNoIntegral;
    private boolean click = true;
    private int bankID, channelType, payType, integralPayType, noIntegralPayType;

    private GetHistoryPayBankCardListResponse.DataBean.ListBean bankCell;


    public PayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        unbinder = ButterKnife.bind(this, view);
        userId = SJApplication.getInstance().getUserId();
        if (getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).getInt(AppConstants.SP_DATA_IS_REAL_STATE, -1) != 1) {
            ToastUtil.show("实名认证后才可以收款哦~");
            startActivity(new Intent(getActivity(), AuthenticateActivity.class));
        }
        getPayType();

        final MyInfoResponse.MyInfo user = SJApplication.getInstance().getUser();
        etPayMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        int state = getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).getInt(AppConstants.SP_DATA_IS_REAL_STATE, -1);
        if (state != 1) {
            etPayMoney.setFocusable(false);//让EditText失去焦点，然后获取点击事件
            etPayMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show("实名认证后才可以收款哦~");
                    startActivity(new Intent(getActivity(), AuthenticateActivity.class));
                }
            });
        }else {
            etPayMoney.setFocusable(true);
            etPayMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + 3);
                            etPayMoney.setText(s);
                            etPayMoney.setSelection(s.length());
                        }
                    }

                    if (s.toString().trim().substring(0).equals("0")) {
                        s = "";
                        etPayMoney.setText(s);
                    }

                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        etPayMoney.setText(s);
                        etPayMoney.setSelection(2);
                    }

                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            etPayMoney.setText(s.subSequence(0, 1));
                            etPayMoney.setSelection(1);
                            return;
                        }
                    }
                    String s1 = s.toString();
                    if (s1.length() >= 1) {
                        if (s1.substring(s1.length() - 1).equals(".")) {
                            s1 = s1.replace(".", "");
                        }
                    }

                    if (s1.length() >= 3 && Double.parseDouble(s1) >= 500) {
                        getPayFeeTool(userId, s1, 0);
                        getPayFeeToolNo(userId, s1, 1);
                    } else {
                        tvIntegralFeeMoney.setText("费用：" + 0.00 + "元");
                        tvNoIntegralFeeMoney.setText("费用：" + 0.00 + "元");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        tbPayTitle.setOnClick2RightText(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PayListActivity.class));
            }
        });

        etBankCardNumber.setCursorVisible(false);
        etBankCardNumber.setFocusable(false);
        etBankCardNumber.setFocusableInTouchMode(false);
//        tvFee.setText("费率：" + user.getRate1() + " + " + user.getRate3() + "元/笔");
        return view;
    }


    @Override
    public void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取通道
     */
    private void getPayType() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getPayType, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.PayType, s, ApiConstants.API_CONFIG);
        request.add("UserId", userId);
        request(3, request, httpListener, md5, true, true);
    }


    /**
     * 计算手续费，到账金额
     *
     * @param userId
     * @param amount
     * @param payType
     */
    private void getPayFeeTool(String userId, String amount, int payType) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getPayFeeTool, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&Amount=" + amount + "&PayType=" + payType).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.PayFeeTool, s, ApiConstants.API_YEEPAY);
        request.add("UserId", userId);
        request.add("Amount", amount);
        request.add("PayType", payType);
        LogUtils.d("SJHttp", "UserId=" + userId + "&Amount=" + amount + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, true, false);
    }

    /**
     * 计算手续费，到账金额
     *
     * @param userId
     * @param amount
     * @param payType
     */
    private void getPayFeeToolNo(String userId, String amount, int payType) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getPayFeeTool, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&Amount=" + amount + "&PayType=" + payType).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.PayFeeTool, s, ApiConstants.API_YEEPAY);
        request.add("UserId", userId);
        request.add("Amount", amount);
        request.add("PayType", payType);
        LogUtils.d("SJHttp", "UserId=" + userId + "&Amount=" + amount + "---" + s + "---" + md5);
        request(1, request, httpListener, md5, true, false);
    }

    /**
     * 收款
     *
     * @param userId
     * @param amount
     */
    private void getReceiveApi(String userId, String amount, int bankID, int payType) {
        click = false;
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getReceiveApi, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&Amount=" + amount + "&BankID=" + bankID + "&PayType=" + payType).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.ReceiveApi, s, ApiConstants.API_YEEPAY);
        request.add("UserId", userId);
        request.add("Amount", amount);
        request.add("BankID", bankID);
        request.add("PayType", payType);
        LogUtils.d("SJHttp", "UserId=" + userId + "&Amount=" + amount + "&BankID=" + bankID + "---" + s + "---" + md5);
        request(2, request, httpListener, md5, false, false);
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
        request(4, request, httpListener, md5, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    PayFeeToolResponse ayFeeToolResponse = getGson().fromJson(registerJson, PayFeeToolResponse.class);
                    LogUtils.d("SJHttp", ayFeeToolResponse.getBackStatus());
                    if (TextUtils.equals(ayFeeToolResponse.getBackStatus(), "0")) {
                        PayFeeToolResponse.PayFeeTool data = ayFeeToolResponse.getData();
                        String payFee = data.getPayFee();
                        tvIntegralFeeMoney.setText("费用：" + payFee + "元");
                    } else {
                        ToastUtil.show(ayFeeToolResponse.getMessage());
                    }
                    break;
                case 1:
                    String noJson = response.get();
                    PayFeeToolResponse ayFeeToolNoResponse = getGson().fromJson(noJson, PayFeeToolResponse.class);
                    LogUtils.d("SJHttp", ayFeeToolNoResponse.getBackStatus());
                    if (TextUtils.equals(ayFeeToolNoResponse.getBackStatus(), "0")) {
                        PayFeeToolResponse.PayFeeTool data = ayFeeToolNoResponse.getData();
                        String payFee = data.getPayFee();
                        tvNoIntegralFeeMoney.setText("费用：" + payFee + "元");
                    } else {
                        ToastUtil.show(ayFeeToolNoResponse.getMessage());
                    }

                    break;
                case 2:
                    String receiveApiJson = response.get();
                    ReceiveApiResponse receiveApiResponse = getGson().fromJson(receiveApiJson, ReceiveApiResponse.class);
                    LogUtils.d("SJHttp", receiveApiResponse.getBackStatus());
                    ReceiveApiResponse.ReceiveApi data = receiveApiResponse.getData();
                    if (TextUtils.equals(receiveApiResponse.getBackStatus(), "0")) {
                        String payUrl = data.getPayUrl();
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("payUrl", payUrl);
                        intent.putExtra("title", "付款");
                        startActivity(intent);
                        etPayMoney.setText("");
                        etBankCardNumber.setText("");
                    } else if (TextUtils.equals(receiveApiResponse.getBackStatus(), "-200")) {//未开通快捷协议
                        bankID = data.getBankID();
                        channelType = data.getChannelType();
                        getBankCardActivate(userId, bankID);
                        DialogUtil.dismissLoading();
                        etPayMoney.setText("");
                        etBankCardNumber.setText("");
                    } else if (TextUtils.equals(receiveApiResponse.getBackStatus(), "-8401")) {//通道无额度
                        DialogUtil.dismissLoading();
                    } else {
                        ToastUtil.show(receiveApiResponse.getMessage());
                    }
                    click = true;
                    DialogUtil.dismissLoading();
                    break;
                case 3:
                    String payTypeJson = response.get();
                    PayTypeResponse payTypeResponse = getGson().fromJson(payTypeJson, PayTypeResponse.class);
                    LogUtils.d("SJHttp", payTypeResponse.getBackStatus() + "");
                    if (payTypeResponse.getBackStatus() == 0) {
                        List<PayTypeResponse.DataBean> payTypeData = payTypeResponse.getData();
                        PayTypeResponse.DataBean dataBean = payTypeData.get(1);
                        timeIntegral = dataBean.getTime();
                        integralPayType = dataBean.getPayType();
                        tvNoIntegralName.setText(dataBean.getPayName());
                        tvIntegralQuota.setText("额度：" + dataBean.getQuota());
                        tvIntegralFee.setText("费率：" + dataBean.getFee());
                        tvIntegralClearing.setText("结算：" + dataBean.getWithDraw());
                        tvIntegralTime.setText("时间：" + timeIntegral);
                        tvIntegralTip.setText("提示：" + dataBean.getRemark());
                        tvIntegralTip.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        tvIntegralTip.setSingleLine(true);
                        tvIntegralTip.setSelected(true);
                        tvIntegralTip.setFocusable(true);
                        tvIntegralTip.setFocusableInTouchMode(true);

                        PayTypeResponse.DataBean noIntegral = payTypeData.get(0);
                        timeNoIntegral = noIntegral.getTime();
                        noIntegralPayType = noIntegral.getPayType();
                        tvIntegralName.setText(noIntegral.getPayName());
                        tvNoIntegralQuota.setText("额度：" + noIntegral.getQuota());
                        tvNoIntegralFee.setText("费率：" + noIntegral.getFee());
                        tvNoIntegralClearing.setText("结算：" + noIntegral.getWithDraw());
                        tvNoIntegralTime.setText("时间：" + timeNoIntegral);
                        tvNoIntegralTip.setText("提示：" + noIntegral.getRemark());
                        tvNoIntegralTip.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        tvNoIntegralTip.setSingleLine(true);
                        tvNoIntegralTip.setSelected(true);
                        tvNoIntegralTip.setFocusable(true);
                        tvNoIntegralTip.setFocusableInTouchMode(true);

                    } else {
                        ToastUtil.show(payTypeResponse.getMessage());
                    }
                    break;
                case 4:
                    String bankCardActivateJson = response.get();
                    RegisterResponse registerResponse = getGson().fromJson(bankCardActivateJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", registerResponse.getBackStatus() + "");
                    if (registerResponse.getBackStatus().equals("0")) {
                        ToastUtil.show("短信已发送");
                        Intent intent = new Intent(getActivity(), QuickAgreementActivity.class);
                        intent.putExtra(INTENT_KEY_BANK_CELL, bankCell);
                        intent.putExtra("ChannelType", channelType);
                        intent.putExtra("payMoney", payMoney);
                        intent.putExtra("PayType", payType);
                        startActivity(intent);
                    } else {
                        ToastUtil.show(registerResponse.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            DialogUtil.dismissLoading();
            click = true;
            LogUtils.d("SJHttp", json);
        }
    };

    String payMoney = StringUtil.EMPTY, bankCardNumber = StringUtil.EMPTY;

    @OnClick({R.id.iv_add_card, R.id.et_bank_card_number, R.id.ll_no_integral_aisle, R.id.ll_integral_aisle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_bank_card_number:
                if (getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).getInt(AppConstants.SP_DATA_IS_REAL_STATE, -1) != 1) {
                    ToastUtil.show("实名认证后才可以收款哦~");
                    startActivity(new Intent(getActivity(), AuthenticateActivity.class));
                    return;
                }
                Intent intent = new Intent(getActivity(), ChoseBankCardActivity.class);
                startActivityForResult(intent, AppConstants.INTENT_REQUEST_CODE_FROM_BAND_CARD);
                break;
            case R.id.ll_no_integral_aisle:
                if (getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).getInt(AppConstants.SP_DATA_IS_REAL_STATE, -1) != 1) {
                    ToastUtil.show("实名认证后才可以收款哦~");
                    startActivity(new Intent(getActivity(), AuthenticateActivity.class));
                    return;
                }

                payMoney = etPayMoney.getText().toString().trim();
                if (TextUtils.isEmpty(payMoney)) {
                    ToastUtil.show("金额不能为空");
                    return;
                }

                bankCardNumber = etBankCardNumber.getText().toString().trim();
                if (TextUtils.isEmpty(bankCardNumber)) {
                    ToastUtil.show("请选择支付卡");
                    return;
                }
                if (!StringUtil.checkBankCardNumber(bankCardNumber)) {
                    return;
                }
                payType = noIntegralPayType;
                if (click) {
                    DialogUtil.showLoading(getContext(), false);
                    getReceiveApi(userId, payMoney, bankID, payType);
                }
                break;
            case R.id.ll_integral_aisle:
                if (getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).getInt(AppConstants.SP_DATA_IS_REAL_STATE, -1) != 1) {
                    ToastUtil.show("实名认证后才可以收款哦~");
                    startActivity(new Intent(getActivity(), AuthenticateActivity.class));
                    return;
                }

                payMoney = etPayMoney.getText().toString().trim();
                if (TextUtils.isEmpty(payMoney)) {
                    ToastUtil.show("金额不能为空");
                    return;
                }

                bankCardNumber = etBankCardNumber.getText().toString().trim();
                if (TextUtils.isEmpty(bankCardNumber)) {
                    ToastUtil.show("请选择支付卡");
                    return;
                }
                if (!StringUtil.checkBankCardNumber(bankCardNumber)) {
                    return;
                }
                payType = integralPayType;
                if (click) {
                    DialogUtil.showLoading(getContext(), false);
                    getReceiveApi(userId, payMoney, bankID, payType);
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.INTENT_REQUEST_CODE_FROM_BAND_CARD:
                    Serializable serializableExtra = data.getSerializableExtra(INTENT_KEY_BANK_CELL);
                    if (serializableExtra != null) {
                        bankCell = (GetHistoryPayBankCardListResponse.DataBean.ListBean) serializableExtra;
                        String bankCard = bankCell.getBankCard();
                        bankID = bankCell.getID();
                        etBankCardNumber.setText(bankCard);
                    }
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
