package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sujin.sjpay.R;
import com.sujin.sjpay.adapter.InviteListAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.InviteListResponse;
import com.sujin.sjpay.protocol.InviteRuleResponse;
import com.sujin.sjpay.protocol.ShareResponse;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.ScrollzfView;
import com.sujin.sjpay.view.TitleBarView;
import com.sujin.sjpay.view.dialog.InviteDetailDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity {
    private static final int WHAT_INVITE_RULE = 0;
    private static final int WHAT_INVITE_LIST = 1;
    private static final int WHAT_SHARE = 2;

    @BindView(R.id.tb_login_title)
    TitleBarView tbLoginTitle;
    @BindView(R.id.sv)
    ScrollzfView sv;
    @BindView(R.id.tv_invite_rule_one)
    TextView tvInviteRuleOne;
    @BindView(R.id.tv_invite_rule_two)
    TextView tvInviteRuleTwo;
    @BindView(R.id.tv_invite_rule_three)
    TextView tvInviteRuleThree;
    @BindView(R.id.tv_invite_rule_four)
    TextView tvInviteRuleFour;
    @BindView(R.id.iv_invite_share)
    ImageView ivInviteShare;
    @BindView(R.id.tv_invite_detail_btn)
    TextView tvInviteDetailBtn;
    @BindView(R.id.lv_invite)
    ListView lvInvite;
    @BindView(R.id.srl_my_invite)
    SmartRefreshLayout srlMyInvite;
    @BindView(R.id.tv_no_list)
    TextView tvNoList;
    private String userId;
    private List<InviteRuleResponse.DataBean.ComplexBean> complex;
    private List<InviteRuleResponse.DataBean.SimpleBean> simple;

    private List<InviteListResponse.DataBean.ListBean> data;
    private InviteListAdapter adapter;
    private ShareResponse.DataBean shareInfo;
    private int page = 1;
    private static int pageSize = 10;
    private int pages = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
        getInviteRule();
        getInviteList(page, true);
        getShare();
        initView();
    }

    @Override
    protected void initView() {
        sv.setScrollViewListener(new ScrollzfView.ScrollViewListener() {
            @Override
            public void onScrollChanged(float y) {
                Log.d("sc", "sv.getScrollY: " + sv.getScrollY());
                Log.d("sc", "lvInvite.getY(): " + lvInvite.getY());
                if (Math.abs(sv.getScrollY() - lvInvite.getY()) < 1100) {
                    sv.smoothScrollTo(0, (int) lvInvite.getY());
                }

            }
        });


        srlMyInvite.setRefreshHeader(new MaterialHeader(this));
        srlMyInvite.setRefreshFooter(new ClassicsFooter(this));
        srlMyInvite.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getInviteList(page, false);
            }
        });
        srlMyInvite.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getInviteList(page, false);
            }
        });

        data = new ArrayList<>();
        adapter = new InviteListAdapter(this, data);
        lvInvite.setDividerHeight(0);
        lvInvite.setOverScrollMode(View.OVER_SCROLL_NEVER);
        lvInvite.setSelector(R.color.transparent);
        lvInvite.setAdapter(adapter);
    }

    @OnClick({R.id.tv_invite_detail_btn, R.id.iv_invite_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_invite_detail_btn:
                String inviteDetail = StringUtil.EMPTY;
                for (int i = 0; i < complex.size(); i++) {
                    inviteDetail = inviteDetail + complex.get(i).getLine() + "\n\n";
                }
                InviteDetailDialog inviteDetailDialog = new InviteDetailDialog(InviteActivity.this, inviteDetail);
                inviteDetailDialog.show();
                break;
            case R.id.iv_invite_share:
                UMWeb web = new UMWeb(shareInfo.getShareUrl());
                web.setTitle(shareInfo.getTitle());//标题
                web.setThumb(new UMImage(this, shareInfo.getImageUrl()));  //缩略图
                web.setDescription(shareInfo.getContext());//描述
                new ShareAction(InviteActivity.this).withMedia(web).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener).open();
                break;
        }
    }

    //获取邀请规则
    public void getInviteRule() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getInviteRule, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.InviteRule, s, ApiConstants.API_PROFIT);
        request.add("UserId", userId);
        request(WHAT_INVITE_RULE, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    //获取分享内容
    public void getShare() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getShare, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&Soure=" + 1).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Share, s, ApiConstants.API_PROFIT);
        request.add("UserId", userId);
        request.add("Soure", 1);
        request(WHAT_SHARE, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    //邀请列表
    private void getInviteList(int page, boolean isLoading) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getInviteList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&pageIndex=" + page + "&pageSize=" + pageSize).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.InviteList, s, ApiConstants.API_PROFIT);
        request.add("UserId", userId);
        request.add("pageIndex", page);
        request.add("pageSize", pageSize);
        request(WHAT_INVITE_LIST, request, httpListener, md5, false, isLoading);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    private boolean hasMoreData = false;
    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_INVITE_RULE:
                    String inviteRuleJson = response.get();
                    InviteRuleResponse inviteRuleResponse = getGson().fromJson(inviteRuleJson, InviteRuleResponse.class);
                    LogUtils.d("SJHttp", inviteRuleResponse.getBackStatus() + "");
                    if (inviteRuleResponse.getBackStatus() == 0) {
                        complex = inviteRuleResponse.getData().getComplex();
                        simple = inviteRuleResponse.getData().getSimple();
                        tvInviteRuleOne.setText(simple.get(0).getLine());
                        tvInviteRuleTwo.setText(simple.get(1).getLine());
                        tvInviteRuleThree.setText(simple.get(2).getLine());
                        tvInviteRuleFour.setText(simple.get(3).getLine());
                    } else {
                        ToastUtil.show(inviteRuleResponse.getMessage());
                    }
                    break;
                case WHAT_INVITE_LIST:
                    String inviteListJson = response.get();
                    InviteListResponse inviteListResponse = getGson().fromJson(inviteListJson, InviteListResponse.class);
                    LogUtils.d("SJHttp", inviteListResponse.getBackStatus() + "");
                    if (inviteListResponse.getBackStatus() == 0) {
                        if (page == 1) {
                            data.clear();
                            hasMoreData = false;
                        }
                        page++;
                        pages = inviteListResponse.getData().getPageCount();
                        List<InviteListResponse.DataBean.ListBean> list = inviteListResponse.getData().getList();
                        if (list != null && list.size() != 0) {
                            for (int i = 0; i < list.size(); i++) {
                                data.add(list.get(i));
                            }
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
//                            listPayList.setSelection(0);
                        } else {
                            tvNoList.setVisibility(View.VISIBLE);
                            lvInvite.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtil.show(inviteListResponse.getMessage());
                    }
                    if (page > pages) {
                        hasMoreData = true;
                    }
                    srlMyInvite.finishRefresh(1000, true);
                    srlMyInvite.finishLoadMore(1000, true, hasMoreData);
                    break;
                case WHAT_SHARE:
                    String shareJson = response.get();
                    ShareResponse shareResponse = getGson().fromJson(shareJson, ShareResponse.class);
                    LogUtils.d("SJHttp", shareResponse.getBackStatus() + "");
                    if (shareResponse.getBackStatus() == 0) {
                        shareInfo = shareResponse.getData();
                    } else {
                        ToastUtil.show(shareResponse.getMessage());
                    }
                    break;
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            srlMyInvite.finishRefresh(1000, true);
            srlMyInvite.finishLoadMore(1000, true, hasMoreData);
            DialogUtil.dismissLoading();
            LogUtils.d("SJHttp", json);
        }
    };


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            new ShareAction(InviteActivity.this).withText("hello").share();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };
}
