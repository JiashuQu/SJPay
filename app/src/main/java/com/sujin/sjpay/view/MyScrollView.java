package com.sujin.sjpay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zhaofan on 2018/6/14.
 */

public class MyScrollView extends ScrollView{
    private float oldY;
    private float newY;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                newY = ev.getY();
                if(newY<oldY &&  getChildAt(0).getMeasuredHeight() <= getScrollY() + getHeight()){
                    return true;
                }

                oldY = newY;
                break;
        }


        return super.onTouchEvent(ev);
    }
}
