package com.sujin.sjpay.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sujin.sjpay.R;
import com.sujin.sjpay.protocol.ElementProvinceCityResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by czb on 2016/12/7.
 */

public class SelectAddressAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ElementProvinceCityResponse> datas;
    private String type;
    private Drawable arrow;

    public SelectAddressAdapter(Context context, ArrayList<ElementProvinceCityResponse> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_address, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ElementProvinceCityResponse data = datas.get(position);
        holder.tvItemSelect.setText(data.getName());
        if (TextUtils.equals(type, "0")) {
            holder.tvItemSelect.setCompoundDrawables(null, null, arrow, null);
        } else {
            holder.tvItemSelect.setCompoundDrawables(null, null, null, null);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_item_select)
        TextView tvItemSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setData(ArrayList<ElementProvinceCityResponse> datas, String type) {
        this.datas = datas;
        this.type = type;
        notifyDataSetChanged();
    }
}
