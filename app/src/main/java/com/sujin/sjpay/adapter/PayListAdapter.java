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
import com.sujin.sjpay.protocol.PayListResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QJS on 2018/3/15.
 */

public class PayListAdapter extends BaseAdapter {
    private Context context;
    private List<PayListResponse.DataBean.ListBean> datas;
    private Drawable arrow;

    public PayListAdapter(Context context, List<PayListResponse.DataBean.ListBean> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pay_list, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PayListResponse.DataBean.ListBean data = datas.get(position);
        holder.tvCreateTime.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.tvCreateTime.setSingleLine(true);
        holder.tvCreateTime.setSelected(true);
        holder.tvCreateTime.setFocusable(true);
        holder.tvCreateTime.setFocusableInTouchMode(true);
        holder.tvOrderNumber.setText("订单号：" + data.getOrderNo());
        holder.tvCreateTime.setText(data.getCTime());
        holder.tvPayMoney.setText(data.getAmount());
        holder.tvPayState.setText(data.getPayStateTxt());
        holder.tvGetMoney.setText(data.getActualAmount());

        int payState = data.getPayState();

        if ( payState == 10) {
            holder.tvPayState.setTextColor(0xFF1Dc694);
        }else if ( payState == 5) {
            holder.tvPayState.setTextColor(0xFFF89B00);
        }else {
            holder.tvPayState.setTextColor(0xFFEf001E);
        }

        return convertView;
    }

    public void setData(List<PayListResponse.DataBean.ListBean> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_order_number)
        TextView tvOrderNumber;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_pay_money)
        TextView tvPayMoney;
        @BindView(R.id.tv_pay_state)
        TextView tvPayState;
        @BindView(R.id.tv_get_money)
        TextView tvGetMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
