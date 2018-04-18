package com.sujin.sjpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.util.AppVersionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        tvAppVersion.setText(getResources().getString(R.string.app_version) + AppVersionUtil.getAppVersion(AboutUsActivity.this));
    }

    @OnClick(R.id.tv_security_logout)
    public void onViewClicked() {
        SJApplication.getInstance().logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
