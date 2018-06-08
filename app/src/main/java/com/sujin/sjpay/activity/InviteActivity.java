package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.adapter.InviteListAdapter;
import com.sujin.sjpay.adapter.PayListAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.InviteListResponse;
import com.sujin.sjpay.protocol.InviteRuleResponse;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
import com.sujin.sjpay.view.dialog.InviteDetailDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
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

    @BindView(R.id.tb_login_title)
    TitleBarView tbLoginTitle;
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
    private String userId;
    private List<InviteRuleResponse.DataBean.ComplexBean> complex;
    private List<InviteRuleResponse.DataBean.SimpleBean> simple;

    private List<InviteListResponse.DataBean.ListBean> data;
    private InviteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
        getInviteRule();
        getInviteList();
        initView();
    }

    @Override
    protected void initView() {
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
                new ShareAction(InviteActivity.this).withText("hello").setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
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

    //邀请列表
    private void getInviteList() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getInviteList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.InviteList, s, ApiConstants.API_PROFIT);
        request.add("UserId", userId);
        request(WHAT_INVITE_LIST, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

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
                        data = inviteListResponse.getData().getList();
                        if (data != null || data.size() == 0) {
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.show("暂可用银行卡");
                        }
                    } else {
                        ToastUtil.show(inviteListResponse.getMessage());
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
