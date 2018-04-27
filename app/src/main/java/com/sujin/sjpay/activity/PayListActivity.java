package com.sujin.sjpay.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sujin.sjpay.R;
import com.sujin.sjpay.adapter.PayListAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.PayListResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayListActivity extends BaseActivity {

    @BindView(R.id.list_pay_list)
    ListView listPayList;
    @BindView(R.id.srl_pay_list)
    SmartRefreshLayout srlPayList;

    private ArrayList<PayListResponse.DataBean.ListBean> data;
    private PayListAdapter adapter;
    private int page = 1;
    private static int pageSize = 10;
    private int pages = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_list);
        ButterKnife.bind(this);
        getPayList(SJApplication.getInstance().getUserId(), page, true);
        initView();
    }

    @Override
    protected void initView() {
        srlPayList.setRefreshHeader(new MaterialHeader(this));
        srlPayList.setRefreshFooter(new ClassicsFooter(this));
        srlPayList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getPayList(SJApplication.getInstance().getUserId(), page, false);
            }
        });
        srlPayList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPayList(SJApplication.getInstance().getUserId(), page, false);
            }
        });

        data = new ArrayList<>();
        adapter = new PayListAdapter(this, data);
        listPayList.setDividerHeight(0);
        listPayList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listPayList.setSelector(R.color.transparent);
        listPayList.setAdapter(adapter);
    }

    private void getPayList(String userId, int page, boolean isLoading) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getQueryPayRecordList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&State=" + -200 + "&pageIndex=" + page + "&pageSize=" + pageSize).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.QueryPayRecordList, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("State", -200);
        request.add("pageIndex", page);
        request.add("pageSize", pageSize);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId + "&pageIndex=" + page + "&pageSize=" + pageSize + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, true, isLoading);
    }

    private boolean hasMoreData = false;
    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    PayListResponse payListResponse = getGson().fromJson(registerJson, PayListResponse.class);
                    if (payListResponse.getBackStatus() == 0) {
                        if (page == 1) {
                            data.clear();
                            hasMoreData = false;
                        }
                        page++;
                        pages = payListResponse.getData().getPageCount();
                        List<PayListResponse.DataBean.ListBean> list = payListResponse.getData().getList();
                        if (list != null || list.size() == 0) {
                            for (int i = 0; i < list.size(); i++) {
                                data.add(list.get(i));
                            }
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
//                            listPayList.setSelection(0);
                        } else {
                            ToastUtil.show("没有交易记录");
                        }
                    } else {
                        ToastUtil.show(payListResponse.getMessage());
                    }
                    if (page > pages){
                        hasMoreData = true;
                    }
                    srlPayList.finishRefresh(1000, true);
                    srlPayList.finishLoadMore(1000, true, hasMoreData);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            srlPayList.finishRefresh(1000, true);
            srlPayList.finishLoadMore(1000, true, hasMoreData);
            LogUtils.d("SJHttp", json);
        }
    };
}
