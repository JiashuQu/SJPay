package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by QJS on 2018/3/5.
 */

public class GetPayBankQuotaListResponse implements Serializable {
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

    protected ArrayList<GetPayBankQuotaList> data;

    public String getBackStatus() {
        return backStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public ArrayList<GetPayBankQuotaList> getData() {
        return data;
    }
}
