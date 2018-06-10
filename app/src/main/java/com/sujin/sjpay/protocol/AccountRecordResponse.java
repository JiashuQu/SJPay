package com.sujin.sjpay.protocol;

import java.util.List;

public class AccountRecordResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-10 12:38:51
     * data : {"list":[{"InOrOut":"-","InOrOutNum":-1,"Amount":"300.00","Available":"2558.00","Frozen":"0.00","CTime":"2018-05-31 15:24:46","TypeId":301,"Service":"提现到账"},{"InOrOut":"+","InOrOutNum":1,"Amount":"159.00","Available":"2858.00","Frozen":"0.00","CTime":"2018-05-31 14:49:18","TypeId":101,"Service":"开通SVIP分润"},{"InOrOut":"+","InOrOutNum":1,"Amount":"2699.00","Available":"2699.00","Frozen":"0.00","CTime":"2018-05-31 14:48:24","TypeId":104,"Service":"开通顶级代理"}],"pageCount":1,"recordCount":3,"pageIndex":1,"pageSize":10}
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
         * list : [{"InOrOut":"-","InOrOutNum":-1,"Amount":"300.00","Available":"2558.00","Frozen":"0.00","CTime":"2018-05-31 15:24:46","TypeId":301,"Service":"提现到账"},{"InOrOut":"+","InOrOutNum":1,"Amount":"159.00","Available":"2858.00","Frozen":"0.00","CTime":"2018-05-31 14:49:18","TypeId":101,"Service":"开通SVIP分润"},{"InOrOut":"+","InOrOutNum":1,"Amount":"2699.00","Available":"2699.00","Frozen":"0.00","CTime":"2018-05-31 14:48:24","TypeId":104,"Service":"开通顶级代理"}]
         * pageCount : 1
         * recordCount : 3
         * pageIndex : 1
         * pageSize : 10
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
            /**
             * InOrOut : -
             * InOrOutNum : -1
             * Amount : 300.00
             * Available : 2558.00
             * Frozen : 0.00
             * CTime : 2018-05-31 15:24:46
             * TypeId : 301
             * Service : 提现到账
             */

            private String InOrOut;
            private int InOrOutNum;
            private String Amount;
            private String Available;
            private String Frozen;
            private String CTime;
            private int TypeId;
            private String Service;

            public String getInOrOut() {
                return InOrOut;
            }

            public void setInOrOut(String InOrOut) {
                this.InOrOut = InOrOut;
            }

            public int getInOrOutNum() {
                return InOrOutNum;
            }

            public void setInOrOutNum(int InOrOutNum) {
                this.InOrOutNum = InOrOutNum;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String Amount) {
                this.Amount = Amount;
            }

            public String getAvailable() {
                return Available;
            }

            public void setAvailable(String Available) {
                this.Available = Available;
            }

            public String getFrozen() {
                return Frozen;
            }

            public void setFrozen(String Frozen) {
                this.Frozen = Frozen;
            }

            public String getCTime() {
                return CTime;
            }

            public void setCTime(String CTime) {
                this.CTime = CTime;
            }

            public int getTypeId() {
                return TypeId;
            }

            public void setTypeId(int TypeId) {
                this.TypeId = TypeId;
            }

            public String getService() {
                return Service;
            }

            public void setService(String Service) {
                this.Service = Service;
            }
        }
    }
}
