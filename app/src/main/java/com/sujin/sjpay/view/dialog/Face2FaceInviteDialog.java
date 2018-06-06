package com.sujin.sjpay.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sujin.sjpay.R;

public class Face2FaceInviteDialog extends Dialog {
    private Context context;
    private String content;
    private String imgUrl;

    public Face2FaceInviteDialog(Context context, String content, String imgUrl) {
        super(context, R.style.SercurityDialogTheme);
        this.context = context;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_invite, null);
        setContentView(view);

        TextView tvInvitePhone = view.findViewById(R.id.tv_invite_phone);
        ImageView ivQrCode = view.findViewById(R.id.iv_qr_code);
        tvInvitePhone.setText(content);
        Glide.with(context).load(imgUrl).into(ivQrCode);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        dialogWindow.setAttributes(lp);
    }

}
