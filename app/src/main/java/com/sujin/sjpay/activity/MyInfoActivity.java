package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.protocol.MyInfoResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_id_card)
    TextView tvIdCard;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_bankcard)
    TextView tvBankcard;
    @BindView(R.id.tv_my_feel)
    TextView tvMyFeel;
    @BindView(R.id.tv_bankcard_belong)
    TextView tvBankcardBelong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        MyInfoResponse myInfo = (MyInfoResponse) getIntent().getSerializableExtra("myInfo");
        MyInfoResponse.DataBean data = myInfo.getData();
        String vipTypeTxt = data.getVipTypeTxt();
        String bankCard = data.getBankCard();
        String bankName = data.getBankName();
        tvName.setText(data.getRealName());
        tvPhone.setText(data.getMobile());
        tvIdCard.setText(data.getIdCard());
        tvLevel.setText(vipTypeTxt);
        tvBankcard.setText(bankCard);
        tvBankcardBelong.setText(bankName);
        tvMyFeel.setText(data.getRate1() + "+" + data.getRate3() + "元/笔");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
