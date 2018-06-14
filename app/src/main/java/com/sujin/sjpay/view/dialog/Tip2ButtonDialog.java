package com.sujin.sjpay.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.util.UIUtil;

import butterknife.ButterKnife;

/**
 * 提示框，有两个Button的Dialog
 * Created by czb on 2016/8/2.
 */
public class Tip2ButtonDialog extends Dialog {
    public Tip2ButtonDialog(Context context) {
        super(context);
    }

    public Tip2ButtonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String tip;
        private String leftText;
        private String rightText;
        public TextView tvDialogTip2ButtonRight;
        private View.OnClickListener leftListener;
        private View.OnClickListener rightListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTip(String tip) {
            this.tip = tip;
            return this;
        }

        public Builder setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }

        public Builder setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }

        public Builder setLeftListener(View.OnClickListener leftListener) {
            this.leftListener = leftListener;
            return this;
        }

        public Builder setRightListener(View.OnClickListener rightListener) {
            this.rightListener = rightListener;
            return this;
        }

        public Tip2ButtonDialog create() {
            final Tip2ButtonDialog dialog = new Tip2ButtonDialog(context, R.style.dialog);
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.dialog_tip_2_button, null, false);// 得到加载view
            ButterKnife.bind(v);
            TextView tvDialogTip2ButtonTip = (TextView) v.findViewById(R.id.tv_dialog_tip_2_button_tip);
            TextView tvDialogTip2ButtonLeft = (TextView) v.findViewById(R.id.tv_dialog_tip_2_button_left);
            tvDialogTip2ButtonRight = (TextView) v.findViewById(R.id.tv_dialog_tip_2_button_right);

            tvDialogTip2ButtonTip.setText(tip);

            if (!TextUtils.isEmpty(leftText)){
                tvDialogTip2ButtonLeft.setText(leftText);
            }

            if (!TextUtils.isEmpty(rightText)){
                tvDialogTip2ButtonRight.setText(rightText);
            }

            if (leftListener != null){
                tvDialogTip2ButtonLeft.setOnClickListener(leftListener);
            } else {
                tvDialogTip2ButtonLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            if (rightListener != null){
                tvDialogTip2ButtonRight.setOnClickListener(rightListener);
            } else {
                tvDialogTip2ButtonRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            dialog.setContentView(v);

            Window dialogWindow = dialog.getWindow();
//			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//			dialogWindow.setGravity(Gravity.CENTER);

			/*
			 * 将对话框的大小按屏幕大小的百分比设置
			 */
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (UIUtil.getScreenWidth() * 0.7);
            dialogWindow.setAttributes(p);

            return dialog;
        }
    }
}
