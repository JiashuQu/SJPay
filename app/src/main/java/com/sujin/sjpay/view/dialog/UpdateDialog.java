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

import com.sujin.sjpay.R;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.util.BitmapUtil;

/**
 * Created by QJS on 2016/8/1.
 */
public class UpdateDialog extends Dialog {

    private Context context;
    private String content;
    private ClickListenerInterface clickListenerInterface;

    public UpdateDialog(Context context, String content) {
        super(context, R.style.SercurityDialogTheme);
        this.context = context;
        this.content = content;
    }

    public interface ClickListenerInterface {
        void doConfirm();
        void doCancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update, null);
        setContentView(view);

        TextView tvUpdateContent = (TextView) view.findViewById(R.id.tv_update_content);
        TextView tvUpdateNow = (TextView) view.findViewById(R.id.tv_update_now);
        ImageView ivUpdateClose = (ImageView) view.findViewById(R.id.iv_update_close);
//        BitmapUtil.showImageCustom(context, ApiHostUtil.getAppIconUrl(AppConstants.PIC_UPDATE), R.drawable.update_bg, (ImageView) findViewById(R.id.img_update_bg));
        tvUpdateContent.setText(content);

        ivUpdateClose.setOnClickListener(new clickListener());
        tvUpdateNow.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.tv_update_now:
                    clickListenerInterface.doConfirm();
                    break;
                case R.id.iv_update_close:
                    clickListenerInterface.doCancel();
                    break;
            }
        }
    }
}
