package com.sujin.sjpay.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by zhaofan on 2018/6/12.
 */

public class ScrollzfView extends ScrollView {

    private ScrollViewListener scrollViewListener;
    private int lastY = 0;
    private int touchEventId = -9983761;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == touchEventId) {
                if (lastY == getScrollY()) {

                    if (scrollViewListener != null ) {
                        scrollViewListener.onScrollChanged(getScrollY());
                    }
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId), 5);
                    lastY = getScrollY();
                }
            }
        }
    };

    public ScrollzfView(Context context) {
        super(context);
    }

    public ScrollzfView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollzfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            handler.sendMessageDelayed(handler.obtainMessage(touchEventId), 5);
        }
        return super.onTouchEvent(ev);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    public interface ScrollViewListener {
        void onScrollChanged(float y);
    }

}
