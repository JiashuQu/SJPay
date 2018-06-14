package com.sujin.sjpay.protocol;


import java.util.List;

/**
 * Created by QJS on 2018/3/16.
 */

public class PayCardListResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-14 10:59:00
     * data : [{"BankName":"建设银行","BankCode":"CCB","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/CCB.png"},{"BankName":"农业银行","BankCode":"ABC","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/ABC.png"},{"BankName":"工商银行","BankCode":"ICBC","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/ICBC.png"},{"BankName":"中国银行","BankCode":"BOC","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/BOC.png"},{"BankName":"民生银行","BankCode":"CMBC","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/CMBC.png"},{"BankName":"交通银行","BankCode":"BCM","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/BCM.png"},{"BankName":"光大银行","BankCode":"CEB","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/CEB.png"},{"BankName":"中信银行","BankCode":"ECITIC","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/ECITIC.png"},{"BankName":"广发银行","BankCode":"GDB","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/GDB.png"},{"BankName":"浦发银行","BankCode":"SPDB","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/SPDB.png"},{"BankName":"华夏银行","BankCode":"HX","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/HX.png"},{"BankName":"平安银行","BankCode":"SZPA","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/SZPA.png"},{"BankName":"北京银行","BankCode":"BCCB","State":0,"StateTxt":"可用","ICON":"http://static.sujintech.com/upload/bank/80/BCCB.png"}]
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
         * BankName : 建设银行
         * BankCode : CCB
         * State : 0
         * StateTxt : 可用
         * ICON : http://static.sujintech.com/upload/bank/80/CCB.png
         */

        private String BankName;
        private String BankCode;
        private int State;
        private String StateTxt;
        private String ICON;

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String BankName) {
            this.BankName = BankName;
        }

        public String getBankCode() {
            return BankCode;
        }

        public void setBankCode(String BankCode) {
            this.BankCode = BankCode;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getStateTxt() {
            return StateTxt;
        }

        public void setStateTxt(String StateTxt) {
            this.StateTxt = StateTxt;
        }

        public String getICON() {
            return ICON;
        }

        public void setICON(String ICON) {
            this.ICON = ICON;
        }
    }
}
