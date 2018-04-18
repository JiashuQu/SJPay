package com.sujin.sjpay.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.entity.TabData;
import com.sujin.sjpay.util.BitmapUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by czb on 2016/7/26.
 */
public class TabView extends RelativeLayout {
    @BindView(R.id.img_tab_icon)
    ImageView imgTabIcon;
    @BindView(R.id.tv_tab_name)
    TextView tvTabName;
    @BindView(R.id.img_tab_point)
    ImageView imgTabPoint;

    private Context context;

    private TabData tabData;

    private OnTabClickListener listener;

    private int colerOrange, colorGray;

    private boolean isSelected;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_tab, this);
        ButterKnife.bind(this);
        this.context = context;
        this.setOnClickListener(onClickListener);
        colerOrange = context.getResources().getColor(R.color.orange);
        colorGray = context.getResources().getColor(R.color.gray_A3A3A3);
    }

    public void setTabData(TabData tabData) {
        this.tabData = tabData;
        imgTabIcon.setImageResource(tabData.getResourceId());
        if (!TextUtils.isEmpty(tabData.getIconUrl())) {
            BitmapUtil.showImage(context, tabData.getIconUrl(), imgTabIcon);
        }
        tvTabName.setText(tabData.getName());
    }

    public TabData getTabData() {
        return tabData;
    }

    public void selected() {
        if (!TextUtils.isEmpty(tabData.getIconUrl()) && !TextUtils.isEmpty(tabData.getIconUrlSelect())) {
            BitmapUtil.showImageCustom(context, tabData.getIconUrlSelect(), tabData.getResourceIdSelect(), imgTabIcon);
        } else {
            imgTabIcon.setImageResource(tabData.getResourceIdSelect());
        }
        tvTabName.isSelected();
        tvTabName.setTextColor(colerOrange);
        isSelected = true;
    }

    public void unSelected() {
        if (!TextUtils.isEmpty(tabData.getIconUrl()) && !TextUtils.isEmpty(tabData.getIconUrlSelect())) {
            BitmapUtil.showImageCustom(context, tabData.getIconUrl(),tabData.getResourceId(), imgTabIcon);
        } else {
            imgTabIcon.setImageResource(tabData.getResourceId());
        }
        tvTabName.setSelected(false);
        tvTabName.setTextColor(colorGray);
        isSelected = false;
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean b = true;
            if (listener != null) {
                b = listener.onTabClick(tabData);
            }
            if (b){
                selected();
            }
        }
    };


    /**
     * 替换icon图片
     * @param url 未选中状态
     * @param urlSelect 选中状态
     */
    public void setImage(String url, String urlSelect){
        tabData.setIconUrl(url);
        tabData.setIconUrlSelect(urlSelect);
        if (isSelected){
            BitmapUtil.showImageCustom(context, urlSelect, tabData.getResourceIdSelect(), imgTabIcon);
        } else {
            BitmapUtil.showImageCustom(context, url, tabData.getResourceId(), imgTabIcon);
        }
    }

    /**
     * 显示小圆点
     */
    public void showPoint(){
        imgTabPoint.setVisibility(VISIBLE);
    }

    /**
     * 隐藏小圆点
     */
    public void hidePoint(){
        imgTabPoint.setVisibility(GONE);
    }

    public interface OnTabClickListener {
        /**
         * 点击TabView调用
         * @param tabData
         * @return 是否跳转
         */
        boolean onTabClick(TabData tabData);
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.listener = listener;
    }

}
