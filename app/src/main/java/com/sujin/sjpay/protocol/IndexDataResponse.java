package com.sujin.sjpay.protocol;

import java.util.List;

public class IndexDataResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-06 16:12:03
     * data : {"btnList":[{"Title":"邀请好友","icon":"http://static.sujintech.com/","WapUrl":"QRcode"},{"Title":"邀请收益","icon":"http://static.sujintech.com/","WapUrl":"Invite"},{"Title":"新手指引","icon":"http://static.sujintech.com/","WapUrl":"https://mp.weixin.qq.com/s/wPYnEFtQZOuWYnERcsQfGQ"},{"Title":"火爆上线","icon":"http://static.sujintech.com/","WapUrl":"HuoBao"},{"Title":"办卡攻略","icon":"http://static.sujintech.com/","WapUrl":"http://api.sujintech.com/bankcard.html"},{"Title":"收款攻略","icon":"http://static.sujintech.com/","WapUrl":"http://api.sujintech.com/Swipe.html"}]}
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
        private List<BtnListBean> btnList;

        public List<BtnListBean> getBtnList() {
            return btnList;
        }

        public void setBtnList(List<BtnListBean> btnList) {
            this.btnList = btnList;
        }

        public static class BtnListBean {
            /**
             * Title : 邀请好友
             * icon : http://static.sujintech.com/
             * WapUrl : QRcode
             */

            private String Title;
            private String icon;
            private String WapUrl;

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getWapUrl() {
                return WapUrl;
            }

            public void setWapUrl(String WapUrl) {
                this.WapUrl = WapUrl;
            }
        }
    }
}
