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
import com.sujin.sjpay.adapter.SelectAddressAdapter;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.ElementProvinceCityResponse;
import com.sujin.sjpay.protocol.ProvinceCityResponse;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectAddressActivity extends BaseActivity {

    @BindView(R.id.list_select_address)
    ListView listSelectAddress;
    private ArrayList<ElementProvinceCityResponse> provinces, citys;
    private SelectAddressAdapter adapter;
    private String province, city;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        request("0");
        provinces = new ArrayList<>();
        citys = new ArrayList<>();
        adapter = new SelectAddressAdapter(this, provinces);
        listSelectAddress.setAdapter(adapter);
        initView();
    }

    @Override
    protected void initView() {
        listSelectAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ElementProvinceCityResponse elementProvinceCityResponse = (ElementProvinceCityResponse) parent.getAdapter().getItem(position);
                if (elementProvinceCityResponse == null) {
                    return;
                }

                if (!TextUtils.isEmpty(elementProvinceCityResponse.getCode())) {
                    city = elementProvinceCityResponse.getName();
                    if (TextUtils.isEmpty(province)) {
                        province = "";
                    }
                    Intent intent = new Intent();
                    intent.putExtra(AppConstants.INTENT_KEY_PROVINCE, province);
                    intent.putExtra(AppConstants.INTENT_KEY_CITY, city);
                    intent.putExtra(AppConstants.INTENT_KEY_CITY_CODE, elementProvinceCityResponse.getCode());
                    setResult(RESULT_OK, intent);
                    finish();
                    return;
                }
                province = elementProvinceCityResponse.getName();
                type = "2";
                request(elementProvinceCityResponse.getId());
            }
        });
    }

    private void request(String baseId) {
        type = baseId;
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getBankAreaCode, RequestMethod.GET);
        char[] chars = ("BaseId=" + baseId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.AreaCode, s, ApiConstants.API_YEEPAY);
        request.add("BaseId", baseId);
        com.lidroid.xutils.util.LogUtils.d(baseId + "---" + s + "---"  + md5);
        request(0, request, httpListener, md5, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    ProvinceCityResponse provinceCityResponse = getGson().fromJson(registerJson, ProvinceCityResponse.class);
                    LogUtils.d("SJHttp", provinceCityResponse.getBackStatus());
                    if (TextUtils.equals(provinceCityResponse.getBackStatus(), "0")) {
                        ArrayList<ElementProvinceCityResponse> data = provinceCityResponse.getData();
                        if (data != null) {
                            if (TextUtils.equals(type, "0")) {
                                provinces = data;
                            } else {
                                citys = data;
                            }
                            adapter.setData(data, type);
                            listSelectAddress.setSelection(0);
                        }
                    } else {
                        ToastUtil.show(provinceCityResponse.getMessage());
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
