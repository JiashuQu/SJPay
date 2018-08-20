package com.sujin.sjpay.android;

import com.sujin.sjpay.fragment.HomeFragment;
import com.sujin.sjpay.fragment.MyFragment;
import com.sujin.sjpay.fragment.PayFragment;

/**
 * Created by QJS on 2018/1/31.
 */

public class AppConstants {
    // log开关
    public static final boolean DEBUG = true;  // false true

    public static final String LOG_TAG_HTTP = "SJHttp";

    // 用户信息
    public static final String SP_NAME_USER_INFO = "user_info";
    public static final String SP_DATA_USER_PHONE = "data_user_phone";
    public static final String SP_DATA_USER_ID = "user_id";
    public static final String SP_DATA_IS_REAL_STATE = "is_real_state";

    /*------------------------ MainActivity Tab tag       ------------------*/
    public static final String TAB_TAG_HOME = HomeFragment.class.getSimpleName();
    public static final String TAB_TAG_PAY = PayFragment.class.getSimpleName();
    public static final String TAB_TAG_MY = MyFragment.class.getSimpleName();
	/*------------------------ MainActivity Tab tag  end  ------------------*/

    /*------------------------ MainActivity Tab 名字       ------------------*/
    public static final String TAB_NAME_HOME = "首页";
    public static final String TAB_NAME_PAY = "收款";
    public static final String TAB_NAME_MY = "我的";
    public static final String INTENT_KEY_TAB_TAG = "tab_tag";
	/*------------------------ MainActivity Tab 名字  end  ------------------*/

    /*------------------------    数字 string    ------------------------*/
    public static final String NUMBER_ZERO = "0";
    public static final String NUMBER_ONE = "1";
    public static final String NUMBER_TWO = "2";
	/*------------------------ 数字   end  ------------------------*/

    public static final int INTENT_REQUEST_CODE_REFRESH = 25;

    public static final String INTENT_KEY_PROVINCE = "province";
    public static final String INTENT_KEY_CITY = "city";
    public static final String INTENT_KEY_CITY_CODE = "city_code";
    public static final String INTENT_KEY_BANK_CELL = "bank_cell";
    public static final String INTENT_KEY_BANK_CHANNEL = "bank_channel";
    public static final int INTENT_REQUEST_CODE_FROM_BAND_CARD = 19;

    // 线上最新app版本
    public static final String SP_APP_VERSION = "netVersion"; // 是否有现版本
    public static final String SP_APP_VERSION_CODE = "versionCode"; // 版本号
    public static final String SP_APP_DES = "DES";
    public static final String SP_APP_IS_MUST_UPDATE = "isMustUpdate";
    public static final String SP_APP_URL = "app_url";
}
