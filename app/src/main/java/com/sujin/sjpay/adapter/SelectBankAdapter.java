package com.sujin.sjpay.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.protocol.GetPayBankQuotaList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QJS on 2018/3/5.
 */

public class SelectBankAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GetPayBankQuotaList> datas;
    private Drawable arrow;

    public SelectBankAdapter(Context context, ArrayList<GetPayBankQuotaList> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank_cell, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        GetPayBankQuotaList data = datas.get(position);
        holder.tvItemBankCell.setText(data.getBankName());
//        Resources resources = context.getResources();
//        holder.tvItemBankLimit.setText(resources.getString(R.string.single_quota) + data.getSingleQuota() + "  " + resources.getString(R.string.day_quota) + data.getDayQuota() + "  " + resources.getString(R.string.mouth_quota) + data.getMouthQuota());

        return convertView;
    }

    public void setData(ArrayList<GetPayBankQuotaList> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_item_bank_cell)
        TextView tvItemBankCell;
        @BindView(R.id.tv_item_bank_limit)
        TextView tvItemBankLimit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
