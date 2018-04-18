package com.sujin.sjpay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.util.UIUtil;

/**
 * @author chengzibo
 *         <p/>
 *         2016-4-8
 */
public class WebViewBase extends WebView {

    private OnWebViewJavascriptListener onWebViewJavascriptListener;
    private OnWebViewListener onWebViewListener;

    private ProgressBar progressbar;

    private AlphaAnimation alphaAnimation;

    private boolean isFirst = true;

    private static final int DURATION = 700;

    public WebViewBase(Context context) {
        this(context, null);
    }

    public WebViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSettings();
        requestFocus();
        setSaveEnabled(true);
        // JS调java
        JsToCalljava();

        initAnimation();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WebViewBase);
        boolean showProgressBar = a.getBoolean(R.styleable.WebViewBase_show_progress, false);
        a.recycle();
        if (showProgressBar) {
            progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, UIUtil.dip2px(1), 0, 0));
            progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.bg_progressbar));
            addView(progressbar);
            progressbar.setVisibility(GONE);
        }
        setWebChromeClient(new WebChromeClient());
    }

    private void initSettings() {
        WebSettings webSettings = getSettings();
        setWebViewClient(new MyWebViewClient());
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // http 与 https请求混合模式
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public void loadUrl(String url) {
        isFirst = true;
        super.loadUrl(url);
        LogUtils.d("SJHttp", "WebViewBase loadUrl = " + url);
    }

    public void loadUrlWithoutAnimation(String url){
        loadUrl(url);
        isFirst = false;
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (onWebViewListener != null) {
                onWebViewListener.onPageStarted(view, url, favicon);
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (onWebViewListener != null) {
                return onWebViewListener.shouldOverrideUrlLoading(view, url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (onWebViewListener != null) {
                onWebViewListener.onPageFinished(view, url);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (onWebViewListener != null) {
                onWebViewListener.onReceivedError(view, errorCode, description, failingUrl);
            }
        }

    }

    private void JsToCalljava() {
        addJavascriptInterface(new WebViewJavascriptBridge(), "WebViewJavascriptBridge");
    }

    // <a id="b" data-role="button" onclick="window.WebViewJavascriptBridge.getJson('returnmainpage')">跳转到activity</a>
    public class WebViewJavascriptBridge {
        @JavascriptInterface
        public String getJson(String type) {
            // 判断type然后调到不同的页面
            if (onWebViewJavascriptListener != null) {
                onWebViewJavascriptListener.onJavascriptInvoked(type);
            }
            return null;

        }
    }

    public interface OnWebViewJavascriptListener {
        void onJavascriptInvoked(String json);
    }

    public void setOnWebViewJavascriptListener(OnWebViewJavascriptListener listener) {
        this.onWebViewJavascriptListener = listener;
    }

    public interface OnWebViewListener {
        void onPageStarted(WebView view, String url, Bitmap favicon);

        boolean shouldOverrideUrlLoading(WebView view, String url);

        void onPageFinished(WebView view, String url);

        void onReceivedError(WebView view, int errorCode, String description, String failingUrl);
    }

    public void setOnWebViewListener(OnWebViewListener onWebViewListener) {
        this.onWebViewListener = onWebViewListener;
    }


    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            LogUtils.d(AppConstants.LOG_TAG_JJY, "onProgressChanged  Progress = " + newProgress);
            if (newProgress >= 50){
//                LogUtils.d(AppConstants.LOG_TAG_JJY, "isFirst = " + isFirst);
                if (isFirst){
//                    LogUtils.d(AppConstants.LOG_TAG_JJY, "执行动画");
                    isFirst = false;
                    if (alphaAnimation == null) {
                        initAnimation();
                    }
                    startAnimation(alphaAnimation);
                }
            }
            if (progressbar != null){
                if (newProgress == 100) {
                    progressbar.setProgress(100);
                    progressbar.setVisibility(View.GONE);
                } else {
                    if (progressbar.getVisibility() == GONE)
                        progressbar.setVisibility(VISIBLE);
                    progressbar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (progressbar != null) {
            LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
            lp.x = l;
            lp.y = t;
            progressbar.setLayoutParams(lp);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private void initAnimation(){
        alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(DURATION);//设置动画持续时间
    }


}
