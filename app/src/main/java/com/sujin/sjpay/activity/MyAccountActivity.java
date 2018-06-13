package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.sujin.sjpay.adapter.MyAccountAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.AccountRecordResponse;
import com.sujin.sjpay.protocol.AccountTotalResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
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

public class MyAccountActivity extends BaseActivity {
    private static final int WHAT_ACCOUNT_TOTAL = 0;
    private static final int WHAT_ACCOUNT_RECORD = 1;

    @BindView(R.id.img_titlebar_back)
    ImageView imgTitlebarBack;
    @BindView(R.id.tv_titlebar_right)
    TextView tvTitlebarRight;
    @BindView(R.id.tv_my_balance)
    TextView tvMyBalance;
    @BindView(R.id.tv_frozen_money)
    TextView tvFrozenMoney;
    @BindView(R.id.srl_account_list)
    SmartRefreshLayout srlAccountList;
    @BindView(R.id.list_my_account_list)
    ListView listMyAccountList;
    @BindView(R.id.tv_no_list)
    TextView tvNoList;

    private ArrayList<AccountRecordResponse.DataBean.ListBean> data;
    private MyAccountAdapter adapter;

    private String userId;
    private int page = 1;
    private static int pageSize = 10;
    private int pages = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_count);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
        getAccountTotal(userId);
        getAccountRecord(userId, page, true);
        initView();
    }

    @Override
    protected void initView() {
        srlAccountList.setRefreshHeader(new MaterialHeader(this));
        srlAccountList.setRefreshFooter(new ClassicsFooter(this));
        srlAccountList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getAccountRecord(userId, page, false);
            }
        });
        srlAccountList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getAccountRecord(userId, page, false);
            }
        });

        data = new ArrayList<>();
        adapter = new MyAccountAdapter(this, data);
        listMyAccountList.setDividerHeight(0);
        listMyAccountList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listMyAccountList.setSelector(R.color.transparent);
        listMyAccountList.setAdapter(adapter);

    }

    private void getAccountTotal(String userId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getAccountTotal, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.AccountTotal, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---" + md5);
        request(WHAT_ACCOUNT_TOTAL, request, httpListener, md5, true, true);
    }

    private void getAccountRecord(String userId, int page, boolean isLoading) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getAccountRecord, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&pageIndex=" + page + "&pageSize=" + pageSize).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.AccountRecord, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("pageIndex", page);
        request.add("pageSize", pageSize);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId + "&pageIndex=" + page + "&pageSize=" + pageSize + "---" + s + "---" + md5);
        request(WHAT_ACCOUNT_RECORD, request, httpListener, md5, true, isLoading);
    }

    private boolean hasMoreData = false;
    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_ACCOUNT_TOTAL:
                    String accountTotalJson = response.get();
                    AccountTotalResponse accountTotalResponse = getGson().fromJson(accountTotalJson, AccountTotalResponse.class);
                    LogUtils.d("SJHttp", accountTotalResponse.getBackStatus() + "");
                    if (accountTotalResponse.getBackStatus() == 0) {
                        AccountTotalResponse.DataBean data = accountTotalResponse.getData();
                        tvMyBalance.setText(data.getAvailable());
                        tvFrozenMoney.setText("冻结金额：" + data.getFrozen());
                    } else {
                        ToastUtil.show(accountTotalResponse.getMessage());
                    }
                    break;
                case WHAT_ACCOUNT_RECORD:
                    String accountRecordJson = response.get();
                    AccountRecordResponse accountRecordResponse = getGson().fromJson(accountRecordJson, AccountRecordResponse.class);
                    LogUtils.d("SJHttp", accountRecordResponse.getBackStatus() + "");
                    if (accountRecordResponse.getBackStatus() == 0) {
                        if (page == 1) {
                            data.clear();
                            hasMoreData = false;
                        }
                        page++;
                        pages = accountRecordResponse.getData().getPageCount();
                        List<AccountRecordResponse.DataBean.ListBean> list = accountRecordResponse.getData().getList();
                        if (list != null && list.size() != 0) {
                            tvNoList.setVisibility(View.GONE);
                            listMyAccountList.setVisibility(View.VISIBLE);
                            for (int i = 0; i < list.size(); i++) {
                                data.add(list.get(i));
                            }
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        } else {
                            tvNoList.setVisibility(View.VISIBLE);
                            listMyAccountList.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtil.show(accountRecordResponse.getMessage());
                    }
                    if (page > pages) {
                        hasMoreData = true;
                    }
                    srlAccountList.finishRefresh(1000, true);
                    srlAccountList.finishLoadMore(1000, true, hasMoreData);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            srlAccountList.finishRefresh(1000, true);
            srlAccountList.finishLoadMore(1000, true, hasMoreData);
            LogUtils.d("SJHttp", json);
        }
    };


    @OnClick({R.id.img_titlebar_back, R.id.tv_frozen_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_titlebar_back:
                finish();
                break;
            case R.id.tv_frozen_money:
                final GetTopVipDialog getTopVipDialog = new GetTopVipDialog(this, "冻结余额一般是指您提现未到账的金额，\n提现成功后冻结余额将会减少", "冻结余额");
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
