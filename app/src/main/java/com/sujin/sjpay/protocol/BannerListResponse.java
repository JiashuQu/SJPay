package com.sujin.sjpay.protocol;

import java.util.List;

public class BannerListResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-06 16:12:03
     * data : [{"ID":10001,"Title":"sdgasdfa2","WapURL":"http://api.sujintech.com/2","ImgUrl":"http://static.sujintech.com/upload/banner/20180605/100139514.jpg"},{"ID":10000,"Title":"测试1","WapURL":"http://api.sujintech.com/","ImgUrl":"http://static.sujintech.com/upload/users/20180522/104034067.jpg"}]
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
         * ID : 10001
         * Title : sdgasdfa2
         * WapURL : http://api.sujintech.com/2
         * ImgUrl : http://static.sujintech.com/upload/banner/20180605/100139514.jpg
         */

        private int ID;
        private String Title;
        private String WapURL;
        private String ImgUrl;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getWapURL() {
            return WapURL;
        }

        public void setWapURL(String WapURL) {
            this.WapURL = WapURL;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }
    }
}
