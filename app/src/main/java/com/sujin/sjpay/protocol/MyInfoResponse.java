package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

import java.io.Serializable;

/**
 * Created by QJS on 2018/3/1.
 */

public class MyInfoResponse implements Serializable{
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

    protected MyInfo data;

    public MyInfo getData() {
        return data;
    }

    public class MyInfo implements Serializable{
        private String Mobile = StringUtil.EMPTY;
        private int IsRealState;
        private String IsRealStateText = StringUtil.EMPTY;
        private String RealName = StringUtil.EMPTY;
        private String IdCard = StringUtil.EMPTY;
        private String VipTypeTxt = StringUtil.EMPTY;
        private String BankCard = StringUtil.EMPTY;
        private String BankName = StringUtil.EMPTY;
        private String BankCode = StringUtil.EMPTY;
        private String Rate1 = StringUtil.EMPTY;
        private String NoneRate1 = StringUtil.EMPTY;
        private String NoneRate3 = StringUtil.EMPTY;
        public String getNoneRate1() {
            return NoneRate1;
        }

        public String getNoneRate3() {
            return NoneRate3;
        }

        public String getRate1() {
            return Rate1;
        }

        public String getRate3() {
            return Rate3;
        }

        private String Rate3 = StringUtil.EMPTY;

        public String getBankCard() {
            return BankCard;
        }

        public String getBankName() {
            return BankName;
        }

        public String getBankCode() {
            return BankCode;
        }

        public String getVipTypeTxt() {
            return VipTypeTxt;
        }

        public String getMobile() {
            return Mobile;
        }

        public int getIsRealState() {
            return IsRealState;
        }

        public String getIsRealStateText() {
            return IsRealStateText;
        }

        public String getRealName() {
            return RealName;
        }

        public String getIdCard() {
            return IdCard;
        }
    }
}
