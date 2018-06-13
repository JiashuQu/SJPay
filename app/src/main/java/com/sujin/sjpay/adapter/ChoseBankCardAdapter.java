package com.sujin.sjpay.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sujin.sjpay.R;
import com.sujin.sjpay.protocol.GetHistoryPayBankCardListResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QJS on 2018/3/9.
 */

public class ChoseBankCardAdapter extends BaseAdapter {
    private Context context;
    private List<GetHistoryPayBankCardListResponse.DataBean.ListBean> datas;
    private Drawable arrow;

    public ChoseBankCardAdapter(Context context, List<GetHistoryPayBankCardListResponse.DataBean.ListBean> datas) {
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
        SelectBankAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank_cell, null, false);
            holder = new SelectBankAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SelectBankAdapter.ViewHolder) convertView.getTag();
        }
        GetHistoryPayBankCardListResponse.DataBean.ListBean data = datas.get(position);
        holder.tvItemBankCell.setText(data.getBankName());
        holder.tvItemBankLimit.setText(data.getBankCard());
//        Glide.with(context).asBitmap().load(data.getBankCard()).into(holder.ivBankIcon);

        return convertView;
    }

    public void setData(List<GetHistoryPayBankCardListResponse.DataBean.ListBean> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.iv_bank_icon)
        ImageView ivBankIcon;
        @BindView(R.id.tv_item_bank_cell)
        TextView tvItemBankCell;
        @BindView(R.id.tv_item_bank_limit)
        TextView tvItemBankLimit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
