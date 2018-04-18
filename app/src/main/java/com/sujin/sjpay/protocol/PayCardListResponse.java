package com.sujin.sjpay.protocol;

import java.util.List;

/**
 * Created by QJS on 2018/3/16.
 */

public class PayCardListResponse {


    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-03-16 12:49:53
     * data : ["工商银行","农业银行","招商银行","建设银行","交通银行","中信银行","光大银行","北京银行","平安银行","中国银行","兴业银行","民生银行","华夏银行","广发银行","浦发银行"]
     */

    private int backStatus;
    private String message;
    private String serverTime;
    private List<String> data;

    public int getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(int backStatus) {
        this.backStatus = backStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
