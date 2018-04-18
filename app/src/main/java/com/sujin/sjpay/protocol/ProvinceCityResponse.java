package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

import java.util.ArrayList;

/**
 * Created by QJS on 2018/3/5.
 */

public class ProvinceCityResponse {
    /**
     * 状态码
     */
    private String backStatus = StringUtil.EMPTY;

    /**
     * 状态信息
     */
    private String message = StringUtil.EMPTY;

    /**
     * 服务器时间
     */
    private String serverTime = StringUtil.EMPTY;

    protected ArrayList<ElementProvinceCityResponse> data;

    public ArrayList<ElementProvinceCityResponse> getData() {
        return data;
    }

    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }

}
