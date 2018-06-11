package com.sujin.sjpay.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.protocol.AccountRecordResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAccountAdapter extends BaseAdapter {

    private Context context;
    private List<AccountRecordResponse.DataBean.ListBean> datas;
    private Drawable arrow;

    public MyAccountAdapter(Context context, List<AccountRecordResponse.DataBean.ListBean> datas) {
        this.context = context;
        this.datas = datas;
        arrow = ContextCompat.getDrawable(context, R.drawable.next_arrow_tip);
        arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_account, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AccountRecordResponse.DataBean.ListBean data = datas.get(position);
        holder.tvMoneyType.setText(data.getService());
        holder.tvMoneyStatus.setText(data.getInOrOut() + data.getAmount());
        holder.tvMoneyAvailable.setText(data.getAvailable());
        holder.tvFrozenMoney.setText(data.getFrozen());
        holder.tvMoneyTime.setText(data.getCTime());

        return convertView;
    }

    public void setData(List<AccountRecordResponse.DataBean.ListBean> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_money_type)
        TextView tvMoneyType;
        @BindView(R.id.tv_money_status)
        TextView tvMoneyStatus;
        @BindView(R.id.tv_money_available)
        TextView tvMoneyAvailable;
        @BindView(R.id.tv_frozen_money)
        TextView tvFrozenMoney;
        @BindView(R.id.tv_money_time)
        TextView tvMoneyTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
