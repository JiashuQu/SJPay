package com.sujin.sjpay.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sujin.sjpay.R;

public class DialogUtil {

    public static Dialog mProgressDialog;

    /**
     * @param context
     * @Description: 显示加载提示框
     */
    public static void showLoading(Context context, boolean isCanCancel) {
        showLoading(context, context.getResources().getString(R.string.loading), isCanCancel);
    }

    /**
     * @param context
     * @param text    提示内容
     * @Description: 显示加载提示框
     */
    public static void showLoading(Context context, String text, boolean isCanCancel) {
        if (mProgressDialog != null) {
            dismisLoading();
        }
        mProgressDialog = null;

        mProgressDialog = buildLoadingDialog(context, text);

        mProgressDialog.setCancelable(isCanCancel);

        try {
            mProgressDialog.show();
        } catch (Exception e) {

        }

    }

    /**
     * @param context
     * @param text
     * @return
     * @Description: 创建加载提示框
     */
    public static Dialog buildLoadingDialog(Context context, String text) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        TextView loadingTextView = (TextView) layout.findViewById(R.id.tv_loading_dialog);
        loadingTextView.setText(text);
        Dialog loadingDialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));// 设置布局
        android.view.WindowManager.LayoutParams attributes = loadingDialog
                .getWindow().getAttributes();
        attributes.alpha = 0.8f;
        loadingDialog.getWindow().setAttributes(attributes);
        return loadingDialog;
    }

    /**
     * @Description: 关闭加载提示框
     */
    public static void dismisLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
