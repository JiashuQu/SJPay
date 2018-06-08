package com.sujin.sjpay.protocol;

import java.util.List;

public class VipTypeIntroduceResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-08 18:02:21
     * data : [{"PayName":"无积分","Quota":"500~10000元/笔","Fee":"0.49%+2.0元/笔"},{"PayName":"有积分","Quota":"500~20000元/笔","Fee":"0.53%+2.0元/笔"}]
     */

    private int backStatus;
    private String message;
    private String serverTime;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * PayName : 无积分
         * Quota : 500~10000元/笔
         * Fee : 0.49%+2.0元/笔
         */

        private String PayName;
        private String Quota;
        private String Fee;

        public String getPayName() {
            return PayName;
        }

        public void setPayName(String PayName) {
            this.PayName = PayName;
        }

        public String getQuota() {
            return Quota;
        }

        public void setQuota(String Quota) {
            this.Quota = Quota;
        }

        public String getFee() {
            return Fee;
        }

        public void setFee(String Fee) {
            this.Fee = Fee;
        }
    }
}
