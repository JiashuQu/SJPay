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
import com.sujin.sjpay.protocol.AccountRecordResponse;
import com.sujin.sjpay.protocol.IncomeListResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteIncomeAdapter extends BaseAdapter {

    private Context context;
    private List<IncomeListResponse.DataBean.ListBean> datas;
    private Drawable arrow;

    public InviteIncomeAdapter(Context context, List<IncomeListResponse.DataBean.ListBean> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_invite_money, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        IncomeListResponse.DataBean.ListBean data = datas.get(position);
        holder.tvGetMoney.setText(data.getInOrOut() + data.getAmount());
        holder.tvByWho.setText(data.getRealName());
        holder.tvGetMoneyType.setText(data.getService());
        int inOrOutNum = data.getInOrOutNum();
        if (1 == inOrOutNum) {
            holder.tvGetMoney.setTextColor(context.getResources().getColor(R.color.green_70C050));
        }else if (-1 == inOrOutNum) {
            holder.tvGetMoney.setTextColor(context.getResources().getColor(R.color.red_FF0036));
        }
        return convertView;
    }

    public void setData(List<IncomeListResponse.DataBean.ListBean> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_get_money)
        TextView tvGetMoney;
        @BindView(R.id.tv_by_who)
        TextView tvByWho;
        @BindView(R.id.tv_get_money_type)
        TextView tvGetMoneyType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
