package com.sujin.sjpay.protocol;

import java.io.Serializable;

/**
 * Created by QJS on 2018/3/1.
 */

public class MyInfoResponse implements Serializable{

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-07 09:46:29
     * data : {"Mobile":"18506120807","IsRealState":1,"IsRealStateText":"已认证","RealName":"渠嘉树","IdCard":"320321********4219","VipType":0,"VipTypeTxt":"Boss","AvatarImg":"http://static.sujintech.com/","Rate1":"0.41%","Rate3":"1.0","NoneRate1":"0.30%","NoneRate3":"0.5","BankCard":"6217900100011156547","BankName":"中国银行","BankCode":"BOC","BaseUserName":"--"}
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

    public static class DataBean  implements Serializable{
        /**
         * Mobile : 18506120807
         * IsRealState : 1
         * IsRealStateText : 已认证
         * RealName : 渠嘉树
         * IdCard : 320321********4219
         * VipType : 0
         * VipTypeTxt : Boss
         * AvatarImg : http://static.sujintech.com/
         * Rate1 : 0.41%
         * Rate3 : 1.0
         * NoneRate1 : 0.30%
         * NoneRate3 : 0.5
         * BankCard : 6217900100011156547
         * BankName : 中国银行
         * BankCode : BOC
         * BaseUserName : --
         */

        private String Mobile;
        private int IsRealState;
        private String IsRealStateText;
        private String RealName;
        private String IdCard;
        private int VipType;
        private String VipTypeTxt;
        private String AvatarImg;
        private String Rate1;
        private String Rate3;
        private String NoneRate1;
        private String NoneRate3;
        private String BankCard;
        private String BankName;
        private String BankCode;
        private String BaseUserName;

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public int getIsRealState() {
            return IsRealState;
        }

        public void setIsRealState(int IsRealState) {
            this.IsRealState = IsRealState;
        }

        public String getIsRealStateText() {
            return IsRealStateText;
        }

        public void setIsRealStateText(String IsRealStateText) {
            this.IsRealStateText = IsRealStateText;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getIdCard() {
            return IdCard;
        }

        public void setIdCard(String IdCard) {
            this.IdCard = IdCard;
        }

        public int getVipType() {
            return VipType;
        }

        public void setVipType(int VipType) {
            this.VipType = VipType;
        }

        public String getVipTypeTxt() {
            return VipTypeTxt;
        }

        public void setVipTypeTxt(String VipTypeTxt) {
            this.VipTypeTxt = VipTypeTxt;
        }

        public String getAvatarImg() {
            return AvatarImg;
        }

        public void setAvatarImg(String AvatarImg) {
            this.AvatarImg = AvatarImg;
        }

        public String getRate1() {
            return Rate1;
        }

        public void setRate1(String Rate1) {
            this.Rate1 = Rate1;
        }

        public String getRate3() {
            return Rate3;
        }

        public void setRate3(String Rate3) {
            this.Rate3 = Rate3;
        }

        public String getNoneRate1() {
            return NoneRate1;
        }

        public void setNoneRate1(String NoneRate1) {
            this.NoneRate1 = NoneRate1;
        }

        public String getNoneRate3() {
            return NoneRate3;
        }

        public void setNoneRate3(String NoneRate3) {
            this.NoneRate3 = NoneRate3;
        }

        public String getBankCard() {
            return BankCard;
        }

        public void setBankCard(String BankCard) {
            this.BankCard = BankCard;
        }

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String BankName) {
            this.BankName = BankName;
        }

        public String getBankCode() {
            return BankCode;
        }

        public void setBankCode(String BankCode) {
            this.BankCode = BankCode;
        }

        public String getBaseUserName() {
            return BaseUserName;
        }

        public void setBaseUserName(String BaseUserName) {
            this.BaseUserName = BaseUserName;
        }
    }
}
