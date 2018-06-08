package com.sujin.sjpay.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sujin.sjpay.R;

public class InviteDetailDialog extends Dialog {
    private Context context;
    private String content;

    public InviteDetailDialog(Context context, String content) {
        super(context, R.style.SercurityDialogTheme);
        this.context = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_invite_detail, null);
        setContentView(view);

        TextView tvInviteDetail = view.findViewById(R.id.tv_invite_detail);
        tvInviteDetail.setText(content);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.height = (int)(d.heightPixels * 0.5);
        dialogWindow.setAttributes(lp);
    }
}
