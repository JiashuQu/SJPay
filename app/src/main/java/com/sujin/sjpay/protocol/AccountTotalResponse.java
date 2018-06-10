package com.sujin.sjpay.protocol;

public class AccountTotalResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-10 12:38:51
     * data : {"Total":"2558.00","Frozen":"0.00","Available":"2558.00"}
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
         * Total : 2558.00
         * Frozen : 0.00
         * Available : 2558.00
         */

        private String Total;
        private String Frozen;
        private String Available;

        public String getTotal() {
            return Total;
        }

        public void setTotal(String Total) {
            this.Total = Total;
        }

        public String getFrozen() {
            return Frozen;
        }

        public void setFrozen(String Frozen) {
            this.Frozen = Frozen;
        }

        public String getAvailable() {
            return Available;
        }

        public void setAvailable(String Available) {
            this.Available = Available;
        }
    }
}
