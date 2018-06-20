package com.sujin.sjpay.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
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
import com.sujin.sjpay.util.DownLoadImageService;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
import com.sujin.sjpay.view.dialog.GetTopVipDialog;
import com.sujin.sjpay.view.dialog.Tip2ButtonDialog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sujin.sjpay.util.StringUtil.setEllipsize;

public class GetVipActivity extends BaseActivity {
    private static final int WHAT_OPEN_VIP_RULE = 0;
    private static final int MSG_VISIBLE = 1;
    private static final int MSG_ERROR = 2;
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
    @BindView(R.id.tv_get_vip_tips)
    TextView tvGetVipTips;
    @BindView(R.id.tv_vip_one)
    TextView tvVipOne;
    @BindView(R.id.tv_vip_two)
    TextView tvVipTwo;
    @BindView(R.id.tv_vip_three)
    TextView tvVipThree;
    @BindView(R.id.tv_activity_one)
    TextView tvActivityOne;
    @BindView(R.id.tv_activity_one_tip_1)
    TextView tvActivityOneTip1;
    @BindView(R.id.tv_activity_one_tip_2)
    TextView tvActivityOneTip2;
    @BindView(R.id.tv_activity_two)
    TextView tvActivityTwo;
    @BindView(R.id.tv_activity_two_tip)
    TextView tvActivityTwoTip;
    @BindView(R.id.tv_activity_three)
    TextView tvActivityThree;
    @BindView(R.id.tv_activity_three_tip)
    TextView tvActivityThreeTip;

    private String userId;
    private ArrayList<String> list_path, list_title;
    private List<GetVipResponse.DataBean.ListBean> listVIPQr;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_VISIBLE) {
                ToastUtil.show("已保存到本地相册");
            } else if (msg.what == MSG_ERROR) {
                ToastUtil.show("保存失败");
            }
        }
    };

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
                        List<GetVipResponse.DataBean.List2Bean> list2 = getVipResponse.getData().getList2();
                        tvGetVipTips.setText("提示：" + getVipResponse.getData().getTips());
                        initVipVIew(listVIPQr);
                        initVipRule(list2);
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
            LogUtils.d("SJHttp", getResources().getString(R.string.net_error));
        }
    };

    private void initVipRule(List<GetVipResponse.DataBean.List2Bean> list2) {
        tvActivityOne.setText(list2.get(0).getTitle());
        String s = list2.get(0).getContext();
        String []a = s.split("\n");
        tvActivityOneTip1.setText(a[0]);
        tvActivityOneTip2.setText(a[1]);
        tvActivityTwo.setText(list2.get(1).getTitle());
        tvActivityTwoTip.setText(list2.get(1).getContext());
        tvActivityThree.setText(list2.get(2).getTitle());
        tvActivityThreeTip.setText(list2.get(2).getContext());

        setEllipsize(tvActivityOneTip1);
        setEllipsize(tvActivityOneTip2);
//        setEllipsize(tvActivityThreeTip);

    }

    private void initVipVIew(List<GetVipResponse.DataBean.ListBean> listVIPQr) {
        if (listVIPQr == null || listVIPQr.size() < 3) {
            return;
        }
        tvVipOne.setText(listVIPQr.get(0).getTitle());
        tvVipTwo.setText(listVIPQr.get(1).getTitle());
        tvVipThree.setText(listVIPQr.get(2).getTitle());

        tvSvipPrice.setText(listVIPQr.get(0).getCurrentPrice());
        tvVipPrice.setText(listVIPQr.get(1).getCurrentPrice());
        tvVipSvipPrice.setText(listVIPQr.get(2).getCurrentPrice());

        tvSvipOldPrice.setText(listVIPQr.get(0).getOriginalPrice());
        tvVipOldPrice.setText(listVIPQr.get(1).getOriginalPrice());
        tvVipSvipOldPrice.setText(listVIPQr.get(2).getOriginalPrice());
    }

    Tip2ButtonDialog authDialog;
    /**
     * 初始化开通会员二维码轮播图
     *
     * @param listVIPQr
     */
    private void parseBannerUrl(List<GetVipResponse.DataBean.ListBean> listVIPQr) {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        for (int i = 0; i < listVIPQr.size(); i++) {
            list_path.add(listVIPQr.get(i).getQRcode());
        }
        WebBannerAdapter webBannerAdapter = new WebBannerAdapter(this, list_path);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                AndPermission.with(GetVipActivity.this)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                authDialog = DialogUtil.showTip2ButtonDialog(GetVipActivity.this, "是否保存二维码"
                                        , "否"
                                        , null
                                        , "是"
                                        , new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                onDownLoad(list_path.get(position));
                                                authDialog.dismiss();
                                            }
                                        });
                                authDialog.show();
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.show("拒绝访问将不能上传您的证件");
                    }
                }).start();
            }
        });
        recycler.setAdapter(webBannerAdapter);
    }

    /**
     * 启动图片下载线程
     */
    private void onDownLoad(String url) {
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(),url,
                new DownLoadImageService.ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(File file) {
                    }
                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
                        // 在这里执行图片保存方法
                        Message message = new Message();
                        message.what = MSG_VISIBLE;
                        handler.sendMessageDelayed(message, 1000);
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败
                        Message message = new Message();
                        message.what = MSG_ERROR;
                        handler.sendMessageDelayed(message, 1000);
                    }
                });
        //启动图片下载线程
        new Thread(service).start();
    }

    @OnClick(R.id.iv_get_vip)
    public void onViewClicked() {
        final GetTopVipDialog getTopVipDialog = new GetTopVipDialog(GetVipActivity.this, "", "");
        getTopVipDialog.setLiftBankCardListener(new GetTopVipDialog.LiftBankCardListener() {
            @Override
            public void cancel() {
                getTopVipDialog.dismiss();
            }
        });
        getTopVipDialog.show();
    }
}
