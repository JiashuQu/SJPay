package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.example.library.banner.BannerLayout;
import com.sujin.sjpay.R;
import com.sujin.sjpay.adapter.WebBannerAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetVipResponse;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
import com.sujin.sjpay.view.dialog.GetTopVipDialog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetVipActivity extends BaseActivity {
    private static final int WHAT_OPEN_VIP_RULE = 0;
    @BindView(R.id.tb_login_title)
    TitleBarView tbLoginTitle;
    @BindView(R.id.tv_vip_type)
    TextView tvVipType;
    @BindView(R.id.tv_price_top)
    TextView tvPriceTop;
    @BindView(R.id.recycler)
    BannerLayout recycler;
    @BindView(R.id.tv_svip_price)
    TextView tvSvipPrice;
    @BindView(R.id.tv_svip_old_price)
    TextView tvSvipOldPrice;
    @BindView(R.id.tv_vip_price)
    TextView tvVipPrice;
    @BindView(R.id.tv_vip_old_price)
    TextView tvVipOldPrice;
    @BindView(R.id.tv_vip_svip_price)
    TextView tvVipSvipPrice;
    @BindView(R.id.tv_vip_svip_old_price)
    TextView tvVipSvipOldPrice;
    @BindView(R.id.iv_get_vip)
    ImageView ivGetVip;

    private String userId;
    private ArrayList<String> list_path, list_title;
    private List<GetVipResponse.DataBean.ListBean> listVIPQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_vip);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
        getOpenVipRule();
        initView();
    }

    @Override
    protected void initView() {
        recycler.setBannerScrollListener(new BannerLayout.BannerScrollListener() {
            @Override
            public void onBannerPosition(int position) {
                tvVipType.setText(listVIPQr.get(position).getTitle());
                tvPriceTop.setText(listVIPQr.get(position).getCurrentPrice());
            }
        });
    }

    //获得首页数据
    public void getOpenVipRule() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getOpenVipRule, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.OpenVipRule, s, ApiConstants.API_PROFIT);
        request.add("UserId", userId);
        request(WHAT_OPEN_VIP_RULE, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_OPEN_VIP_RULE:
                    String getVipJson = response.get();
                    GetVipResponse getVipResponse = getGson().fromJson(getVipJson, GetVipResponse.class);
                    LogUtils.d("SJHttp", getVipResponse.getBackStatus() + "");
                    if (getVipResponse.getBackStatus() == 0) {
                        listVIPQr = getVipResponse.getData().getList();
                        parseBannerUrl(listVIPQr);
                    } else {
                        ToastUtil.show(getVipResponse.getMessage());
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

    /**
     * 初始化开通会员二维码轮播图
     * @param listVIPQr
     */
    private void parseBannerUrl(List<GetVipResponse.DataBean.ListBean> listVIPQr) {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        for (int i = 0; i < listVIPQr.size(); i++){
            list_path.add(listVIPQr.get(i).getQRcode());
        }
        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(this,list_path);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recycler.setAdapter(webBannerAdapter);
    }

    @OnClick(R.id.iv_get_vip)
    public void onViewClicked() {
        final GetTopVipDialog getTopVipDialog = new GetTopVipDialog(GetVipActivity.this);
        getTopVipDialog.setLiftBankCardListener(new GetTopVipDialog.LiftBankCardListener() {
            @Override
            public void cancel() {
                getTopVipDialog.dismiss();
            }
        });
        getTopVipDialog.show();
    }
}
