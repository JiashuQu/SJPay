package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

import java.io.Serializable;

/**
 * Created by QJS on 2018/3/9.
 */

public class HistoryPayBankCardList implements Serializable {
    private int ID;
    private String BankCard = StringUtil.EMPTY;
    private String Mobile = StringUtil.EMPTY;
    private String BankName = StringUtil.EMPTY;
    private String BankCode = StringUtil.EMPTY;
    private String CTime = StringUtil.EMPTY;

    public String getBankCard() {
        return BankCard;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getBankName() {
        return BankName;
    }

    public String getBankCode() {
        return BankCode;
    }

    public String getCTime() {
        return CTime;
    }
}
