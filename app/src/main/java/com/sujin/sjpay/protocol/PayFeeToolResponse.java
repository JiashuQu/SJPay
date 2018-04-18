package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

/**
 * Created by QJS on 2018/3/7.
 */

public class PayFeeToolResponse {

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

    protected PayFeeTool data;

    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public PayFeeTool getData() {
        return data;
    }

    public class PayFeeTool {
        private String Amount = StringUtil.EMPTY;
        private String Rate1 = StringUtil.EMPTY;
        private String Rate3 = StringUtil.EMPTY;
        private String PayFee = StringUtil.EMPTY;
        private String BasicFee = StringUtil.EMPTY;
        private String ExTargetFee = StringUtil.EMPTY;
        private String ActualAmount = StringUtil.EMPTY;

        public String getAmount() {
            return Amount;
        }

        public String getRate1() {
            return Rate1;
        }

        public String getRate3() {
            return Rate3;
        }

        public String getPayFee() {
            return PayFee;
        }

        public String getBasicFee() {
            return BasicFee;
        }

        public String getExTargetFee() {
            return ExTargetFee;
        }

        public String getActualAmount() {
            return ActualAmount;
        }
    }

}
