package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

import java.io.Serializable;

/**
 * Created by QJS on 2018/3/5.
 */

public class GetPayBankQuotaList implements Serializable {
    private String BankCode = StringUtil.EMPTY;

    public String getBankCode() {
        return BankCode;
    }

    private String BankName = StringUtil.EMPTY;
    private String SingleQuota = StringUtil.EMPTY;
    private String DayQuota = StringUtil.EMPTY;
    private String MouthQuota = StringUtil.EMPTY;


    public String getBankName() {
        return BankName;
    }

    public String getSingleQuota() {
        return SingleQuota;
    }

    public String getDayQuota() {
        return DayQuota;
    }

    public String getMouthQuota() {
        return MouthQuota;
    }
}
