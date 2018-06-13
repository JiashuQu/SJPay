package com.sujin.sjpay.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lidroid.xutils.db.table.KeyValue;
import com.sujin.sjpay.R;

import butterknife.ButterKnife;

/**
 * Created by zf on 2017/11/23.
 */

public abstract class BaseDialog extends Dialog {
    protected  Context context;
    protected DialogListener listener;


    public BaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(contentViewID(), null);
        this.setContentView(view);
        ButterKnife.bind(this, view);
        this.getWindow().setWindowAnimations(getAnimalStyle());
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    protected abstract int contentViewID();
    protected int getAnimalStyle(){
        getWindow().setGravity(Gravity.BOTTOM);
        return R.style.popwin_anim_bottom;
    }


    public interface DialogListener{
        void onClickType(int type, KeyValue bean);
    }


    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

}
