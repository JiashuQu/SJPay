package com.sujin.sjpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.adapter.SelectBankAdapter;
import com.sujin.sjpay.adapter.SelectPayBankListAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetPayBankQuotaList;
import com.sujin.sjpay.protocol.GetPayBankQuotaListResponse;
import com.sujin.sjpay.protocol.PayCardListResponse;
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

public class SelectPayBankActivity extends BaseActivity {

    @BindView(R.id.list_select_bank_belong)
    ListView listSelectBankBelong;

    private ArrayList<PayCardListResponse.DataBean> data;
    private SelectPayBankListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank);
        ButterKnife.bind(this);
        request();
        data = new ArrayList<>();
        adapter = new SelectPayBankListAdapter(this, data);
        listSelectBankBelong.setAdapter(adapter);
        initView();
    }

    @Override
    protected void initView() {
        listSelectBankBelong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetPayBankQuotaList payBankQuotaListResponse = (GetPayBankQuotaList) parent.getAdapter().getItem(position);
                if (payBankQuotaListResponse == null) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(AppConstants.INTENT_KEY_BANK_CELL, payBankQuotaListResponse);
                setResult(RESULT_OK, intent);
                finish();
                return;
            }
        });
    }

    private void request() {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getBankList, RequestMethod.GET);
        char[] chars = ("TypeId=" + 0).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.GetBankList, s, ApiConstants.API_USERS);
        request.add("TypeId", 0);
        com.lidroid.xutils.util.LogUtils.d(md5);
        request(0, request, httpListener, md5, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    PayCardListResponse payCardListResponse = getGson().fromJson(registerJson, PayCardListResponse.class);
                    LogUtils.d("SJHttp", payCardListResponse.getBackStatus()+"");
                    if (TextUtils.equals(payCardListResponse.getBackStatus()+"", "0")) {
                        List<PayCardListResponse.DataBean> data = payCardListResponse.getData();
                        if (data != null || data.size() == 0) {
                            adapter.setData(data);
                            listSelectBankBelong.setSelection(0);
                        } else {
                            ToastUtil.show("暂无数据");
                        }
                    } else {
                        ToastUtil.show(payCardListResponse.getMessage());
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
