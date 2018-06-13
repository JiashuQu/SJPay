package com.sujin.sjpay.protocol;

import com.lidroid.xutils.db.table.KeyValue;

import java.util.List;

public class InviteListResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-08 09:51:54
     * data : {"list":[{"Mobile":"17778041594","CTime":"2018-06-08 08:43:36","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15110167781","CTime":"2018-06-06 17:10:48","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15311436473","CTime":"2018-06-04 21:30:41","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"宋庆龙"},{"Mobile":"15110178984","CTime":"2018-05-23 10:14:06","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"是多少"},{"Mobile":"15110162552","CTime":"2018-05-22 21:15:54","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"哈哈"},{"Mobile":"15110189999","CTime":"2018-05-18 16:36:53","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15110167777","CTime":"2018-05-18 14:51:08","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15162297990","CTime":"2018-05-16 09:24:16","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"杨学义"},{"Mobile":"17301395652","CTime":"2018-05-12 10:40:30","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"18810447149","CTime":"2018-05-07 12:39:31","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"田淳"}],"pageCount":4,"recordCount":34,"pageIndex     ":1,"pageSize":10}
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
         * list : [{"Mobile":"17778041594","CTime":"2018-06-08 08:43:36","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15110167781","CTime":"2018-06-06 17:10:48","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15311436473","CTime":"2018-06-04 21:30:41","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"宋庆龙"},{"Mobile":"15110178984","CTime":"2018-05-23 10:14:06","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"是多少"},{"Mobile":"15110162552","CTime":"2018-05-22 21:15:54","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"哈哈"},{"Mobile":"15110189999","CTime":"2018-05-18 16:36:53","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15110167777","CTime":"2018-05-18 14:51:08","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"15162297990","CTime":"2018-05-16 09:24:16","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"杨学义"},{"Mobile":"17301395652","CTime":"2018-05-12 10:40:30","IsRealState":0,"IsRealStateTxt":"未认证","RealName":"无名氏"},{"Mobile":"18810447149","CTime":"2018-05-07 12:39:31","IsRealState":1,"IsRealStateTxt":"已认证","RealName":"田淳"}]
         * pageCount : 4
         * recordCount : 34
         * pageIndex      : 1
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

        public static class ListBean extends KeyValue{
            /**
             * Mobile : 17778041594
             * CTime : 2018-06-08 08:43:36
             * IsRealState : 0
             * IsRealStateTxt : 未认证
             * RealName : 无名氏
             */

            private String Mobile;
            private String CTime;
            private int IsRealState;
            private String IsRealStateTxt;
            private String RealName;

            public ListBean(String key, Object value) {
                super(key, value);
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

            public String getCTime() {
                return CTime;
            }

            public void setCTime(String CTime) {
                this.CTime = CTime;
            }

            public int getIsRealState() {
                return IsRealState;
            }

            public void setIsRealState(int IsRealState) {
                this.IsRealState = IsRealState;
            }

            public String getIsRealStateTxt() {
                return IsRealStateTxt;
            }

            public void setIsRealStateTxt(String IsRealStateTxt) {
                this.IsRealStateTxt = IsRealStateTxt;
            }

            public String getRealName() {
                return RealName;
            }

            public void setRealName(String RealName) {
                this.RealName = RealName;
            }
        }
    }
}
