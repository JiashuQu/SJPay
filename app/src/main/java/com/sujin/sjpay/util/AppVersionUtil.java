package com.sujin.sjpay.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by QJS on 2016/8/1.
 */
public class AppVersionUtil {

    /**
     * 获取App版本号
     * @param context
     * @return version
     */
    public static String getAppVersion(Context context){
    // PackageManager 包管理器 读取所有程序清单文件信息
        PackageManager pm = context.getPackageManager();
        // 根据应用程序的包名 获取到应用程序信息
        try {
            // getPackageName() 获取当前程序的包名
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            String version = packageInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 比较版本号大小
     * @param netVersion 线上最新版本
     * @param localVersion app当前版本
     * @return true升级
     */
    public static boolean compareAppVersion(String netVersion, String localVersion){
        String net = netVersion.replaceAll("\\.", "");
        String local = localVersion.replaceAll("\\.", "");
        return Integer.parseInt(net) > Integer.parseInt(local);
    }
}
