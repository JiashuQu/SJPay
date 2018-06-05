package com.sujin.sjpay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.bumptech.glide.Glide;
import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.WebActivity;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.UploadImgResponse;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements OnBannerListener {
    private static final int WHAT_HOME_INFI = 0;
    private static final int WHAT_BANNER = 1;

    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.iv_loan)
    ImageView ivLoan;
    @BindView(R.id.iv_get_card)
    ImageView ivGetCard;
    @BindView(R.id.iv_card_raiders)
    ImageView ivCardRaiders;
    @BindView(R.id.iv_pay_raiders)
    ImageView ivPayRaiders;
    private ArrayList<String> list_path, list_title;

    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        userId = SJApplication.getInstance().getUserId();
        initView();
        return view;
    }

    private void initView() {
        getGetIndexData();
        getBannerList();
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("file:///android_asset/banner01.png");
        list_title.add("上线送好礼");
//        list_title.add("天天向上");
//        list_title.add("热爱劳动");
//        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();

    }

    //获得首页数据
    public void getGetIndexData(){// TODO
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getGetIndexData, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.GetIndexData, s, ApiConstants.API_CONFIG);
        request.add("UserId", userId);
        request(WHAT_HOME_INFI, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    //获取banner列表
    public void getBannerList() {// TODO
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getBannerList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.BannerList, s, ApiConstants.API_CONFIG);
        request.add("UserId", userId);
        request(WHAT_BANNER, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_HOME_INFI:
                    String yeePayRegisterJson = response.get();
                    UploadImgResponse yeePayRegister = getGson().fromJson(yeePayRegisterJson, UploadImgResponse.class);
                    LogUtils.d("SJHttp", yeePayRegister.getBackStatus());
                    if (TextUtils.equals(yeePayRegister.getBackStatus(), "0")) {
                        ToastUtil.show("实名认证成功");
//                        startActivity(new Intent(BandCardActivity.this, MyInfoActivity.class));
                    } else {
                        ToastUtil.show(yeePayRegister.getMessage());
                    }
                    DialogUtil.dismissLoading();
                    break;
                case WHAT_BANNER:
//                    String yeePayRegisterJson = response.get();
//                    UploadImgResponse yeePayRegister = getGson().fromJson(yeePayRegisterJson, UploadImgResponse.class);
//                    LogUtils.d("SJHttp", yeePayRegister.getBackStatus());
//                    if (TextUtils.equals(yeePayRegister.getBackStatus(), "0")) {
//                        ToastUtil.show("实名认证成功");
////                        startActivity(new Intent(BandCardActivity.this, MyInfoActivity.class));
//                    } else {
//                        ToastUtil.show(yeePayRegister.getMessage());
//                    }
//                    DialogUtil.dismissLoading();
                    break;
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            DialogUtil.dismissLoading();
            LogUtils.d("SJHttp", json);
        }
    };

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第" + position + "张轮播图");
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("payUrl", "file:///android_asset/gift_activity.png");
        intent.putExtra("title", "上线送好礼");
        startActivity(intent);
    }

    @OnClick({R.id.iv_loan, R.id.iv_get_card, R.id.iv_card_raiders, R.id.iv_pay_raiders, R.id.iv_invite, R.id.iv_my_income})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_loan:
                intent = new Intent(getActivity(), WebActivity.class);
//            intent.putExtra("payUrl", "http://www.51ley.com/front/loan?uid=fc42c00a05784006aefe49dc0c12fa57&rm=1&time=1521019589327");
                intent.putExtra("payUrl", "http://www.51ley.com/f/loan/index?uid=fc42c00a05784006aefe49dc0c12fa57&rm=1");
                intent.putExtra("title", "精选网贷");
                break;
            case R.id.iv_get_card:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("payUrl", "http://www.51ley.com/f/card/index?uid=fc42c00a05784006aefe49dc0c12fa57&rm=1");
                intent.putExtra("title", "极速办卡");
                break;
            case R.id.iv_card_raiders:
                intent = new Intent(getActivity(), WebActivity.class);
//                intent.putExtra("payUrl", "https://credit.u51.com/shenqing/25322.html");
                intent.putExtra("payUrl", "file:///android_asset/get_card.html");
                intent.putExtra("title", "办卡攻略");
                break;
            case R.id.iv_pay_raiders:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("payUrl", "file:///android_asset/pay_skills.html");
                intent.putExtra("title", "收款攻略");
                break;
            case R.id.iv_invite:// TODO
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("payUrl", "file:///android_asset/pay_skills.html");
                intent.putExtra("title", "收款攻略");
                break;
            case R.id.iv_my_income:// TODO
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("payUrl", "file:///android_asset/pay_skills.html");
                intent.putExtra("title", "收款攻略");
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
