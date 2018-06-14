package com.sujin.sjpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.R;
import com.sujin.sjpay.adapter.ChoseBankCardAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.GetHistoryPayBankCardListResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.TitleBarView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoseBankCardActivity extends BaseActivity {

    @BindView(R.id.list_select_bank_belong)
    ListView listSelectBankBelong;
    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.tv_bankcard)
    TextView tvBankcard;
    @BindView(R.id.tv_no_list)
    TextView tvNoList;

    private ArrayList<GetHistoryPayBankCardListResponse.DataBean.ListBean> data;
    private ChoseBankCardAdapter adapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_bank_card);
        ButterKnife.bind(this);
        data = new ArrayList<>();
        adapter = new ChoseBankCardAdapter(this, data);
        listSelectBankBelong.setAdapter(adapter);
        title = getIntent().getStringExtra("title");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistoryPayBankCardList(SJApplication.getInstance().getUserId());
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(title)) {
            titleBar.setTitle(title);
        }
        listSelectBankBelong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetHistoryPayBankCardListResponse.DataBean.ListBean historyPayBankCardList = (GetHistoryPayBankCardListResponse.DataBean.ListBean) parent.getAdapter().getItem(position);
                if (historyPayBankCardList == null) {
                    return;
                }
                if ("支付卡管理".equals(title)) {//这里进来可以修改银行卡
                    Intent intent = new Intent(ChoseBankCardActivity.this, CreditCardActivity.class);
                    intent.putExtra("CreditCardInfo", historyPayBankCardList);
                    intent.putExtra("title", "修改支付卡信息");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(AppConstants.INTENT_KEY_BANK_CELL, historyPayBankCardList);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                return;
            }
        });
    }

    private void getHistoryPayBankCardList(String userId) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getHistoryPayBankCardList, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&pageIndex=" + 1 + "&pageSize=" + 100).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.GetBankCardList, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
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
                    GetHistoryPayBankCardListResponse getHistoryPayBankCardListResponse = getGson().fromJson(registerJson, GetHistoryPayBankCardListResponse.class);
                    if (getHistoryPayBankCardListResponse.getBackStatus() == 0) {
                        List<GetHistoryPayBankCardListResponse.DataBean.ListBean> list = getHistoryPayBankCardListResponse.getData().getList();
                        if (list != null && list.size() != 0) {
                            tvNoList.setVisibility(View.GONE);
                            listSelectBankBelong.setVisibility(View.VISIBLE);
                            adapter.setData(list);
                            listSelectBankBelong.setSelection(0);
                        } else {
                            tvNoList.setVisibility(View.VISIBLE);
                            listSelectBankBelong.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtil.show(getHistoryPayBankCardListResponse.getMessage());
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

    @OnClick(R.id.tv_bankcard)
    public void onViewClicked() {
        startActivity(new Intent(ChoseBankCardActivity.this, CreditCardActivity.class));
    }
}
