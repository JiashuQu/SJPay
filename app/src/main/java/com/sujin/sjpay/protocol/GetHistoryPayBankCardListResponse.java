package com.sujin.sjpay.protocol;


import java.io.Serializable;
import java.util.List;

/**
 * Created by QJS on 2018/3/9.
 */

public class GetHistoryPayBankCardListResponse implements Serializable {


    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-03-21 16:21:26
     * data : {"list":[{"ID":100020,"BankCard":"6226880089853030","Mobile":"18506120807","BankName":"中信银行","BankCode":"ECITIC","CVN2":"170","ExpiresYear":"21","ExpiresMouth":"10","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-20 21:14:17","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":1,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100016,"BankCard":"6259588903947587","Mobile":"18506120807","BankName":"浦发银行","BankCode":"SPDB","CVN2":"432","ExpiresYear":"25","ExpiresMouth":"11","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-19 11:14:03","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100013,"BankCard":"6226230509156673","Mobile":"18618515691","BankName":"民生银行","BankCode":"CMBC","CVN2":"147","ExpiresYear":"34","ExpiresMouth":"12","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-16 16:07:12","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100010,"BankCard":"6222020200083410239","Mobile":"15110167786","BankName":"工商银行","BankCode":"ICBC","CVN2":"300","ExpiresYear":"19","ExpiresMouth":"02","OpeningBank":"北京是阿斯蒂芬阿萨德发送发艾丝凡","OpeningSerialBank":"100000","CTime":"2018-03-14 20:42:46","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100009,"BankCard":"5220010103898278","Mobile":"18506120807","BankName":"北京银行","BankCode":"BCCB","CVN2":"300","ExpiresYear":"19","ExpiresMouth":"04","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-14 14:57:02","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100007,"BankCard":"3568690093425771","Mobile":"185****0807","BankName":"平安银行","BankCode":"SZPA","CVN2":"0","ExpiresYear":"0","ExpiresMouth":"0","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-13 16:58:34","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100006,"BankCard":"6259690440419037","Mobile":"185****0807","BankName":"华夏银行","BankCode":"HX","CVN2":"0","ExpiresYear":"0","ExpiresMouth":"0","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-13 16:26:33","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100001,"BankCard":"6222370205673375","Mobile":"185****0807","BankName":"工商银行","BankCode":"ICBC","CVN2":"0","ExpiresYear":"0","ExpiresMouth":"0","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-07 17:18:08","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]}],"pageCount":1,"recordCount":8,"pageIndex":1,"pageSize":100}
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

    public static class DataBean  implements Serializable {
        /**
         * list : [{"ID":100020,"BankCard":"6226880089853030","Mobile":"18506120807","BankName":"中信银行","BankCode":"ECITIC","CVN2":"170","ExpiresYear":"21","ExpiresMouth":"10","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-20 21:14:17","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":1,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100016,"BankCard":"6259588903947587","Mobile":"18506120807","BankName":"浦发银行","BankCode":"SPDB","CVN2":"432","ExpiresYear":"25","ExpiresMouth":"11","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-19 11:14:03","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100013,"BankCard":"6226230509156673","Mobile":"18618515691","BankName":"民生银行","BankCode":"CMBC","CVN2":"147","ExpiresYear":"34","ExpiresMouth":"12","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-16 16:07:12","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100010,"BankCard":"6222020200083410239","Mobile":"15110167786","BankName":"工商银行","BankCode":"ICBC","CVN2":"300","ExpiresYear":"19","ExpiresMouth":"02","OpeningBank":"北京是阿斯蒂芬阿萨德发送发艾丝凡","OpeningSerialBank":"100000","CTime":"2018-03-14 20:42:46","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100009,"BankCard":"5220010103898278","Mobile":"18506120807","BankName":"北京银行","BankCode":"BCCB","CVN2":"300","ExpiresYear":"19","ExpiresMouth":"04","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-14 14:57:02","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100007,"BankCard":"3568690093425771","Mobile":"185****0807","BankName":"平安银行","BankCode":"SZPA","CVN2":"0","ExpiresYear":"0","ExpiresMouth":"0","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-13 16:58:34","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100006,"BankCard":"6259690440419037","Mobile":"185****0807","BankName":"华夏银行","BankCode":"HX","CVN2":"0","ExpiresYear":"0","ExpiresMouth":"0","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-13 16:26:33","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]},{"ID":100001,"BankCard":"6222370205673375","Mobile":"185****0807","BankName":"工商银行","BankCode":"ICBC","CVN2":"0","ExpiresYear":"0","ExpiresMouth":"0","OpeningBank":"","OpeningSerialBank":"","CTime":"2018-03-07 17:18:08","State":1,"listChannel":[{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":0,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]}]
         * pageCount : 1
         * recordCount : 8
         * pageIndex : 1
         * pageSize : 100
         */

        private int pageCount;
        private int recordCount;
        private int pageIndex;
        private int pageSize;
        private List<ListBean> list;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean  implements Serializable {
            /**
             * ID : 100020
             * BankCard : 6226880089853030
             * Mobile : 18506120807
             * BankName : 中信银行
             * BankCode : ECITIC
             * CVN2 : 170
             * ExpiresYear : 21
             * ExpiresMouth : 10
             * OpeningBank :
             * OpeningSerialBank :
             * CTime : 2018-03-20 21:14:17
             * State : 1
             * listChannel : [{"ChannelType":2,"ChannelName":"荣邦无积分","ChannelRemark":"423423","IsActivate":0,"ChannelState":1},{"ChannelType":1,"ChannelName":"荣邦积分","ChannelRemark":"","IsActivate":1,"ChannelState":1},{"ChannelType":0,"ChannelName":"易宝通道","ChannelRemark":"","IsActivate":1,"ChannelState":1}]
             */

            private int ID;
            private String BankCard;
            private String Mobile;
            private String BankName;
            private String BankCode;
            private String CVN2;
            private String ExpiresYear;
            private String ExpiresMouth;
            private String OpeningBank;
            private String OpeningSerialBank;
            private String CTime;
            private int State;
            private List<ListChannelBean> listChannel;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getBankCard() {
                return BankCard;
            }

            public void setBankCard(String BankCard) {
                this.BankCard = BankCard;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

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

            public String getCVN2() {
                return CVN2;
            }

            public void setCVN2(String CVN2) {
                this.CVN2 = CVN2;
            }

            public String getExpiresYear() {
                return ExpiresYear;
            }

            public void setExpiresYear(String ExpiresYear) {
                this.ExpiresYear = ExpiresYear;
            }

            public String getExpiresMouth() {
                return ExpiresMouth;
            }

            public void setExpiresMouth(String ExpiresMouth) {
                this.ExpiresMouth = ExpiresMouth;
            }

            public String getOpeningBank() {
                return OpeningBank;
            }

            public void setOpeningBank(String OpeningBank) {
                this.OpeningBank = OpeningBank;
            }

            public String getOpeningSerialBank() {
                return OpeningSerialBank;
            }

            public void setOpeningSerialBank(String OpeningSerialBank) {
                this.OpeningSerialBank = OpeningSerialBank;
            }

            public String getCTime() {
                return CTime;
            }

            public void setCTime(String CTime) {
                this.CTime = CTime;
            }

            public int getState() {
                return State;
            }

            public void setState(int State) {
                this.State = State;
            }

            public List<ListChannelBean> getListChannel() {
                return listChannel;
            }

            public void setListChannel(List<ListChannelBean> listChannel) {
                this.listChannel = listChannel;
            }

            public static class ListChannelBean implements Serializable {
                /**
                 * ChannelType : 2
                 * ChannelName : 荣邦无积分
                 * ChannelRemark : 423423
                 * IsActivate : 0
                 * ChannelState : 1
                 */

                private int ChannelType;
                private String ChannelName;
                private String ChannelRemark;
                private int IsActivate;
                private int ChannelState;

                public int getChannelType() {
                    return ChannelType;
                }

                public void setChannelType(int ChannelType) {
                    this.ChannelType = ChannelType;
                }

                public String getChannelName() {
                    return ChannelName;
                }

                public void setChannelName(String ChannelName) {
                    this.ChannelName = ChannelName;
                }

                public String getChannelRemark() {
                    return ChannelRemark;
                }

                public void setChannelRemark(String ChannelRemark) {
                    this.ChannelRemark = ChannelRemark;
                }

                public int getIsActivate() {
                    return IsActivate;
                }

                public void setIsActivate(int IsActivate) {
                    this.IsActivate = IsActivate;
                }

                public int getChannelState() {
                    return ChannelState;
                }

                public void setChannelState(int ChannelState) {
                    this.ChannelState = ChannelState;
                }
            }
        }
    }
}
