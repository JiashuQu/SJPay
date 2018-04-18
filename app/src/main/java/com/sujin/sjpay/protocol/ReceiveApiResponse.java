package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

/**
 * Created by QJS on 2018/3/7.
 */

public class ReceiveApiResponse {
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

    protected ReceiveApi data;

    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public ReceiveApi getData() {
        return data;
    }

    public class ReceiveApi {
        private String PayUrl = StringUtil.EMPTY;
        private int ChannelType;
        private int BankID;

        public String getPayUrl() {
            return PayUrl;
        }

        public int getChannelType() {
            return ChannelType;
        }

        public int getBankID() {
            return BankID;
        }
    }
}
