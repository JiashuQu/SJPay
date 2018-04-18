package com.sujin.sjpay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.adapter.ChoseBankCardAdapter;
import com.sujin.sjpay.adapter.PayListAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetHistoryPayBankCardListResponse;
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

    private ArrayList<PayListResponse.DataBean.ListBean> data;
    private PayListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_list);
        ButterKnife.bind(this);
        getPayList(SJApplication.getInstance().getUserId());
        data = new ArrayList<>();
        adapter = new PayListAdapter(this, data);
        listPayList.setDividerHeight(0);
        listPayList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listPayList.setSelector(R.color.transparent);
        listPayList.setAdapter(adapter);
    }

    @Override
    protected void initView() {

    }

    private void getPayList(String userId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getQueryPayRecordList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&State=" + -200 + "&pageIndex=" + 1 + "&pageSize=" + 100).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.QueryPayRecordList, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("State", -200);
        request.add("pageIndex", 1);
        request.add("pageSize", 100);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId + "&pageIndex=" + 100 + "&pageSize=" + 100 + "---" + s + "---" + md5);
        request(0, request, httpListener, md5, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    PayListResponse payListResponse = getGson().fromJson(registerJson, PayListResponse.class);
                    if (payListResponse.getBackStatus() == 0) {
                        List<PayListResponse.DataBean.ListBean> list = payListResponse.getData().getList();
                        if (list != null || list.size() == 0) {
                            adapter.setData(list);
                            listPayList.setSelection(0);
                        }else {
                            ToastUtil.show("没有交易记录");
                        }
                    } else {
                        ToastUtil.show(payListResponse.getMessage());
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
}
