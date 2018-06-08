package com.sujin.sjpay.protocol;

import java.util.List;

public class InviteRuleResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-07 20:41:32
     * data : {"simple":[{"line":"1.每推荐一名用户开通VIP最高可获得39元现金奖励"},{"line":"2.每推荐一名用户开通SVIP最高可获得79元现金奖励"},{"line":"3.用户邀请好友刷卡可享受高达0.1%的分佣"},{"line":"4.邀请好友刷卡金额累计15万，将送您永久VIP！"}],"complex":[{"line":"1.普通用户刷卡金额累加达到8万元，将获得永久VIP会员"},{"line":"2.普通用户邀请好友：-所有好友刷卡金额累加达到15万元，邀请人将获得永久VIP会员"},{"line":"3.普通用户邀请好友开通VIP会员：-邀请人获得分润19元"},{"line":"4.普通用户邀请好友开通SVIP会员：-被邀请人为普通用户，邀请人获得分润49元；-被邀请人为VIP会员，邀请人获得分润29元"},{"line":"5.VIP会员邀请的好友为普通用户：-刷卡分润a元（a =（普通会员费率 - VIP费率）×刷卡金额"},{"line":"6.VIP会员邀请好友开通VIP会员：-邀请人获得分润29元"},{"line":"7.VIP会员邀请好友开通SVIP会员： -被邀请人为普通用户，邀请人获得分润69元；-被邀请人为VIP会员，邀请人获得分润49元"},{"line":"8.SVIP会员邀请的好友为普通用户：-刷卡分润b元（b =（普通会员费率 - SVIP费率）×刷卡金额)"},{"line":"9.SVIP会员邀请好友开通VIP会员：-刷卡分润c元（c =（VIP费率 - SVIP费率）×刷卡金额）-邀请人获得分润39元"},{"line":"10.SVIP会员邀请好友开通SVIP会员：-被邀请人为普通用户，邀请人获得分润79元；-被邀请人为VIP会员，邀请人获得分润59元"}]}
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
        private List<SimpleBean> simple;
        private List<ComplexBean> complex;

        public List<SimpleBean> getSimple() {
            return simple;
        }

        public void setSimple(List<SimpleBean> simple) {
            this.simple = simple;
        }

        public List<ComplexBean> getComplex() {
            return complex;
        }

        public void setComplex(List<ComplexBean> complex) {
            this.complex = complex;
        }

        public static class SimpleBean {
            /**
             * line : 1.每推荐一名用户开通VIP最高可获得39元现金奖励
             */

            private String line;

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }
        }

        public static class ComplexBean {
            /**
             * line : 1.普通用户刷卡金额累加达到8万元，将获得永久VIP会员
             */

            private String line;

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }
        }
    }
}
