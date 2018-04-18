package com.sujin.sjpay.protocol;

/**
 * Created by QJS on 2018/3/20.
 */

public class VersionResponse {


    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-03-20 14:35:55
     * data : {"update":"1.修复了一些bug","version":"1.0.1","download":"dxn47a9egzljq3pw","isupgrade":0,"CTime":"2018-03-19T20:29:19.907","Platform":2,"PlatformTxt":"Android"}
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
         * update : 1.修复了一些bug
         * version : 1.0.1
         * download : dxn47a9egzljq3pw
         * isupgrade : 0  0不强制，1强制
         * CTime : 2018-03-19T20:29:19.907
         * Platform : 2
         * PlatformTxt : Android
         */

        private String update;
        private String version;
        private String download;
        private int isupgrade;
        private String CTime;
        private int Platform;
        private String PlatformTxt;

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public int getIsupgrade() {
            return isupgrade;
        }

        public void setIsupgrade(int isupgrade) {
            this.isupgrade = isupgrade;
        }

        public String getCTime() {
            return CTime;
        }

        public void setCTime(String CTime) {
            this.CTime = CTime;
        }

        public int getPlatform() {
            return Platform;
        }

        public void setPlatform(int Platform) {
            this.Platform = Platform;
        }

        public String getPlatformTxt() {
            return PlatformTxt;
        }

        public void setPlatformTxt(String PlatformTxt) {
            this.PlatformTxt = PlatformTxt;
        }
    }
}
