package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.IncomeListResponse;
import com.sujin.sjpay.protocol.IncomeTotalResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.dialog.GetTopVipDialog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteIncomeActivity extends BaseActivity {
    private static final int WHAT_INCOME_TOTAL = 0;
    private static final int WHAT_INCOME_LIST = 1;

    @BindView(R.id.img_titlebar_back)
    ImageView imgTitlebarBack;
    @BindView(R.id.tv_invite_info)
    TextView tvInviteInfo;
    @BindView(R.id.tv_last_month_income)
    TextView tvLastMonthIncome;
    @BindView(R.id.tv_this_month_income)
    TextView tvThisMonthIncome;
    @BindView(R.id.srl_income_list)
    SmartRefreshLayout srlIncomeList;
    @BindView(R.id.tv_income_total)
    TextView tvIncomeTotal;

    private IncomeTotalResponse.DataBean data;
    private String userId;
    private String lastMounthIncomeTip;
    private int page = 1;
    private static int pageSize = 10;
    private int pages = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_income);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
        getIncomeTotal(userId);
        getIncomeList(userId, page, true);
        initView();
    }

    private void getIncomeTotal(String userId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getIncomeTotal, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.IncomeTotal, s, ApiConstants.API_PROFIT);
        request.add("UserId", userId);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---" + md5);
        request(WHAT_INCOME_TOTAL, request, httpListener, md5, true, true);
    }

    private void getIncomeList(String userId, int page, boolean isLoading) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getIncomeList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&pageIndex=" + page + "&pageSize=" + pageSize).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.IncomeList, s, ApiConstants.API_PROFIT);
        request.add("UserId", userId);
        request.add("pageIndex", page);
        request.add("pageSize", pageSize);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId + "&pageIndex=" + page + "&pageSize=" + pageSize + "---" + s + "---" + md5);
        request(WHAT_INCOME_LIST, request, httpListener, md5, true, isLoading);
    }

    @Override
    protected void initView() {
        srlIncomeList.setRefreshHeader(new MaterialHeader(this));
        srlIncomeList.setRefreshFooter(new ClassicsFooter(this));
        srlIncomeList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getIncomeList(SJApplication.getInstance().getUserId(), page, false);
            }
        });
        srlIncomeList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getIncomeList(SJApplication.getInstance().getUserId(), page, false);
            }
        });
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_INCOME_TOTAL:
                    String incomeTotalJson = response.get();
                    IncomeTotalResponse incomeTotalResponse = getGson().fromJson(incomeTotalJson, IncomeTotalResponse.class);
                    LogUtils.d("SJHttp", incomeTotalResponse.getBackStatus() + "");
                    if (incomeTotalResponse.getBackStatus() == 0) {
                        IncomeTotalResponse.DataBean data = incomeTotalResponse.getData();
                        tvIncomeTotal.setText(data.getTotalIncome());
                        tvLastMonthIncome.setText(data.getLastMounthIncome());
                        tvThisMonthIncome.setText(data.getCurrentMounthIncome());
                        lastMounthIncomeTip = data.getLastMounthIncomeTip();
                    } else {
                        ToastUtil.show(incomeTotalResponse.getMessage());
                    }
                    break;
                case WHAT_INCOME_LIST:
                    String vipTypeIntroduceJson = response.get();
                    IncomeListResponse incomeListResponse = getGson().fromJson(vipTypeIntroduceJson, IncomeListResponse.class);
                    LogUtils.d("SJHttp", incomeListResponse.getBackStatus() + "");
                    if (incomeListResponse.getBackStatus() == 0) {
                    } else {
                        ToastUtil.show(incomeListResponse.getMessage());
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

    @OnClick({R.id.img_titlebar_back, R.id.tv_invite_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_titlebar_back:
                finish();
                break;
            case R.id.tv_invite_info:
                final GetTopVipDialog getTopVipDialog = new GetTopVipDialog(this, lastMounthIncomeTip);
                getTopVipDialog.setLiftBankCardListener(new GetTopVipDialog.LiftBankCardListener() {
                    @Override
                    public void cancel() {
                        getTopVipDialog.dismiss();
                    }
                });
                getTopVipDialog.show();
                break;
        }
    }
}
