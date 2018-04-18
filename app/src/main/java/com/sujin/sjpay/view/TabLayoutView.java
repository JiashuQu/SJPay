package com.sujin.sjpay.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.sujin.sjpay.R;
import com.sujin.sjpay.entity.TabData;
import com.sujin.sjpay.util.UIUtil;

import java.util.ArrayList;

/**
 * 应用底部容器
 * Created by czb on 2016/7/26.
 */
public class TabLayoutView extends LinearLayout implements TabView.OnTabClickListener {

    private OnTabLayoutClickListener listener;

    private ArrayList<TabView> tabViews;
    private ArrayList<TabData> tabDatas;

    public TabLayoutView(Context context) {
        this(context, null);
    }

    public TabLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(context.getResources().getColor(R.color.gray_F9F9F9));
        tabViews = new ArrayList<>();
    }

    public void initTabView(ArrayList<TabData> tabDatas) {
        if (tabDatas != null && tabDatas.size() > 0) {
            removeAllViews();
            tabViews.clear();
            this.tabDatas = tabDatas;
            for (int i = 0; i < tabDatas.size(); i++) {
                TabView tabView = new TabView(getContext());
                tabView.setTabData(tabDatas.get(i));
                tabView.setOnTabClickListener(this);
                LayoutParams params = new LayoutParams(UIUtil.getScreenWidth() / tabDatas.size(), LayoutParams.MATCH_PARENT);
                this.addView(tabView, params);
                tabViews.add(tabView);
            }
            View view = getChildAt(0);
            if (view != null && view instanceof TabView) {
                TabView tabView = (TabView) view;
                tabView.selected();
            }
        }
    }

    @Override
    public boolean onTabClick(TabData tabData) {
        boolean b = true;
        if (listener != null) {
            b = listener.onTabLayoutClick(tabData);
        }
        if (b) {
            resetAllTab();
        }
        return b;
    }

    private void resetAllTab() {
        for (int i = 0; i < tabViews.size(); i++) {
            TabView view = tabViews.get(i);
            view.unSelected();
        }
    }

    public void selectTab(int i) {
        resetAllTab();
        View view = getChildAt(i);
        if (view != null && view instanceof TabView) {
            TabView tabView = (TabView) view;
            tabView.selected();
        }
    }

    /**
     * 通过Fragment Tag找到相应的TabView
     *
     * @param tag
     * @return
     */
    public TabView getTabViewByTag(String tag) {
        for (int i = 0; i < tabViews.size(); i++) {
            if (TextUtils.equals(tag, tabViews.get(i).getTabData().getTag())) {
                return tabViews.get(i);
            }
        }
        return null;
    }

    public ArrayList<TabView> getTabViews(){
        return tabViews;
    }

    public interface OnTabLayoutClickListener {
        /**
         * 点击TabLayoutView
         *
         * @param tabData
         * @return 是否跳转
         */
        boolean onTabLayoutClick(TabData tabData);
    }

    public void setOnTabLayoutClickListener(OnTabLayoutClickListener listener) {
        this.listener = listener;
    }
}
