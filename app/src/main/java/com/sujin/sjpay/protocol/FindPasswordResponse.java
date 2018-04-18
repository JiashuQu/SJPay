package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

/**
 * Created by QJS on 2018/2/28.
 */

public class FindPasswordResponse {
    /**
     * 状态码
     */
    private String backStatus = StringUtil.EMPTY;

    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public Find getData() {
        return data;
    }

    /**
     * 状态信息
     */
    private String message = StringUtil.EMPTY;

    /**
     * 服务器时间
     */
    private String serverTime = StringUtil.EMPTY;

    protected Find data;

    public class Find{
        private String forgetGuid = StringUtil.EMPTY;
        private String mobile = StringUtil.EMPTY;

        public String getForgetGuid() {
            return forgetGuid;
        }

        public String getMobile() {
            return mobile;
        }
    }
}
