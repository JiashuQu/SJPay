package com.sujin.sjpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.protocol.LoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySecretActivity extends BaseActivity {

    @BindView(R.id.tv_my_info)
    TextView tvMyInfo;
    @BindView(R.id.tv_security_logout)
    TextView tvSecurityLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_secret);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.tv_my_info, R.id.tv_security_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_info:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.tv_security_logout:
                SJApplication.getInstance().logout();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
