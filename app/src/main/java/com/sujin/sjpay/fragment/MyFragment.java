package com.sujin.sjpay.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.AboutUsActivity;
import com.sujin.sjpay.activity.AuthenticateActivity;
import com.sujin.sjpay.activity.ChangePasswordActivity;
import com.sujin.sjpay.activity.ChoseBankCardActivity;
import com.sujin.sjpay.activity.FeeInfoActivity;
import com.sujin.sjpay.activity.GetVipActivity;
import com.sujin.sjpay.activity.InviteActivity;
import com.sujin.sjpay.activity.InviteIncomeActivity;
import com.sujin.sjpay.activity.MyAccountActivity;
import com.sujin.sjpay.activity.MyInfoActivity;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.MyInfoResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_my_secret)
    TextView tvMySecret;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_my_credit_card)
    TextView tvMyCreditCard;
    @BindView(R.id.srl_my)
    SmartRefreshLayout srlMy;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_get_vip)
    TextView tvGetVip;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @BindView(R.id.tv_fee_info)
    TextView tvFeeInfo;
    @BindView(R.id.tv_my_account)
    TextView tvMyAccount;
    @BindView(R.id.tv_invite)
    TextView tvInvite;
    @BindView(R.id.tv_invite_income)
    TextView tvInviteIncome;
    @BindView(R.id.tv_guide_user)
    TextView tvGuideUser;
    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;
    @BindView(R.id.tv_vip_type)
    TextView tvVipType;
    @BindView(R.id.tv_invite_by)
    TextView tvInviteBy;

    private String userId;
    private boolean isJump = true;

    public MyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        this.userId = SJApplication.getInstance().getUserId();
        MyInfoResponse.DataBean user = SJApplication.getInstance().getUser();
        vipType(user.getVipType());
        initView(user);
        srlMy.setRefreshHeader(new MaterialHeader(getContext()));
        srlMy.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isJump = false;
                getMyInfo(MyFragment.this.userId, false);
            }
        });
        return view;
    }

    private void initView(MyInfoResponse.DataBean user) {
        if (user == null) {
            return;
        }
        String realName = user.getRealName();
        String mobile = user.getMobile();
        int isRealState = user.getIsRealState();
        String vipTypeTxt = user.getVipTypeTxt();
        String baseUserName = user.getBaseUserName();
        tvUserName.setText(realName);
        tvUserPhone.setText(mobile);
        tvVipType.setText(vipTypeTxt);
        tvInviteBy.setText("邀请人：" + baseUserName);
        Drawable certified = getResources().getDrawable(R.drawable.icon_certified);
        certified.setBounds( 0, 0, certified.getMinimumWidth(),certified.getMinimumHeight());
        Drawable noCertified = getResources().getDrawable(R.drawable.icon_no_certified);
        noCertified.setBounds( 0, 0, noCertified.getMinimumWidth(),noCertified.getMinimumHeight());

        if (isRealState == 1) {
            tvUserName.setCompoundDrawables(null, null, certified, null);
        } else {
            tvUserName.setCompoundDrawables(null, null, noCertified, null);
        }

    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_my_info, R.id.tv_my_secret, R.id.tv_about_us, R.id.tv_my_credit_card,
            R.id.tv_fee_info, R.id.tv_invite_income, R.id.tv_invite, R.id.tv_get_vip, R.id.tv_my_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_info:
                isJump = true;
                getMyInfo(userId, true);
                break;
            case R.id.tv_my_credit_card:
                Intent intent = new Intent(getActivity(), ChoseBankCardActivity.class);
                intent.putExtra("title", "支付卡管理");
                startActivity(intent);
                break;
            case R.id.tv_my_secret:
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                break;
            case R.id.tv_about_us:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.tv_fee_info:
                startActivity(new Intent(getActivity(), FeeInfoActivity.class));
                break;
            case R.id.tv_invite_income:
                startActivity(new Intent(getActivity(), InviteIncomeActivity.class));
                break;
            case R.id.tv_invite:
                startActivity(new Intent(getActivity(), InviteActivity.class));
                break;
            case R.id.tv_get_vip:
                startActivity(new Intent(getActivity(), GetVipActivity.class));
                break;
            case R.id.tv_my_account:
                startActivity(new Intent(getActivity(), MyAccountActivity.class));
                break;
        }
    }

    private void getMyInfo(String userId, boolean isLoading) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getSingleInfo, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Single, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, true, isLoading);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    MyInfoResponse myInfoResponse = getGson().fromJson(registerJson, MyInfoResponse.class);
                    LogUtils.d("SJHttp", myInfoResponse.getBackStatus() + "");
                    if (myInfoResponse.getBackStatus() == 0) {
                        MyInfoResponse.DataBean data = myInfoResponse.getData();
                        int isRealState = data.getIsRealState();
                        int vipType = data.getVipType();
                        vipType(vipType);
                        if (isJump) {
                            if (isRealState == 0) {
                                // 去认证
                                Intent intent = new Intent(getActivity(), AuthenticateActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                                intent.putExtra("myInfo", myInfoResponse);
                                startActivity(intent);
                            }
                        }
                        SharedPreferences.Editor spUserInfo = getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).edit();
                        spUserInfo.putInt(AppConstants.SP_DATA_IS_REAL_STATE, isRealState);
                        spUserInfo.commit();
                    } else {
                        ToastUtil.show(myInfoResponse.getMessage());
                    }
                    srlMy.finishRefresh(2000, true);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            srlMy.finishRefresh(2000, false);
            LogUtils.d("SJHttp", json);
        }
    };

    private void vipType(int vipType){
        if (vipType == 0 || vipType == 1) {
            setGetVip("", false);
        }else if (vipType == 2) {
            setGetVip(getResources().getString(R.string.get_svip), false);
        }else if (vipType == 3) {
            setGetVip("点击升级会员", true);
        }else if (vipType == 4) {
            setGetVip(getResources().getString(R.string.get_svip), true);
        }
    }

    /**
     * 出来获取会员的显示
     *
     * @param isShowGetVip
     */
    private void setGetVip(String tip, boolean isShowGetVip) {
        tvGetVip.setText(tip);
        if (!isShowGetVip) {
            tvGetVip.setVisibility(View.GONE);
        }
    }
}
