package com.sujin.sjpay.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.sujin.sjpay.R;


/**
 * Created by QJS on 2016/9/20.
 */

public class AuthenticateDialog extends Dialog implements View.OnClickListener {

    private LiftBankCardListener liftBankCardListener;

    private Button btnPickPhoto;
    private Button btnTakePhoto;
    private Button btnCancel;

    public AuthenticateDialog(Context context) {
        super(context, R.style.SercurityDialogTheme);
    }

    public void onClick(View v) {

    }

    public interface LiftBankCardListener {
        void pickPhoto();
        void cancel();
        void takePhoto();
    }

    public void setLiftBankCardListener(LiftBankCardListener liftBankCardListener) {
        this.liftBankCardListener = liftBankCardListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //给dialog设置布局
        setContentView(R.layout.dialog_chose_photo);
        //通过window设置获取dialog参数
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        //获取屏幕的宽高
        WindowManager manager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        //设置dialog的宽
        params.width = width;
        //设置dialog在屏幕中的位置
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        //设置dialog属性
        window.setAttributes(params);
        initView();
    }

    private void initView() {
        btnPickPhoto = (Button)findViewById(R.id.btn_pick_photo);
        btnTakePhoto = (Button)findViewById(R.id.btn_take_photo);
        btnCancel = (Button)findViewById(R.id.btn_cancel);

        btnPickPhoto.setOnClickListener(new clickListener());
        btnTakePhoto.setOnClickListener(new clickListener());
        btnCancel.setOnClickListener(new clickListener());
    }

    private class clickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_pick_photo:
                    liftBankCardListener.pickPhoto();
                    break;
                case R.id.btn_take_photo:
                    liftBankCardListener.takePhoto();
                    break;
                case R.id.btn_cancel:
                    liftBankCardListener.cancel();
                    break;
            }
        }
    }
}
