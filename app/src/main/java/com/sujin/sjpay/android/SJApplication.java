package com.sujin.sjpay.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.protocol.MyInfoResponse;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import java.util.ArrayList;

/**
 * Created by QJS on 2018/1/31.
 */

public class SJApplication extends Application {

    private static SJApplication instance = null;

    private ArrayList<Activity> oList;
    private MyInfoResponse.MyInfo user;

    public SJApplication() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        oList = new ArrayList<>();
        LogUtils.setDebugMode(AppConstants.DEBUG);
        Logger.setDebug(AppConstants.DEBUG); // 开启NoHttp调试模式。
        Logger.setTag("SJHttp"); // 设置NoHttp打印Log的TAG。
//        NoHttp.initialize(this); // NoHttp默认初始化。
        NoHttp.initialize(InitializationConfig.newBuilder(this)
                // 设置全局连接超时时间，单位毫秒，默认10s。
                .connectionTimeout(30 * 1000)
                // 设置全局服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(30 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        new DBCacheStore(this).setEnable(false) // 如果不使用缓存，设置setEnable(false)禁用。
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。
                .cookieStore(
                        new DBCookieStore(this).setEnable(true) // 如果不维护cookie，设置false禁用。
                )
                // 配置网络层，URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(new URLConnectionNetworkExecutor())
                .build()
        );
    }

    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }

    /**
     * 得到程序的application
     *
     * @return {@link SJApplication}
     */
    public static SJApplication getInstance() {
        return instance;
    }

    /*************************** 全局倒计时 ***************************/
    private static OnTimeCountDownListener onTimeCountDownListener;
    /**
     * 用于发短信倒计时
     */
    private static int counter;
    private static boolean cancelCountDown;

    private static final int WHAT_COUNT_DOWN = 1;
    private static final int COUNTER_TIME = 60;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_COUNT_DOWN:
                    if (counter >= 0) {
                        if (onTimeCountDownListener != null) {
                            onTimeCountDownListener.onTimeCount(counter);
                        }
                    } else {
                        removeMessages(WHAT_COUNT_DOWN);
                        return;
                    }
                    counter--;
                    removeMessages(WHAT_COUNT_DOWN);
                    sendEmptyMessageDelayed(WHAT_COUNT_DOWN, 1000);
            }
        }
    };

    /**
     * 设置倒计时监听器
     *
     * @param listener
     */
    public void setOnTimeCountDownListener(OnTimeCountDownListener listener) {
        onTimeCountDownListener = listener;
    }

    /**
     * 解注册倒计时监听器
     */
    public void unregisterTimeCountDownListener(){
        onTimeCountDownListener = null;
    }

    /**
     * 倒计时监听器
     */
    public interface OnTimeCountDownListener {
        void onTimeCount(int counter);
    }

    /**
     * 开始倒计时
     */
    public void startCountDown() {
        counter = COUNTER_TIME;
        cancelCountDown = false;
        handler.removeMessages(WHAT_COUNT_DOWN);
        handler.sendEmptyMessage(WHAT_COUNT_DOWN);
    }

    /**
     * 取消倒计时
     */
    public void cancelCountDown() {
        handler.removeMessages(WHAT_COUNT_DOWN);
        counter = 0;
        if (onTimeCountDownListener != null) {
            onTimeCountDownListener.onTimeCount(counter);
        }
    }

    /*************************** 全局倒计时 end ************************/

    /**
     * 登录状态
     *
     * @return
     */
    public boolean isLogin() {
        String userID = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).getString(AppConstants.SP_DATA_USER_ID, "");
        return !TextUtils.isEmpty(userID);
    }

    /**
     * 获取用户id
     * @return
     */
    public String getUserId() {
        String userID = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).getString(AppConstants.SP_DATA_USER_ID, "");
        return userID;
    }

    /**
     * 退出登录
     *
     */
    public void logout() {
        SharedPreferences.Editor edit = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).edit();
        edit.clear().commit();
    }

    /**
     * 设置User数据
     *
     * @param u
     */
    public void setUser(MyInfoResponse.MyInfo u) {
        if (u != null) {
            user = u;
        }
    }

    /**
     * 获取User数据
     *
     * @return
     */
    public MyInfoResponse.MyInfo getUser() {
        return user;
    }
}
