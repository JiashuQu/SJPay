package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

/**
 * Created by QJS on 2018/2/28.
 */

public class LoginResponse {
    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public Login getData() {
        return data;
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

    protected Login data;

    public class Login{
        private String UserId = StringUtil.EMPTY;

        public String getUserId() {
            return UserId;
        }
    }
}
