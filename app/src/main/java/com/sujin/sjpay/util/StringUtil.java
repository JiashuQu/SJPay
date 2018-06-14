package com.sujin.sjpay.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String EMPTY = "";

    public static boolean isBlank(String str) {
        return (str == null || "".equals(str));
    }

    public static String changeResponseStr(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, str.indexOf("request")));
        stringBuffer.append("response");
        stringBuffer
                .append(" type=\"0\" seq=\"0\" resCode=\"0\"><head><resMsg>success</resMsg></head><message><em>成功</em><ec>0</ec>");
        stringBuffer.append(str.substring(str.indexOf("<message>") + 9, str.indexOf("</message>") + 10));
        stringBuffer.append("<sign></sign><key></key></response>");
        return stringBuffer.toString();
    }

    public static String getUUId() {
        return UUID.randomUUID().toString();
//        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static String changeStr(String str) {
        if (isBlank(str)) {
            return "--";
        }
        return str;
    }

    //应用默认金额数字都保留到小数点后两位，要截取出来
    //长字符串的转化中文表示处理，规则为：超过一百万，如有必要标示为小数点后一位，超过一亿，

    /**
     * @param str 示例 101000.00 1000000 10000 11111 1000
     * @return 10.1万
     */
    public static String rawIntStr2IntStr(String str) {
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf("."));
        }
        if (str.length() > 4 && str.length() < 9) {
            //超过万不超过亿
            char c = str.charAt(str.length() - 4);
            if (Integer.valueOf(c + "") > 0) {
                str = str.substring(0, str.length() - 4) + "." + c + "万";
            } else {
                str = str.substring(0, str.length() - 4) + "万";
            }
        } else if (str.length() > 8) {
            //超过亿
            boolean flag = false;
            StringBuffer buffer = new StringBuffer();
            for (int i = 4; i < 9; i++) {
                char c = str.charAt(str.length() - i);
                if (flag) {
                    buffer.insert(0, c);
                } else if (Integer.valueOf(c + "") > 0) {
                    flag = true;
                    buffer.append(c);
                }
            }
            //开始拼装
            if (buffer.toString().length() != 0) {
                str = str.substring(0, str.length() - 8) + "." + buffer.toString() + "亿";
            } else {
                str = str.substring(0, str.length() - 8) + "亿";
            }
        } else {
            //不超过万的情况，现实小数点后两位.00
            str += ".00";
        }
        return str;
    }

    //校验手机号码,精确地
    public static boolean isLegalPhoneNumDetail(String phoneNum) {
        if (StringUtil.isBlank(phoneNum)) {
            return false;
        }
//		else return phoneNum.matches("^(13[0-9]|17[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|4|3|5|6|7|8|9])\\d{8}$");
        else return phoneNum.matches("^1\\d{10}$");
    }

    //校验手机号码
    public static boolean isLegalPhoneNum(String phoneNum) {
        if (StringUtil.isBlank(phoneNum)) {
            return false;
        } else return phoneNum.matches("^1\\d{10}$");
    }

    //校验身份证号码
    public static boolean isLegalIdNum(String idNum) {
        if (StringUtil.isBlank(idNum)) {
            return false;
        } else return idNum.matches("^\\d{17}[0-9Xx]$");
    }

    //校验中国用户名
    public static boolean isFailChineseName(String homeName) {
        if (StringUtil.isBlank(homeName)) {
            return false;
        }

//        return !homeName.matches("(?:[\u4E00-\u9FFF]{1,8}·\u4E00-\u9FFF]{1,8})|(?:[\u4E00-\u9FFF]{2,5})");
        return !homeName.matches("^\\s*[\\u4e00-\\u9fa5]{1,}[\\u4e00-\\u9fa5.·]{0,15}[\\u4e00-\\u9fa5]{1,}\\s*$");
    }

    //校验邮箱
    public static boolean isLegalMailAddress(String mail) {
        if (StringUtil.isBlank(mail)) {
            return false;
        } else
            return mail.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    //校验银行卡号
    public static boolean isLegalBankCardNo(String bankNo) {
        if (StringUtil.isBlank(bankNo)) {
            return false;
        } else return bankNo.matches("^\\d{16,19}$");
    }

    //混淆手机号码，中间添加*格式   133****1236
    public static String blurPhoneNo(String phoneNo) {
        if (StringUtil.isBlank(phoneNo)) {
            return phoneNo;
        } else if (StringUtil.isLegalPhoneNum(phoneNo)) {
            return phoneNo.substring(0, 3) + "****" + phoneNo.substring(7, 11);
        }
        return null;
    }

    //混淆身份证号，中间添加*格式   133123*********236
    public static String blurIdCardNo(String idNo) {
        if (StringUtil.isBlank(idNo)) {
            return idNo;
        } else if (StringUtil.isLegalIdNum(idNo)) {
            return idNo.substring(0, 4) + "**********"
                    + idNo.substring(idNo.length() - 4, idNo.length());
        }
        return null;
    }

    //混淆银行卡号，中间添加*格式   6228 **** **** 6217
    public static String blurBankCardNo(String bankNo) {
        if (StringUtil.isBlank(bankNo)) {
            return bankNo;
        } else if (StringUtil.isLegalBankCardNo(bankNo)) {
            return bankNo.substring(0, 4) + " **** **** "
                    + bankNo.substring(bankNo.length() - 4, bankNo.length());
        }
        return null;
    }

    //是否是合法的充值金额，是大于0的正整数
    public static boolean isLegalAmount(String amount) {
        String prexString = "^[1-9]\\d*$";
        return amount.matches(prexString);
    }

    /**
     * 超过十万的显示处理
     *
     * @param money 金额【可能带有元单位】
     */
    public static String exceedTenW(String money) {
        money = money.replace("元", "");
        double fMoney = Double.parseDouble(money);
        if (fMoney > 100000) {
            return fMoney / 10000.00 + "万";
        } else {
            return money + "元";
        }
    }

    /**
     * 超过十万的显示处理
     *
     * @param money 金额【可能带有元单位】
     */
    public static String exceedTenW3(String money) {
        money = money.replace("元", "");
        double fMoney = Double.parseDouble(money);
        if (fMoney > 100000) {
            return fMoney / 10000.00 + "0万";
        } else {
            return money + "元";
        }
    }

    /**
     * 当大于等于100000时，要以X万来显示,如果为10000的非整数倍，取两位小数，并不四舍五入，不足10万的不保留小数
     *
     * @param money
     * @return
     */
    public static String exceedTenW2(String money) {
//		money = money.replace("元", "");
        money = money.substring(0, money.lastIndexOf("."));
        if (money.length() > 5) {
            String c = money.substring(money.length() - 4, money.length() - 2);
            if (Integer.valueOf(c) > 0) {
                money = money.substring(0, money.length() - 4) + "." + c + "万";
            } else {
                money = money.substring(0, money.length() - 4) + "万";
            }
        } else {
            money = money + "元";
        }
        return money;
    }

    /**
     * 当大于等于100000时，要以X万来显示,如果为10000的非整数倍，取两位小数，并不四舍五入，不足10万的不保留小数
     *
     * @param money
     * @return
     */
    public static String exceedTenW4(String money) {
        money = money.replace("元", "");
        String moneyStr = money;
        money = money.substring(0, money.lastIndexOf("."));
        if (money.length() > 5) {
            String c = money.substring(money.length() - 4, money.length() - 2);
            if (Integer.valueOf(c) > 0) {
                money = money.substring(0, money.length() - 4) + "." + c + "万";
            } else {
                money = money.substring(0, money.length() - 4) + "万";
            }
        } else {
            money = round(moneyStr) + "元";
        }
        return money;
    }

    /**
     * 保留小数点后两位
     *
     * @param value
     * @return
     */
    public static String round(String value) {
        int scale = 2;
        int roundingMode = BigDecimal.ROUND_HALF_EVEN;
        BigDecimal decimal = new BigDecimal(value);
        decimal = decimal.setScale(scale, roundingMode);
        return decimal.toString();
    }

    /**
     * 正则判断密码特殊字符
     *
     * @param password
     * @return
     */
    public static boolean regPassword(String password) {
        String regPaw = "[a-zA-Z0-9~!@#$^*.]+";
        boolean isPassword = password.matches(regPaw);
        return isPassword;
    }

    /**
     * 正则判断邀请码特殊字符
     *
     * @param inviteCode
     * @return
     */
    public static boolean regInviteCode(String inviteCode) {
        String regPaw = "^[0-9a-zA-Z]{6}$";
        boolean isPassword = inviteCode.matches(regPaw);
        return isPassword;
    }

    /**
     * 返回格式化金钱
     *
     * @param money
     * @return
     */
    public static String getMoneyFormat(String money) {
        try {
            BigDecimal in = new BigDecimal(money);
            DecimalFormat out = new DecimalFormat("#,###,###,###,###,###,##0.00");
            return out.format(in);
        } catch (NumberFormatException e) {
            return AppConstants.NUMBER_ZERO;
        }

    }

    /**
     * 返回格式化金钱不带最后两个0
     *
     * @param money
     * @return
     */
    public static String getMoneyFormatWithoutEnd(String money) {
        try {
            BigDecimal in = new BigDecimal(money);
            DecimalFormat out = new DecimalFormat("#,###,###,###,###,###,##0");
            return out.format(in);
        } catch (NumberFormatException e) {
            return AppConstants.NUMBER_ZERO;
        }
    }

    /**
     * 组成两种颜色的字符串
     *
     * @param context
     * @param s
     * @param index   从第几个字开始变色
     * @param color1  第一种颜色id
     * @param color2  第二种颜色id
     * @return
     */
    public static SpannableString changeStringColor(Context context, String s, int index, @ColorRes int color1, @ColorRes int color2) {
        if (s.length() < index) {
            return new SpannableString(s);
        }
        SpannableString content = new SpannableString(s);
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color1)), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color2)), index, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return content;
    }

    /**
     * 改变从index开始的字符大小
     *
     * @param s
     * @param index        从第几个字符开始
     * @param relativeSize 相对大小倍数
     * @return
     */
    public static SpannableString changeStringSize(String s, int index, float relativeSize) {
        if (s.length() < index) {
            return new SpannableString(s);
        }
        SpannableString content = new SpannableString(s);
        content.setSpan(new RelativeSizeSpan(relativeSize), index, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return content;
    }

    /**
     * 验证正整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 返回后四个字符串
     *
     * @param s
     * @return
     */
    public static String end4(String s) {
        int i = s.length();
        if (i <= 4) {
            return s;
        } else {
            return s.substring(i - 4, i);
        }
    }

    /**
     * 检查姓名是否符合规则,带提示
     *
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.show("请输入您的姓名");
            return false;
        }
        int length = name.length();
        if (length < 2 || length > 20 || StringUtil.isFailChineseName(name)) {
            ToastUtil.show("姓名输入有误");
            return false;
        }
        return true;
    }

    /**
     * 检查身份证号是否符合规则,带提示
     *
     * @param id
     * @return
     */
    public static boolean checkIdCard(String id) {
        if (TextUtils.isEmpty(id)) {
            ToastUtil.show("请输入您的身份证号码");
            return false;
        }
        int lengthId = id.length();
        if (lengthId < 18 || !StringUtil.isLegalIdNum(id)) {
            ToastUtil.show("身份证号码不正确");
            return false;
        }
        return true;
    }

    /**
     * 检查银行卡号是否符合规则,带提示
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardNumber(String card) {
        if (TextUtils.isEmpty(card)) {
            ToastUtil.show("请输入您的银行卡号");
            return false;
        }
        int cardL = card.length();
        if (cardL < 14 || cardL > 20) {
            ToastUtil.show("银行卡号不正确");
            return false;
        }
        return true;
    }

    /**
     * 检查手机号是否符合规则,带提示
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("请输入您的手机号");
            return false;
        }
        if (!StringUtil.isLegalPhoneNum(phone)) {
            ToastUtil.show("手机号输入错误");
            return false;
        }
        return true;
    }

    /**
     * 检查密码是否符合规则,带提示
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show("请输入密码");
            return false;
        }
        if (password.length() < 6 || password.length() > 16) {
            ToastUtil.show("密码必须为6~16字母，数字，部分符号");
            return false;
        }
        return true;
    }

    /**
     * 半角转为全角,字符全角化。防止TextView换行后不整齐
     * 即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了。
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        if (TextUtils.isEmpty(input)) {
            return EMPTY;
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * 提取数字
     *
     * @param content
     * @return
     */
    public static String getNumbers(String content) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }

    /**
     * 替换字符串中的“/”
     *
     * @param s
     * @return
     */
    public static String replaceSprit(String s) {
        if (s.contains("/")) {
            s = s.replace("/", "_");
        }
        return s;
    }

    /*
* 毫秒转化
*/
    public static String[] formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        String strDay = (day < 10 && day >= 0) ? "0" + day : "" + day; //天
        String strHour = (hour < 10 && hour >= 0) ? "0" + hour : "" + hour;//小时
        String strMinute = (minute < 10 && minute >= 0) ? "0" + minute : "" + minute;//分钟
        String strSecond = (second < 10 && second >= 0) ? "0" + second : "" + second;//秒
        String[] time = {strHour, strMinute, strSecond};

        return time;
    }

    /**
     * 毫秒转换，返回int[]
     *
     * @param ms
     * @return
     */
    public static int[] formatTimeToInt(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        int[] time = {(int) hour, (int) minute, (int) second};

        return time;
    }


    /**
     * 转换String为int
     *
     * @param s
     * @return
     */
    public static int tranStr2Int(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 转换String为int
     *
     * @param s
     * @return
     */
    public static double tranStr2Double(String s) {
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            return 0d;
        }
    }

    /**
     * 转换String为float
     *
     * @param s
     * @return
     */
    public static float tranStr2Float(String s) {
        try {
            return Float.valueOf(s);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }


    /**
     * 保留小数，四舍五入
     * @param d
     * @param i
     * @return
     */
    public static double scaleDouble(double d, int i) {
        BigDecimal bg = new BigDecimal(d);
        d = bg.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }

    /**
     * 保留两位小数
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d);
    }

    /**
     * 删除开头的0
     *
     * @param s
     * @return
     */
    public static String delStartZero(String s) {
        if (s.length() == 2) {
            if (s.startsWith(AppConstants.NUMBER_ZERO)) {
                s = s.substring(1);
            }
        }
        return s;
    }

    /**
     * 格式化银行卡号，四位隔开
     * @param bankNumber
     * @return
     */
    public static String formatBankNumber(String bankNumber){
        if (TextUtils.isEmpty(bankNumber)) return bankNumber;
        int index = 0;
        StringBuffer buffer = new StringBuffer(bankNumber);
        while (index < buffer.length()) {
            if (index == 4 || index == 9 || index == 14 || index == 19 || index == 24 || index == 29) {
                buffer.insert(index, ' ');
            }
            index++;
        }
        return buffer.toString();
    }

    /**
     * 存管专用
     * 格式化银行卡号，前五位隔开，之后每四位隔开
     * @param bankNumber
     * @return
     */
    public static String formatBankNumberFirst5(String bankNumber){
        if (TextUtils.isEmpty(bankNumber)) return bankNumber;
        int index = 0;
        StringBuffer buffer = new StringBuffer(bankNumber);
        while (index < buffer.length()) {
            if (index == 5 || index == 10 || index == 15 || index == 20 || index == 25 || index == 30) {
                buffer.insert(index, ' ');
            }
            index++;
        }
        return buffer.toString();
    }

    public static String MD5(String apiName, String s, String apiType) {
        String password = ApiConstants.appType + ApiConstants.firstPassword + apiType + apiName + ApiConstants.secondPassword + s;
        com.lidroid.xutils.util.LogUtils.d(password);
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String MD5(String s) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(s.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String sort(char[] chars){
        Arrays.sort(chars);
        String s = new String(chars);
        return s;
    }

    /**
     * 小数转百分数
     * @param point
     * @return
     */
    public static String changePointToPercent(float point) {
        return String.valueOf(point*100)+"%";
    }

    public static boolean dateCompare(String sysData, String serverData) throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        Date dt1 = df.parse(sysData);//将字符串转换为date类型
        Date dt2 = df.parse(serverData);
        if(dt1.getTime()>dt2.getTime())//比较时间大小,如果dt1大于dt2
        {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置滚动
     * @param tv
     */
    public static void setEllipsize(TextView tv){
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv.setSingleLine(true);
        tv.setSelected(true);
        tv.setFocusable(true);
        tv.setFocusableInTouchMode(true);
    }

}
