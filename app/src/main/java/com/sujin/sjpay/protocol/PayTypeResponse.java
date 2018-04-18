package com.sujin.sjpay.protocol;

import java.util.List;

/**
 * Created by QJS on 2018/3/24.
 */

public class PayTypeResponse {


    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-03-24 16:14:23
     * data : [{"PayType":1,"PayName":"无积分","Quota":"500~10000元/笔","WithDraw":"实时到帐","Time":"09:00-21:00","Remark":"单日单卡限额10000元","Fee":"0.30%+0.5元/笔"},{"PayType":0,"PayName":"积分","Quota":"100~20000元/笔","WithDraw":"实时到帐","Time":"09:00-21:00","Remark":"单日单卡限额10000元","Fee":"0.38%+1元/笔"}]
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
         * PayType : 1
         * PayName : 无积分
         * Quota : 500~10000元/笔
         * WithDraw : 实时到帐
         * Time : 09:00-21:00
         * Remark : 单日单卡限额10000元
         * Fee : 0.30%+0.5元/笔
         */

        private int PayType;
        private String PayName;
        private String Quota;
        private String WithDraw;
        private String Time;
        private String Remark;
        private String Fee;

        public int getPayType() {
            return PayType;
        }

        public void setPayType(int PayType) {
            this.PayType = PayType;
        }

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

        public String getWithDraw() {
            return WithDraw;
        }

        public void setWithDraw(String WithDraw) {
            this.WithDraw = WithDraw;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getFee() {
            return Fee;
        }

        public void setFee(String Fee) {
            this.Fee = Fee;
        }
    }
}
