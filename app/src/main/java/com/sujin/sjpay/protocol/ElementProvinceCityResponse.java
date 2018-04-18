package com.sujin.sjpay.protocol;

import com.sujin.sjpay.util.StringUtil;

/**
 * Created by QJS on 2018/3/5.
 */

public class ElementProvinceCityResponse {
    private String Id = StringUtil.EMPTY;
    private String Code = StringUtil.EMPTY;
    private String Name = StringUtil.EMPTY;

    public String getId() {
        return Id;
    }

    public String getCode() {
        return Code;
    }

    public String getName() {
        return Name;
    }
}
