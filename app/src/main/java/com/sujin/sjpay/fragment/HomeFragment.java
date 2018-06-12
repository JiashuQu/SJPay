package com.sujin.sjpay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.bumptech.glide.Glide;
import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.InviteActivity;
import com.sujin.sjpay.activity.InviteIncomeActivity;
import com.sujin.sjpay.activity.MyAccountActivity;
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
    @BindView(R.id.ll_invite)
    LinearLayout llInvite;
    @BindView(R.id.ll_invite_income)
    LinearLayout llInviteIncome;
    @BindView(R.id.ll_loan)
    LinearLayout llLoan;
    @BindView(R.id.ll_get_card)
    LinearLayout llGetCard;
    @BindView(R.id.ll_card_raiders)
    LinearLayout llCardRaiders;
    @BindView(R.id.ll_pay_raiders)
    LinearLayout llPayRaiders;
    @BindView(R.id.tv_invite)
    TextView tvInvite;
    @BindView(R.id.tv_my_income)
    TextView tvMyIncome;
    @BindView(R.id.tv_loan)
    TextView tvLoan;
    @BindView(R.id.tv_get_card)
    TextView tvGetCard;
    @BindView(R.id.tv_card_raiders)
    TextView tvCardRaiders;
    @BindView(R.id.tv_pay_raiders)
    TextView tvPayRaiders;
    private ArrayList<String> list_path, list_title;

    private String userId;
    private List<BannerListResponse.DataBean> data;
    private List<IndexDataResponse.DataBean.BtnListBean> btnList;

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
                        btnList = indexDataResponse.getData().getBtnList();
                        Glide.with(getActivity()).asBitmap().load(btnList.get(0).getIcon()).into(ivInvite);
                        Glide.with(getActivity()).asBitmap().load(btnList.get(1).getIcon()).into(ivMyIncome);
                        Glide.with(getActivity()).asBitmap().load(btnList.get(2).getIcon()).into(ivCardRaiders);
                        Glide.with(getActivity()).asBitmap().load(btnList.get(3).getIcon()).into(ivPayRaiders);
                        Glide.with(getActivity()).asBitmap().load(btnList.get(4).getIcon()).into(ivLoan);
                        Glide.with(getActivity()).asBitmap().load(btnList.get(5).getIcon()).into(ivGetCard);
                        tvInvite.setText(btnList.get(0).getTitle());
                        tvMyIncome.setText(btnList.get(1).getTitle());
                        tvLoan.setText(btnList.get(2).getTitle());
                        tvGetCard.setText(btnList.get(3).getTitle());
                        tvCardRaiders.setText(btnList.get(4).getTitle());
                        tvPayRaiders.setText(btnList.get(5).getTitle());

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

    //banner
    private void initBanner(List<BannerListResponse.DataBean> bannerList) {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
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
        String wapURL = data.get(position).getWapURL();
        Intent intent = null;
        if (wapURL.contains("http")) {
            intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("payUrl", wapURL);
        }else {
            if (wapURL.equals("HuoBao")) {
                intent = new Intent(getActivity(), WebActivity.class);
                wapURL = "file:///android_asset/gift_activity.png";
                intent.putExtra("payUrl", wapURL);
            }else if (wapURL.equals("InviteIncome")) {
                intent = new Intent(getActivity(), InviteIncomeActivity.class);
            }else if (wapURL.equals("QRcode")) {
                getQR();
            }else if (wapURL.equals("Invite")) {
                intent = new Intent(getActivity(), InviteActivity.class);
            }else if (wapURL.equals("AccountRecord")) {
                intent = new Intent(getActivity(), MyAccountActivity.class);
            }
        }
        if (intent != null) {
            intent.putExtra("title", data.get(position).getTitle());
            startActivity(intent);
        }
    }


    @OnClick({R.id.ll_invite, R.id.ll_invite_income, R.id.ll_loan, R.id.ll_get_card, R.id.ll_card_raiders, R.id.ll_pay_raiders})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_invite:
                setBtn(0);
                break;
            case R.id.ll_invite_income:
                setBtn(1);
                break;
            case R.id.ll_loan:
                setBtn(2);
                break;
            case R.id.ll_get_card:
                setBtn(3);
                break;
            case R.id.ll_card_raiders:
                setBtn(4);
                break;
            case R.id.ll_pay_raiders:
                setBtn(5);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void setBtn(int i) {
        Log.i("tag", "你点了第" + i + "个按钮");
        String wapURL = btnList.get(i).getWapUrl();
        Intent intent = null;
        if (wapURL.contains("http")) {
            intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("payUrl", wapURL);
        }else {
            if (wapURL.equals("HuoBao")) {
                intent = new Intent(getActivity(), WebActivity.class);
                wapURL = "file:///android_asset/gift_activity.png";
                intent.putExtra("payUrl", wapURL);
            }else if (wapURL.equals("InviteIncome")) {
                intent = new Intent(getActivity(), InviteIncomeActivity.class);
            }else if (wapURL.equals("QRcode")) {
                getQR();
            }else if (wapURL.equals("Invite")) {
                intent = new Intent(getActivity(), InviteActivity.class);
            }else if (wapURL.equals("AccountRecord")) {
                intent = new Intent(getActivity(), MyAccountActivity.class);
            }
        }
        if (intent != null) {
            intent.putExtra("title", btnList.get(i).getTitle());
            startActivity(intent);
        }
    }

    private void setBtn(int i, Intent intent) {
        String wapUrl = btnList.get(i).getWapUrl();
        if (wapUrl.contains("http")) {
            intent.putExtra("payUrl", wapUrl);
        }else {
            if (wapUrl.equals("HuoBao")) {
                wapUrl = "file:///android_asset/gift_activity.png";
                intent.putExtra("payUrl", wapUrl);
            }
        }
        intent.putExtra("title", btnList.get(i).getTitle());
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
