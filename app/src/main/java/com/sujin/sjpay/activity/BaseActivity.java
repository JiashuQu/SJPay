package com.sujin.sjpay.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.google.gson.Gson;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.nohttp.HttpResponseListener;
import com.sujin.sjpay.util.AppVersionUtil;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import java.util.Stack;

/**
 * Created by QJS on 2016/6/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private static Stack<BaseActivity> stackActivities = new Stack<>();
    private static Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addActivity(this);
        super.onCreate(savedInstanceState);
        Logger.e(getClass().getName());
        LogUtils.d(TAG, "!-- onCreate --!");
        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue(1);
        SJApplication.getInstance().addActivity_(this);
    }

    //-------------- NoHttp -----------//

    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request, HttpListener<T> callback, String md5,
                                   boolean canCancel, boolean isLoading) {
        request.setCancelSign(object);
        request.add("itormName", "itormandroid");
        request.add("sign", md5);
        request.add("version", AppVersionUtil.getAppVersion(this));
        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }

    @Override
    protected void onDestroy() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(object);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();
        removeActivity(this);
        super.onDestroy();
    }

    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }
    protected abstract void initView();

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d(TAG, "!-- onRestart --!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "!-- onResume --!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, "!-- onPause --!");
    }

    /**
     * 界面Activity入栈
     * @param activity
     */
    private static void addActivity(BaseActivity activity) {
        if (activity == null) {
            return;
        }
        stackActivities.push(activity);
    }

    /**
     * 界面Activity移出栈
     * @param activity
     */
    private static void removeActivity(BaseActivity activity) {
        if (activity == null) {
            return;
        }

        if (stackActivities.contains(activity)) {
            stackActivities.remove(activity);
        }
    }

    /**
     * 获取当前Activity的Context
     *
     * @return
     */
    public Context getContext() {
        return BaseActivity.this;
    }

    public Gson getGson(){
        if (gson == null){
            gson = new Gson();
        }
        return gson;
    }

}
