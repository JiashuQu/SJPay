package com.sujin.sjpay.protocol;

import java.util.List;

public class GetVipResponse {

    /**
     * backStatus : 0
     * message : success
     * serverTime : 2018-06-07 14:29:09
     * data : {"listTitle":"微信扫码支付","list":[{"Title":"SVIP会员","CurrentPrice":"99","OriginalPrice":"原价￥199","QRcode":"http://static.sujintech.com/upload/QRcode/99svip.png"},{"Title":"VIP会员","CurrentPrice":"49","OriginalPrice":"原价￥99","QRcode":"http://static.sujintech.com/upload/QRcode/49vip.png"},{"Title":"升级SVIP","CurrentPrice":"69","OriginalPrice":"原价￥119","QRcode":"http://static.sujintech.com/upload/QRcode/u69svip.png"}],"list2Title":"VIP尊享权益","list2":[{"Title":"1.超低费率","Context":"SVIP有积分0.43%，无积分0.39% VIP有积分0.48%，无积分0.44%"},{"Title":"2.邀请好友超高返佣","Context":"好友开通SVIP一次性最高返现50元"},{"Title":"3.单单分润","Context":"好友每次刷卡，即可享受高达0.1%的利润返现"}],"DingjiDaili":"客户经理：18610122058\n客户经理：18506120807\n微信客服：SJpay-op","Tips":"选择您需要开通会员对应的二维码并使用微信支付，备在注速金派已注册的账号，工作人员确认收款后立刻给您开通会员。您还可以将二维码保存在手机里面，方便支付。如有疑问请关注我们的微信公众号SJ派，联系在线客服"}
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
         * listTitle : 微信扫码支付
         * list : [{"Title":"SVIP会员","CurrentPrice":"99","OriginalPrice":"原价￥199","QRcode":"http://static.sujintech.com/upload/QRcode/99svip.png"},{"Title":"VIP会员","CurrentPrice":"49","OriginalPrice":"原价￥99","QRcode":"http://static.sujintech.com/upload/QRcode/49vip.png"},{"Title":"升级SVIP","CurrentPrice":"69","OriginalPrice":"原价￥119","QRcode":"http://static.sujintech.com/upload/QRcode/u69svip.png"}]
         * list2Title : VIP尊享权益
         * list2 : [{"Title":"1.超低费率","Context":"SVIP有积分0.43%，无积分0.39% VIP有积分0.48%，无积分0.44%"},{"Title":"2.邀请好友超高返佣","Context":"好友开通SVIP一次性最高返现50元"},{"Title":"3.单单分润","Context":"好友每次刷卡，即可享受高达0.1%的利润返现"}]
         * DingjiDaili : 客户经理：18610122058
         客户经理：18506120807
         微信客服：SJpay-op
         * Tips : 选择您需要开通会员对应的二维码并使用微信支付，备在注速金派已注册的账号，工作人员确认收款后立刻给您开通会员。您还可以将二维码保存在手机里面，方便支付。如有疑问请关注我们的微信公众号SJ派，联系在线客服
         */

        private String listTitle;
        private String list2Title;
        private String DingjiDaili;
        private String Tips;
        private List<ListBean> list;
        private List<List2Bean> list2;

        public String getListTitle() {
            return listTitle;
        }

        public void setListTitle(String listTitle) {
            this.listTitle = listTitle;
        }

        public String getList2Title() {
            return list2Title;
        }

        public void setList2Title(String list2Title) {
            this.list2Title = list2Title;
        }

        public String getDingjiDaili() {
            return DingjiDaili;
        }

        public void setDingjiDaili(String DingjiDaili) {
            this.DingjiDaili = DingjiDaili;
        }

        public String getTips() {
            return Tips;
        }

        public void setTips(String Tips) {
            this.Tips = Tips;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<List2Bean> getList2() {
            return list2;
        }

        public void setList2(List<List2Bean> list2) {
            this.list2 = list2;
        }

        public static class ListBean {
            /**
             * Title : SVIP会员
             * CurrentPrice : 99
             * OriginalPrice : 原价￥199
             * QRcode : http://static.sujintech.com/upload/QRcode/99svip.png
             */

            private String Title;
            private String CurrentPrice;
            private String OriginalPrice;
            private String QRcode;

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getCurrentPrice() {
                return CurrentPrice;
            }

            public void setCurrentPrice(String CurrentPrice) {
                this.CurrentPrice = CurrentPrice;
            }

            public String getOriginalPrice() {
                return OriginalPrice;
            }

            public void setOriginalPrice(String OriginalPrice) {
                this.OriginalPrice = OriginalPrice;
            }

            public String getQRcode() {
                return QRcode;
            }

            public void setQRcode(String QRcode) {
                this.QRcode = QRcode;
            }
        }

        public static class List2Bean {
            /**
             * Title : 1.超低费率
             * Context : SVIP有积分0.43%，无积分0.39% VIP有积分0.48%，无积分0.44%
             */

            private String Title;
            private String Context;

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getContext() {
                return Context;
            }

            public void setContext(String Context) {
                this.Context = Context;
            }
        }
    }
}
