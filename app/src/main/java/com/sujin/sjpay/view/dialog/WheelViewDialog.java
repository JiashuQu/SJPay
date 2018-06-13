package com.sujin.sjpay.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.lidroid.xutils.db.table.KeyValue;
import com.sujin.sjpay.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhaofan on 2018/6/13.
 */

public class WheelViewDialog extends BaseDialog {

    private  List<? extends KeyValue> list;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.wv)
    WheelView wheelView;

    public WheelViewDialog(@NonNull Context context, List<? extends KeyValue> list) {

        super(context);
        this.list = list;
        initView();
    }

    private void initView() {

        wheelView.setCyclic(false);

        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return list.size();
            }

            @Override
            public Object getItem(int index) {
                return list.get(index).value;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        });

    }

    @Override
    protected int contentViewID() {
        return R.layout.dialog_wheel_dialog;
    }

    @OnClick({R.id.tv_cancle, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                dismiss();
                break;
            case R.id.tv_ok:
                dismiss();
                if(listener!=null){
                    listener.onClickType(0,list.get(wheelView.getCurrentItem()));
                }

                break;
        }
    }
}
