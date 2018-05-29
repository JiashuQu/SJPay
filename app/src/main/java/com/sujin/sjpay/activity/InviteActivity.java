package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.iv_invite_share)
    public void onViewClicked() {
        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();
    }

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
