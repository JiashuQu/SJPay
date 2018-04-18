package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

/**
 * Created by QJS on 2018/3/5.
 */

public class UploadImgResponse {
    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }


    /**
     * 状态码
     */
    private String backStatus = StringUtil.EMPTY;

    /**
     * 状态信息
     */
    private String message = StringUtil.EMPTY;

    /**
     * 服务器时间
     */
    private String serverTime = StringUtil.EMPTY;

    public UploadImg getData() {
        return data;
    }

    protected UploadImg data;

    public class UploadImg{
        private String ImgUrl = StringUtil.EMPTY;
        private int ID;
        private String fileLength = StringUtil.EMPTY;

        public String getImgUrl() {
            return ImgUrl;
        }

        public int getID() {
            return ID;
        }

        public String getFileLength() {
            return fileLength;
        }
    }
}
