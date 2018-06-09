package com.sujin.sjpay.protocol;

public class IncomeTotalResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-09 16:48:20
     * data : {"TotalIncome":"0.00","LastMounthIncome":"0.00","CurrentMounthIncome":"0.00","LastMounthIncomeTip":"上月收益会扣除2元的手续费在每月的第一个工作日打款到您的结算卡中。"}
     */

    private int backStatus;
    private String message;
    private String serverTime;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * TotalIncome : 0.00
         * LastMounthIncome : 0.00
         * CurrentMounthIncome : 0.00
         * LastMounthIncomeTip : 上月收益会扣除2元的手续费在每月的第一个工作日打款到您的结算卡中。
         */

        private String TotalIncome;
        private String LastMounthIncome;
        private String CurrentMounthIncome;
        private String LastMounthIncomeTip;

        public String getTotalIncome() {
            return TotalIncome;
        }

        public void setTotalIncome(String TotalIncome) {
            this.TotalIncome = TotalIncome;
        }

        public String getLastMounthIncome() {
            return LastMounthIncome;
        }

        public void setLastMounthIncome(String LastMounthIncome) {
            this.LastMounthIncome = LastMounthIncome;
        }

        public String getCurrentMounthIncome() {
            return CurrentMounthIncome;
        }

        public void setCurrentMounthIncome(String CurrentMounthIncome) {
            this.CurrentMounthIncome = CurrentMounthIncome;
        }

        public String getLastMounthIncomeTip() {
            return LastMounthIncomeTip;
        }

        public void setLastMounthIncomeTip(String LastMounthIncomeTip) {
            this.LastMounthIncomeTip = LastMounthIncomeTip;
        }
    }
}
