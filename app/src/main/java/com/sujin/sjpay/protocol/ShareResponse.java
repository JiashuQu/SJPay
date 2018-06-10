package com.sujin.sjpay.protocol;

public class ShareResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-10 10:07:36
     * data : {"Title":"速金派邀请您一起刷卡赚收益","ShareUrl":"http://testapi.sujintech.com/itapi/invite/reg?u=396DCC4064848F6FE9A6B8FA45F91163","ImageUrl":"","Context":"速金派，移动刷卡神器。价格低，秒到账，邀请好友享收益。"}
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
         * Title : 速金派邀请您一起刷卡赚收益
         * ShareUrl : http://testapi.sujintech.com/itapi/invite/reg?u=396DCC4064848F6FE9A6B8FA45F91163
         * ImageUrl :
         * Context : 速金派，移动刷卡神器。价格低，秒到账，邀请好友享收益。
         */

        private String Title;
        private String ShareUrl;
        private String ImageUrl;
        private String Context;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getShareUrl() {
            return ShareUrl;
        }

        public void setShareUrl(String ShareUrl) {
            this.ShareUrl = ShareUrl;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public String getContext() {
            return Context;
        }

        public void setContext(String Context) {
            this.Context = Context;
        }
    }
}
