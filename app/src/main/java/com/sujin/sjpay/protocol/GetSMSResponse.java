package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

/**
 * Created by QJS on 2018/2/28.
 */

public class GetSMSResponse {
    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public GetData getData() {
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

    protected GetData data;

    public class GetData{
        private String regGuid = StringUtil.EMPTY;

        public String getForgetGuid() {
            return forgetGuid;
        }

        private String forgetGuid = StringUtil.EMPTY;

        public String getRegGuid() {
            return regGuid;
        }
    }
}
