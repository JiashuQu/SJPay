package com.sujin.sjpay.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
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

public class FeeInfoActivity extends BaseActivity {

    @BindView(R.id.img_titlebar_back)
    ImageView imgTitlebarBack;
    @BindView(R.id.tv_titlebar_right)
    TextView tvTitlebarRight;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_get_vip)
    TextView tvGetVip;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @BindView(R.id.tv_vip_one)
    TextView tvVipOne;
    @BindView(R.id.tv_vip_two)
    TextView tvVipTwo;
    @BindView(R.id.tv_vip_three)
    TextView tvVipThree;
    @BindView(R.id.tv_integral_quota)
    TextView tvIntegralQuota;
    @BindView(R.id.tv_integral_fee)
    TextView tvIntegralFee;
    @BindView(R.id.tv_no_integral_quota)
    TextView tvNoIntegralQuota;
    @BindView(R.id.tv_no_integral_fee)
    TextView tvNoIntegralFee;
    @BindView(R.id.tv_get_proxy)
    TextView tvGetProxy;

    private String userId;
    private MyInfoResponse.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_info);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
        getMyInfo(userId);
        initView();
    }

    @Override
    protected void initView() {

    }

    private void getMyInfo(String userId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getSingleInfo, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Single, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, true, true);
    }

    private void getVipTypeIntroduce(int vipType) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getVipTypeIntroduce, RequestMethod.GET);
        char[] chars = ("VipType=" + vipType).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.GetVipTypeIntroduce, s, ApiConstants.API_CONFIG);
        request.add("VipType", vipType);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---" + md5);
        request(1, request, httpListener, md5, true, true);
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
                        data = myInfoResponse.getData();
                        int isRealState = data.getIsRealState();
                        SharedPreferences.Editor spUserInfo = getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).edit();
                        spUserInfo.putInt(AppConstants.SP_DATA_IS_REAL_STATE, isRealState);
                        spUserInfo.commit();
                        tvUserName.setText(data.getRealName());
                        int vipType = data.getVipType();
                        if (vipType == 0 || vipType == 1) {
                            setGetVip("你已经是最牛逼的了", false, false, false, vipType);
                        }else if (vipType == 2) {
                            setGetVip(getResources().getString(R.string.get_svip), true, true, true, vipType);
                        }else if (vipType == 3) {
                            setGetVip(getResources().getString(R.string.get_svip), true, true, true, vipType);
                        }else if (vipType == 4) {
                            setGetVip(getResources().getString(R.string.get_svip), true, true, true, vipType);
                        }
                        setBtn(1);
                    } else {
                        ToastUtil.show(myInfoResponse.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            LogUtils.d("SJHttp", json);
        }
    };

    @OnClick({R.id.iv_user_icon, R.id.tv_get_vip, R.id.tv_vip_one, R.id.tv_vip_two, R.id.tv_vip_three, R.id.tv_get_proxy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                break;
            case R.id.tv_get_vip:
                break;
            case R.id.tv_vip_one:
                getVipTypeIntroduce(4);
                setBtn(1);
                break;
            case R.id.tv_vip_two:
                setBtn(2);
                break;
            case R.id.tv_vip_three:
                setBtn(3);
                break;
            case R.id.tv_get_proxy:
                break;
        }
    }

    /**
     * 出来获取会员的显示
     *
     * @param info
     * @param isShowIcon
     */
    private void setGetVip(String info, boolean isShowGetVip, boolean isShowIcon, boolean isShowGetProxy, int whichIcon) {
        tvGetVip.setText(info);
        if (!isShowGetVip) {
            tvGetVip.setVisibility(View.GONE);
        }
        if (isShowIcon) {
            Drawable certified = getResources().getDrawable(R.drawable.icon_right);
            certified.setBounds(0, 0, certified.getMinimumWidth(), certified.getMinimumHeight());
            tvGetVip.setCompoundDrawables(null, null, certified, null);
        }

        if (!isShowGetProxy) {
            tvGetProxy.setVisibility(View.GONE);
        }

        Drawable userType = null;
        if (whichIcon == 2 || whichIcon == 1 || whichIcon == 0) {
            userType = getResources().getDrawable(R.drawable.user_svip);
        }else if (whichIcon == 3) {
            userType = getResources().getDrawable(R.drawable.user_vip);
        }else if (whichIcon == 4) {
            userType = getResources().getDrawable(R.drawable.user_common);
        }

        if (userType != null) {
            userType.setBounds(0, 0, userType.getMinimumWidth(), userType.getMinimumHeight());
            tvUserName.setCompoundDrawables(null, null, userType, null);
        }
    }

    /**
     * 设置按钮颜色和内容
     *
     * @param i    哪个按钮
     */
    private void setBtn(int i) {

        String vipTypeTxt = data.getVipTypeTxt();
        String rate1 = data.getRate1();
        String rate3 = data.getRate3();
        String noneRate1 = data.getNoneRate1();
        String noneRate3 = data.getNoneRate3();
        tvIntegralFee.setText(rate1 + rate3 + "元/每笔");
        tvNoIntegralFee.setText(noneRate1 + noneRate3 + "元/每笔");

        switch (i) {
            case 1:
                tvVipOne.setBackgroundResource(R.drawable.bg_vip_info_light);
                tvVipTwo.setBackgroundResource(R.drawable.bg_vip_info_dark);
                tvVipThree.setBackgroundResource(R.drawable.bg_vip_info_dark);
                break;
            case 2:
                tvVipOne.setBackgroundResource(R.drawable.bg_vip_info_dark);
                tvVipTwo.setBackgroundResource(R.drawable.bg_vip_info_light);
                tvVipThree.setBackgroundResource(R.drawable.bg_vip_info_dark);
                break;
            case 3:
                tvVipOne.setBackgroundResource(R.drawable.bg_vip_info_dark);
                tvVipTwo.setBackgroundResource(R.drawable.bg_vip_info_dark);
                tvVipThree.setBackgroundResource(R.drawable.bg_vip_info_light);
                break;
        }

    }
}
