package com.sujin.sjpay.protocol;

import java.util.List;

/**
 * Created by QJS on 2018/3/15.
 */

public class PayListResponse {


    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-03-15 11:53:47
     * data : {"list":[{"Amount":"100.00","Fee":"0.38","Rate":"0.38%","CTime":"2018-03-14 14:56:13","PayTime":"2018-03-14 14:58:02","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"BCCB","BankCard":"522001******8278","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-14 14:58:09"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-14 09:31:21","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"522001******8278","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-14 09:29:54","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625809******7680","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.38","Rate":"0.38%","CTime":"2018-03-13 16:57:34","PayTime":"2018-03-13 16:59:07","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"SZPA","BankCard":"356869******5771","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-13 16:59:14"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:33:28","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"522001******8278","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:31:52","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:30:57","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"1.14","Rate":"0.38%","CTime":"2018-03-13 16:25:31","PayTime":"2018-03-13 16:27:08","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"HX","BankCard":"625969******9037","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-13 16:27:12"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:21:17","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-09 09:25:48","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-09 09:21:00","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625976******8979","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-09 09:18:25","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 17:14:59","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 17:13:36","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 14:47:09","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"522001******8278","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 14:44:59","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625976******8979","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 11:22:44","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625958******7587","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 10:44:19","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"356869******5771","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 10:03:48","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.38","Rate":"0.38%","CTime":"2018-03-07 17:17:09","PayTime":"2018-03-07 17:18:30","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"ICBC","BankCard":"622237******3375","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-07 17:18:39"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 17:17:05","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622237******3375","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 16:27:36","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 15:57:03","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"10000.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 15:40:19","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"9962.00","ActualAmount":"9961.00","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"10000.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 15:39:40","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"9962.00","ActualAmount":"9961.00","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"}],"pageCount":1,"recordCount":25,"pageIndex":1,"pageSize":100}
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
         * list : [{"Amount":"100.00","Fee":"0.38","Rate":"0.38%","CTime":"2018-03-14 14:56:13","PayTime":"2018-03-14 14:58:02","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"BCCB","BankCard":"522001******8278","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-14 14:58:09"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-14 09:31:21","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"522001******8278","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-14 09:29:54","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625809******7680","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.38","Rate":"0.38%","CTime":"2018-03-13 16:57:34","PayTime":"2018-03-13 16:59:07","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"SZPA","BankCard":"356869******5771","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-13 16:59:14"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:33:28","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"522001******8278","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:31:52","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:30:57","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"1.14","Rate":"0.38%","CTime":"2018-03-13 16:25:31","PayTime":"2018-03-13 16:27:08","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"HX","BankCard":"625969******9037","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-13 16:27:12"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-13 16:21:17","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-09 09:25:48","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-09 09:21:00","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625976******8979","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-09 09:18:25","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 17:14:59","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 17:13:36","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625808******9280","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 14:47:09","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"522001******8278","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 14:44:59","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625976******8979","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 11:22:44","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"625958******7587","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 10:44:19","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"356869******5771","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-08 10:03:48","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"100.00","Fee":"0.38","Rate":"0.38%","CTime":"2018-03-07 17:17:09","PayTime":"2018-03-07 17:18:30","PayState":10,"PayStateTxt":"支付成功","Message":"","BankCode":"ICBC","BankCard":"622237******3375","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"621790*********6547","DrawState":10,"DrawStateTxt":"打款成功","HandleTime":"2018-03-07 17:18:39"},{"Amount":"100.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 17:17:05","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622237******3375","WithDrawAmount":"99.62","ActualAmount":"98.62","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 16:27:36","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"300.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 15:57:03","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"298.86","ActualAmount":"297.86","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"10000.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 15:40:19","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"9962.00","ActualAmount":"9961.00","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"},{"Amount":"10000.00","Fee":"0.00","Rate":"0.38%","CTime":"2018-03-07 15:39:40","PayTime":"--","PayState":1,"PayStateTxt":"支付发起中","Message":"成功","BankCode":"","BankCard":"622688******3030","WithDrawAmount":"9962.00","ActualAmount":"9961.00","Fee3":"1.00","DrawBankCard":"","DrawState":0,"DrawStateTxt":"未发起","HandleTime":"--"}]
         * pageCount : 1
         * recordCount : 25
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

        public static class ListBean {
            public String getOrderNo() {
                return OrderNo;
            }

            /**
             * Amount : 100.00
             * Fee : 0.38
             * Rate : 0.38%
             * CTime : 2018-03-14 14:56:13
             * PayTime : 2018-03-14 14:58:02
             * PayState : 10
             * PayStateTxt : 支付成功
             * Message :
             * BankCode : BCCB
             * BankCard : 522001******8278
             * WithDrawAmount : 99.62
             * ActualAmount : 98.62
             * Fee3 : 1.00
             * DrawBankCard : 621790*********6547
             * DrawState : 10
             * DrawStateTxt : 打款成功
             * HandleTime : 2018-03-14 14:58:09
             */

            private String OrderNo;
            private String Amount;
            private String Fee;
            private String Rate;
            private String CTime;
            private String PayTime;
            private int PayState;
            private String PayStateTxt;
            private String Message;
            private String BankCode;
            private String BankCard;
            private String WithDrawAmount;
            private String ActualAmount;
            private String Fee3;
            private String DrawBankCard;
            private int DrawState;
            private String DrawStateTxt;
            private String HandleTime;

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String Amount) {
                this.Amount = Amount;
            }

            public String getFee() {
                return Fee;
            }

            public void setFee(String Fee) {
                this.Fee = Fee;
            }

            public String getRate() {
                return Rate;
            }

            public void setRate(String Rate) {
                this.Rate = Rate;
            }

            public String getCTime() {
                return CTime;
            }

            public void setCTime(String CTime) {
                this.CTime = CTime;
            }

            public String getPayTime() {
                return PayTime;
            }

            public void setPayTime(String PayTime) {
                this.PayTime = PayTime;
            }

            public int getPayState() {
                return PayState;
            }

            public void setPayState(int PayState) {
                this.PayState = PayState;
            }

            public String getPayStateTxt() {
                return PayStateTxt;
            }

            public void setPayStateTxt(String PayStateTxt) {
                this.PayStateTxt = PayStateTxt;
            }

            public String getMessage() {
                return Message;
            }

            public void setMessage(String Message) {
                this.Message = Message;
            }

            public String getBankCode() {
                return BankCode;
            }

            public void setBankCode(String BankCode) {
                this.BankCode = BankCode;
            }

            public String getBankCard() {
                return BankCard;
            }

            public void setBankCard(String BankCard) {
                this.BankCard = BankCard;
            }

            public String getWithDrawAmount() {
                return WithDrawAmount;
            }

            public void setWithDrawAmount(String WithDrawAmount) {
                this.WithDrawAmount = WithDrawAmount;
            }

            public String getActualAmount() {
                return ActualAmount;
            }

            public void setActualAmount(String ActualAmount) {
                this.ActualAmount = ActualAmount;
            }

            public String getFee3() {
                return Fee3;
            }

            public void setFee3(String Fee3) {
                this.Fee3 = Fee3;
            }

            public String getDrawBankCard() {
                return DrawBankCard;
            }

            public void setDrawBankCard(String DrawBankCard) {
                this.DrawBankCard = DrawBankCard;
            }

            public int getDrawState() {
                return DrawState;
            }

            public void setDrawState(int DrawState) {
                this.DrawState = DrawState;
            }

            public String getDrawStateTxt() {
                return DrawStateTxt;
            }

            public void setDrawStateTxt(String DrawStateTxt) {
                this.DrawStateTxt = DrawStateTxt;
            }

            public String getHandleTime() {
                return HandleTime;
            }

            public void setHandleTime(String HandleTime) {
                this.HandleTime = HandleTime;
            }
        }
    }
}
