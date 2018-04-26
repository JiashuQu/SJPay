package com.sujin.sjpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sujin.sjpay.R;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
import com.sujin.sjpay.view.WebViewBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity implements WebViewBase.OnWebViewJavascriptListener {

    @BindView(R.id.web_web)
    WebViewBase webView;

    String payUrl, title;
    @BindView(R.id.vb_title)
    TitleBarView vbTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            payUrl = intent.getStringExtra("payUrl");
            title = intent.getStringExtra("title");
        }
        initView();
    }

    @Override
    protected void initView() {
        vbTitle.setTitle(title);
        webView.setOnWebViewJavascriptListener(this);
        if (webView != null && !TextUtils.isEmpty(payUrl)) {
            webView.loadUrl(payUrl);
        }
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("backState=0")) {
                    ToastUtil.show("收款成功，款项将很快到您的储蓄卡中:)");
                    finish();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    public void onJavascriptInvoked(String json) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }
}
