package com.sujin.sjpay.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sujin.sjpay.R;

public class GetTopVipDialog extends Dialog {

    private GetTopVipDialog.LiftBankCardListener liftBankCardListener;

    private TextView tvConfirm;
    private LinearLayout llContact_us;
    private LinearLayout llMyAccountTip;
    private TextView tvMyAccountTip;
    private TextView tvMyAccountTitle;
    private Context context;
    private String showWhat;

    public GetTopVipDialog(Context context, String showWhat, String title) {
        super(context, R.style.SercurityDialogTheme);
        this.context = context;
        this.showWhat = showWhat;
    }

    public void onClick(View v) {

    }

    public interface LiftBankCardListener {
        void cancel();
    }

    public void setLiftBankCardListener(GetTopVipDialog.LiftBankCardListener liftBankCardListener) {
        this.liftBankCardListener = liftBankCardListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //给dialog设置布局
        setContentView(R.layout.dialog_get_top_vip);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        dialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        tvConfirm = findViewById(R.id.tv_confirm);
        llContact_us = findViewById(R.id.ll_contact_us);
        llMyAccountTip = findViewById(R.id.ll_my_account_tip);
        tvMyAccountTip = findViewById(R.id.tv_my_account_tip);
        tvMyAccountTitle = findViewById(R.id.tv_my_account_title);

        if (TextUtils.isEmpty(showWhat)) {
            llContact_us.setVisibility(View.VISIBLE);
            llMyAccountTip.setVisibility(View.GONE);
        }else {
            llContact_us.setVisibility(View.GONE);
            llMyAccountTip.setVisibility(View.VISIBLE);
            tvMyAccountTip.setText(showWhat);
        }

        tvConfirm.setOnClickListener(new GetTopVipDialog.clickListener());
    }

    private class clickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.tv_confirm:
                    liftBankCardListener.cancel();
                    break;
            }
        }
    }
}
