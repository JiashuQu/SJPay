package com.sujin.sjpay.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.sujin.sjpay.activity.AboutUsActivity;
import com.sujin.sjpay.activity.BaseActivity;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.nohttp.HttpResponseListener;
import com.sujin.sjpay.util.AppVersionUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;


/**
 * Created by czb on 2016/7/26.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();
    /**
     * Fragment当前状态是否可见
     */
    public boolean isVisible;
    public Gson gson;
    private Activity mActivity;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }

    //    /**
//     * 创建add Fragment时不会调用，调用hide和show时会调用
//     * @param hidden 是否隐藏
//     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(!hidden) {
//            isVisible = true;
////            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = NoHttp.newRequestQueue(1);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 可见
     */
    public void onVisible() {
        isVisible = true;
        lazyLoad();
    }

    /**
     * 不可见
     */
    public void onInvisible() {
        isVisible = false;
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    public abstract void lazyLoad();


    public boolean getIsVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }






    //-------------- NoHttp -----------//

    /**
     * 用来标记取消。
     */
    private Object object = new Object();

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
        request.add("version", AppVersionUtil.getAppVersion(mActivity));
        mQueue.add(what, request, new HttpResponseListener<>(mActivity, request, callback, canCancel, isLoading));
    }

    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }

    public Gson getGson(){
        if (gson == null){
            gson = new Gson();
        }
        return gson;
    }
}
