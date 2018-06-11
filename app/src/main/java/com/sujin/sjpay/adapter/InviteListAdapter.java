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
    private List<InviteListResponse.DataBean.ListBean> data;
    private Drawable arrow;

    public InviteListAdapter(Context context, List<InviteListResponse.DataBean.ListBean> data) {
        this.context = context;
        this.data = data;
        arrow = ContextCompat.getDrawable(context, R.drawable.next_arrow_tip);
        arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_invite, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InviteListResponse.DataBean.ListBean inviteList = data.get(position);
        holder.tvInviteName.setText(inviteList.getRealName() + "  " + inviteList.getMobile());
        holder.tvInviteData.setText(inviteList.getCTime());
        int isRealState = inviteList.getIsRealState();
        String isRealStateTxt = inviteList.getIsRealStateTxt();
        holder.tvInviteStatus.setText(isRealStateTxt);
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
        this.data = data;
        notifyDataSetChanged();
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
