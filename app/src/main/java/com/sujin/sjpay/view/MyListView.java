package com.sujin.sjpay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {

    private float oldY,newY;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //测量的大小由一个32位的数字表示，前两位表示测量模式，后30位表示大小，这里需要右移两位才能拿到测量的大小
//        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, heightSpec);
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0) {//到头部了
            getParent().requestDisallowInterceptTouchEvent(false);//放行
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);//拦截
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                newY = ev.getY();
                if(newY>oldY && getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0){
                    return true;
                }

                oldY = newY;
                break;
        }


        return super.onTouchEvent(ev);
    }
}