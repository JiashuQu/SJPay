package com.sujin.sjpay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.bumptech.glide.Glide;
import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.InviteActivity;
import com.sujin.sjpay.activity.WebActivity;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.BannerListResponse;
import com.sujin.sjpay.protocol.IndexDataResponse;
import com.sujin.sjpay.util.AppVersionUtil;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.dialog.Face2FaceInviteDialog;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements OnBannerListener {
    private static final int WHAT_HOME_INFO = 0;
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
    @BindView(R.id.iv_invite)
    ImageView ivInvite;
    @BindView(R.id.iv_my_income)
    ImageView ivMyIncome;
    private ArrayList<String> list_path, list_title;

    private String userId;
    private List<BannerListResponse.DataBean> data;
    private List<IndexDataResponse.DataBean.BtnListBean> bannerList;

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
    }

    //获得首页数据
    public void getGetIndexData() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getGetIndexData, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.GetIndexData, s, ApiConstants.API_CONFIG);
        request.add("UserId", userId);
        request(WHAT_HOME_INFO, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    //获取banner列表
    public void getBannerList() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getBannerList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.BannerList, s, ApiConstants.API_CONFIG);
        request.add("UserId", userId);
        request(WHAT_BANNER, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    //获取邀请二维码
    public void getQR() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getQRcode, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.QRcode, s, ApiConstants.API_PROFIT);
        request.add("itormName", "itormandroid");
        request.add("sign", md5);
        request.add("UserId", userId);
        request.add("version", AppVersionUtil.getAppVersion(getActivity()));
        Face2FaceInviteDialog face2FaceInviteDialog = new Face2FaceInviteDialog(getActivity(), SJApplication.getInstance().getUser().getMobile(), request.url());
        face2FaceInviteDialog.setCancelable(true);
        face2FaceInviteDialog.show();
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_HOME_INFO:
                    String homeInfoJson = response.get();
                    IndexDataResponse indexDataResponse = getGson().fromJson(homeInfoJson, IndexDataResponse.class);
                    LogUtils.d("SJHttp", indexDataResponse.getBackStatus() + "");
                    if (indexDataResponse.getBackStatus() == 0) {
                        bannerList = indexDataResponse.getData().getBtnList();
                    } else {
                        ToastUtil.show(indexDataResponse.getMessage());
                    }
                    break;
                case WHAT_BANNER:
                    String yeePayRegisterJson = response.get();
                    BannerListResponse bannerListResponse = getGson().fromJson(yeePayRegisterJson, BannerListResponse.class);
                    LogUtils.d("SJHttp", bannerListResponse.getBackStatus() + "");
                    if (bannerListResponse.getBackStatus() == 0) {
                        data = bannerListResponse.getData();
                        initBanner(data);
                    } else {
                        ToastUtil.show(bannerListResponse.getMessage());
                    }
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

    //首页按钮
    private void refresh(List<IndexDataResponse.DataBean.BtnListBean> bannerList) {

    }

    //banner
    private void initBanner(List<BannerListResponse.DataBean> bannerList) {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++){
            list_path.add(bannerList.get(i).getImgUrl());
        }
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
        intent.putExtra("payUrl", data.get(position).getWapURL());
        intent.putExtra("title", data.get(position).getTitle());
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
            case R.id.iv_invite:
                getQR();
                break;
            case R.id.iv_my_income:
                intent = new Intent(getActivity(), InviteActivity.class);
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
