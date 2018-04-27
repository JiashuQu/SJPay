package com.sujin.sjpay.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.AboutUsActivity;
import com.sujin.sjpay.activity.AuthenticateActivity;
import com.sujin.sjpay.activity.ChangePasswordActivity;
import com.sujin.sjpay.activity.ChoseBankCardActivity;
import com.sujin.sjpay.activity.FeeInfoActivity;
import com.sujin.sjpay.activity.MyInfoActivity;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.MyInfoResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
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

    @BindView(R.id.tv_my_info)
    TextView tvMyInfo;
    Unbinder unbinder;
    @BindView(R.id.tb_login_title)
    TitleBarView tbLoginTitle;
    @BindView(R.id.tv_my_secret)
    TextView tvMySecret;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_my_credit_card)
    TextView tvMyCreditCard;
    @BindView(R.id.srl_my)
    SmartRefreshLayout srlMy;

    private String userId;

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
        SharedPreferences spUserInfo = getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE);
        userId = spUserInfo.getString(AppConstants.SP_DATA_USER_ID, "");
        tbLoginTitle.hideBackButton();
        srlMy.setRefreshHeader(new MaterialHeader(getContext()));
        srlMy.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMyInfo(userId, false);
            }
        });
        return view;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_my_info, R.id.tv_my_secret, R.id.tv_about_us, R.id.tv_my_credit_card, R.id.tv_fee_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_info:
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
                    LogUtils.d("SJHttp", myInfoResponse.getBackStatus());
                    if (TextUtils.equals(myInfoResponse.getBackStatus(), "0")) {
                        MyInfoResponse.MyInfo data = myInfoResponse.getData();
                        int isRealState = data.getIsRealState();
                        if (isRealState == 0) {
                            // 去认证
                            Intent intent = new Intent(getActivity(), AuthenticateActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                            intent.putExtra("myInfo", myInfoResponse);
                            startActivity(intent);
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
}
