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
import com.sujin.sjpay.protocol.PayCardListResponse;
import com.sujin.sjpay.util.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QJS on 2018/3/16.
 */

public class SelectPayBankListAdapter extends BaseAdapter {

    private Context context;
    private List<PayCardListResponse.DataBean> datas;
    private Drawable arrow;

    public SelectPayBankListAdapter(Context context, ArrayList<PayCardListResponse.DataBean> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank_belong, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PayCardListResponse.DataBean dataBean = datas.get(position);
        String bankName = dataBean.getBankName();
        holder.tvItemBankCell.setText(bankName);
        Glide.with(context).asBitmap().load(dataBean.getICON()).into(holder.ivBankIcon);

        return convertView;
    }

    public void setData(List<PayCardListResponse.DataBean> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.iv_bank_icon)
        ImageView ivBankIcon;
        @BindView(R.id.tv_item_bank_cell)
        TextView tvItemBankCell;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
