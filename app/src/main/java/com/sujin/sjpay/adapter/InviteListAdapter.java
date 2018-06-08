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
import com.sujin.sjpay.protocol.InviteListResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteListAdapter extends BaseAdapter {
    private Context context;
    private List<InviteListResponse.DataBean.ListBean> datas;
    private Drawable arrow;

    public InviteListAdapter(Context context, List<InviteListResponse.DataBean.ListBean> datas) {
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
        InviteListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_invite, null, false);
            holder = new InviteListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (InviteListAdapter.ViewHolder) convertView.getTag();
        }
        InviteListResponse.DataBean.ListBean data = datas.get(position);
        holder.tvInviteName.setText(data.getRealName() + "  " + data.getMobile());
        holder.tvInviteData.setText(data.getCTime());
        int isRealState = data.getIsRealState();
        String isRealStateTxt = data.getIsRealStateTxt();
        if (isRealState == 0) {//未实名
            holder.tvInviteStatus.setTextColor(0xFF00BA76);
            holder.tvInviteStatus.setText(isRealStateTxt);
        }else {
            holder.tvInviteStatus.setTextColor(0xFF999999);
            holder.tvInviteStatus.setText(isRealStateTxt);
        }
        return convertView;
    }

    public void setData(List<InviteListResponse.DataBean.ListBean> data) {
        this.datas = data;
//        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_invite_name)
        TextView tvInviteName;
        @BindView(R.id.tv_invite_data)
        TextView tvInviteData;
        @BindView(R.id.tv_invite_status)
        TextView tvInviteStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
