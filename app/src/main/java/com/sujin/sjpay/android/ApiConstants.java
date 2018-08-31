package com.sujin.sjpay.android;

/**
 * Created by QJS on 2018/2/26.
 */

public interface ApiConstants {

//    String APP_URL = "http://testapi.sujintech.com/itapi/";
    String APP_URL = "http://api.sujintech.com/itapi/";

    String API_USERS = "Users/";
    String API_FORGET = "Forget/";
    String API_UPLOAD = "Upload/";
    String API_YEEPAY = "Yeepay/";
    String API_CONFIG = "Config/";
    String API_PROFIT = "Profit/";

    String appType = "itormandroide";
    String firstPassword = "61671437de57573b4ec9c66ef39a615";
    String secondPassword = "c9866fa3d7511f55ad935919d89fab43";

    //接口名
    String GetImgCode = "GetImgCode";
    String SendMsgCode = "SendMsgCode";
    String Register = "Register";
    String Login = "Login";
    String ValidateMobileCode = "ValidateMobileCode";
    String UpdatePassword = "UpdatePassword";
    String Single = "Single";
    String UpImg = "UpImg";
    String AreaCode = "GetAreaCode";
    String GetBankCardList = "GetBankCardList";
    String PayFeeTool = "PayFeeTool";
    String ReceiveApi = "ReceiveApi2";
    String CheckBaseUserIdByMobile = "CheckBaseUserIdByMobile";
    String GetBankList = "GetBankList";
    String QueryPayRecordList = "QueryPayRecordList";
    String BankBind = "BankBind";
    String Version = "GetVersion";
    String PayType = "GetPayType";
    String BankCardActivate = "BankCardActivate";
    String BankCardSubmitActivateCode = "BankCardSubmitActivateCode";
    String UpdateBankMobile = "UpdateBankMobile";
    String BannerList = "BannerList";
    String GetIndexData = "GetIndexData";
    String QRcode = "QRcode";
    String OpenVipRule = "OpenVipRule";
    String InviteRule = "InviteRule";
    String InviteList = "InviteList";
    String GetVipTypeIntroduce = "GetVipTypeIntroduce";
    String IncomeTotal = "IncomeTotal";
    String IncomeList = "IncomeList";
    String Share = "Share";
    String AccountTotal = "AccountTotal";
    String AccountRecord = "AccountRecord";
    String AvatarImg = "AvatarImg";
    //接口名URL
    String getImgCode = APP_URL + API_USERS + GetImgCode;
    String getSmsCode = APP_URL + API_USERS  + SendMsgCode;
    String getRegister = APP_URL + API_USERS  + Register;
    String getLogin = APP_URL + API_USERS  + Login;
    String getSingleInfo = APP_URL + API_USERS  + Single;
    String getUpdateNewPassword = APP_URL + API_USERS  + UpdatePassword;
    String getCheckBaseUserIdByMobile = APP_URL + API_USERS  + CheckBaseUserIdByMobile;
    String getHistoryPayBankCardList = APP_URL + API_USERS  + GetBankCardList;
    String getUserBankList = APP_URL + API_USERS  + GetBankList;
    String getQueryPayRecordList = APP_URL + API_USERS  + QueryPayRecordList;
    String getBankBind = APP_URL + API_USERS  + BankBind;
    String getBankCardActivate = APP_URL + API_USERS  + BankCardActivate;
    String getBankCardSubmitActivateCode = APP_URL + API_USERS  + BankCardSubmitActivateCode;
    String getUpdateBankMobile = APP_URL + API_USERS  + UpdateBankMobile;
    String getAccountTotal = APP_URL + API_USERS  + AccountTotal;
    String getAccountRecord = APP_URL + API_USERS  + AccountRecord;
    String setAvatarImg = APP_URL + API_USERS  + AvatarImg;
    String getBankList = APP_URL + API_USERS  + GetBankList;

    String getForgetImgCode = APP_URL + API_FORGET  + GetImgCode;
    String getForgetSmsCode = APP_URL + API_FORGET  + SendMsgCode;
    String getForgetValidateMobileCode = APP_URL + API_FORGET  + ValidateMobileCode;
    String getUpdatePassword = APP_URL + API_FORGET  + UpdatePassword;

    String getUploadImg = APP_URL + API_UPLOAD  + UpImg;

    String getBankAreaCode = APP_URL + API_YEEPAY  + AreaCode;
    String getYeePayRegister = APP_URL + API_YEEPAY  + Register;
    String getPayFeeTool = APP_URL + API_YEEPAY  + PayFeeTool;
    String getReceiveApi = APP_URL + API_YEEPAY  + ReceiveApi;

    String getVersion = APP_URL + API_CONFIG  + Version;
    String getPayType = APP_URL + API_CONFIG  + PayType;
    String getBannerList = APP_URL + API_CONFIG  + BannerList;
    String getGetIndexData = APP_URL + API_CONFIG  + GetIndexData;
    String getVipTypeIntroduce = APP_URL + API_CONFIG  + GetVipTypeIntroduce;

    String getQRcode = APP_URL + API_PROFIT  + QRcode;
    String getOpenVipRule = APP_URL + API_PROFIT  + OpenVipRule;
    String getInviteRule = APP_URL + API_PROFIT  + InviteRule;
    String getInviteList = APP_URL + API_PROFIT  + InviteList;
    String getIncomeTotal = APP_URL + API_PROFIT  + IncomeTotal;
    String getIncomeList = APP_URL + API_PROFIT  + IncomeList;
    String getShare = APP_URL + API_PROFIT  + Share;
}
