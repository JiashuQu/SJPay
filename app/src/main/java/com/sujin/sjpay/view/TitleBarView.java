package com.sujin.sjpay.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.BaseActivity;

/**
 * Created by czb on 2016/7/27.
 */
public class TitleBarView extends RelativeLayout {

    public static final int TITLE_BAR_TYPE_COMMON = 0;
    public static final int TITLE_BAR_TYPE_PAY_LIST = 1;
    public static final int TITLE_BAR_TYPE_HOME = 2;
    public static final int TITLE_BAR_TYPE_INVITE = 3;
    public static final int TITLE_BAR_TYPE_MY = 4;
    public static final int TITLE_BAR_TYPE_TRADE_RECORD = 5;
    public static final int TITLE_BAR_TYPE_MESSAGE_LIST = 6;
    public static final int TITLE_BAR_TYPE_WEB = 7;
    public static final int TITLE_BAR_TYPE_EDIT_LIFE = 8;
    public static final int TITLE_BAR_TYPE_FRESH = 9;
    public static final int TITLE_BAR_TYPE_MY_FRESH = 10;
    public static final int TITLE_TYPE_PAY_LIST = 11;

    /*
    *  普通标题
    */
    ImageView imgTitlebarBack;
    TextView tvTitlebarTitle;

    private View viewLine;
    private int barType;

    private Context context;

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
        barType = a.getInteger(R.styleable.TitleBarView_bar_type, TITLE_BAR_TYPE_COMMON);
        String title = a.getString(R.styleable.TitleBarView_bar_title);
        boolean haveBack = a.getBoolean(R.styleable.TitleBarView_bar_have_back, true);
        a.recycle();

        switch (barType) {
            /*交易记录标题*/
            case TITLE_BAR_TYPE_PAY_LIST:
                inflate(context, R.layout.view_titlebar_pay_list, this);
                break;
            /*交易记录筛选*/
            case TITLE_TYPE_PAY_LIST:
                inflate(context, R.layout.view_bar_pay_list, this);
                break;
//            /*置业标题*/
//            case TITLE_BAR_TYPE_CROWDFUNDING:
//                inflate(context, R.layout.view_titlebar_crowdfunding, this);
//                break;
//
//            /*主页标题*/
//            case TITLE_BAR_TYPE_HOME:
//                inflate(context, R.layout.view_titlebar_home, this);
//                break;
//
//            /*邀请标题*/
//            case TITLE_BAR_TYPE_INVITE:
//                inflate(context, R.layout.view_titlebar_invite, this);
//                break;
//
//            /*WebActivity标题*/
//            case TITLE_BAR_TYPE_WEB:
//                inflate(context, R.layout.view_titlebar_web, this);
//                break;
//
//            /*我的标题*/
//            case TITLE_BAR_TYPE_MY:
//                inflate(context, R.layout.view_titlebar_my, this);
//                break;
//
//            /*消息列表标题*/
//            case TITLE_BAR_TYPE_MESSAGE_LIST:
//                inflate(context, R.layout.view_titlebar_message_list, this);
//                break;
//
//            /*生活标题*/
//            case TITLE_BAR_TYPE_EDIT_LIFE:
//                inflate(context, R.layout.view_titlebar_edit_life, this);
//                break;
//
//            /*我的生鲜*/
//            case TITLE_BAR_TYPE_MY_FRESH:
//                inflate(context, R.layout.view_titlebar_my_fresh, this);
//                break;
//
//            /*生鲜标题*/
//            case TITLE_BAR_TYPE_FRESH:
//                inflate(context, R.layout.view_titlebar_fresh, this);
//                break;

            /*普通标题*/
            case TITLE_BAR_TYPE_COMMON:
            default:
                inflate(context, R.layout.view_titlebar_common, this);
                break;
        }

        setBackgroundColor(context.getResources().getColor(R.color.red_FE2D55));
        viewLine = new View(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        viewLine.setBackgroundColor(context.getResources().getColor(R.color.red_FE2D55));
        this.addView(viewLine, params);

        tvTitlebarTitle = findViewById(R.id.tv_titlebar_title);
        imgTitlebarBack = findViewById(R.id.img_titlebar_back);

        if (imgTitlebarBack != null) {
            if (haveBack) {
                imgTitlebarBack.setVisibility(VISIBLE);
            } else {
                imgTitlebarBack.setVisibility(GONE);
            }
        }
        if (!TextUtils.isEmpty(title)){
            tvTitlebarTitle.setText(title);
        }

        setClickable(true);
        initView(barType);
    }

    private void initView(int barType) {
        switch (barType) {
//            /*交易记录标题*/
//            case TITLE_BAR_TYPE_TRADE_RECORD:
//                break;
//
//            /*置业标题*/
//            case TITLE_BAR_TYPE_CROWDFUNDING:
//                tvTitlebarTitle.setOnClickListener(crowdfundingListener);
//                findViewById(R.id.img_titlebar_center).setOnClickListener(crowdfundingListener);
//                break;
//
//            /*主页标题*/
//            case TITLE_BAR_TYPE_HOME:
//                findViewById(R.id.tv_titlebar_title).setOnClickListener(homeListener);
//                findViewById(R.id.img_titlebar_more).setOnClickListener(homeListener);
//                BitmapUtil.showImageCustom(context, ApiHostUtil.getAppIconUrl(AppConstants.PIC_NAVLOGO), R.drawable.icon_jjy, (ImageView) findViewById(R.id.img_titlebar_logo));
//                break;
//
//            case TITLE_BAR_TYPE_MY_FRESH:
//                findViewById(R.id.tv_titlebar_right).setOnClickListener(freshListener);
//                break;
//
//            /*WebActivity标题*/
//            case TITLE_BAR_TYPE_WEB:
//                BitmapUtil.showImageCustom(context, ApiHostUtil.getAppIconUrl(AppConstants.PIC_SHAREBTN), R.drawable.icon_share, (ImageView) findViewById(R.id.img_titlebar_share));
//                break;
//
//            /*邀请标题*/
//            case TITLE_BAR_TYPE_INVITE:
//                BitmapUtil.showImageCustom(context, ApiHostUtil.getAppIconUrl(AppConstants.PIC_SHAREBTN), R.drawable.icon_share, (ImageView) findViewById(R.id.img_titlebar_share));
//                break;
//
//            /*我的标题*/
//            case TITLE_BAR_TYPE_MY:
//                findViewById(R.id.img_titlebar_alert).setOnClickListener(myListener);
//                BitmapUtil.showImageCustom(context, ApiHostUtil.getAppIconUrl(AppConstants.PIC_MESSAGE), R.drawable.icon_alert, (ImageView) findViewById(R.id.img_titlebar_alert));
//                break;

            /*普通标题*/
            case TITLE_BAR_TYPE_COMMON:
            default:
                break;
        }
        if (imgTitlebarBack != null) {
            imgTitlebarBack.setOnClickListener(commonListener);
        }
    }

    /**
     * 设置title
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitlebarTitle.setText(title);
    }

    /**
     * 设置title
     *
     * @param resId
     */
    public void setTitle(@StringRes int resId) {
        tvTitlebarTitle.setText(resId);
    }

    /**
     * 返回显示title的TextView
     *
     * @return
     */
    public TextView getTitleTV() {
        return tvTitlebarTitle;
    }

    /**
     * 返回左边返回键ImageView
     *
     * @return
     */
    public ImageView getImgTitlebarBack() {
        return imgTitlebarBack;
    }

    /**
     * 隐藏返回键
     */
    public void hideBackButton() {
        imgTitlebarBack.setVisibility(View.GONE);
    }

    /**
     * 隐藏返回键
     */
    public void setBackButtonVisible(int visibility) {
        imgTitlebarBack.setVisibility(visibility);
    }

    /**
     * 设置TitleBarView下面的线是否可见
     *
     * @param visible
     */
    public void setViewLineVisible(int visible) {
        viewLine.setVisibility(visible);
    }

    /**
     * 隐藏线
     */
    public void hideLine() {
        viewLine.setVisibility(View.GONE);
    }

    /**
     * 交易记录标题用
     *
     * @param listener
     */
//    public void setOnClick2Title(OnClickListener listener) {
//        if (listener == null) {
//            return;
//        }
//        tvTitlebarTitle.setOnClickListener(listener);
//        View view = findViewById(R.id.img_titlebar_center);
//        if (view != null) {
//            view.setOnClickListener(listener);
//        }
//    }

    /**
     * 右边TextView点击事件
     *
     * @param listener
     */
    public void setOnClick2RightText(OnClickListener listener) {
        if (listener == null) {
            return;
        }
        View view = findViewById(R.id.tv_titlebar_right);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 设置右边文字
     *
     * @param res
     */
//    public void setRightText(@StringRes int res) {
//        try {
//            String text = getContext().getString(res);
//            setRightText(text);
//        } catch (Resources.NotFoundException e) {
//        }
//    }

    /**
     * 设置右边文字
     *
     * @param text
     */
//    public void setRightText(String text) {
//        View view = findViewById(R.id.tv_titlebar_right);
//        if (view != null) {
//            if (view instanceof TextView) {
//                TextView textView = (TextView) view;
//                textView.setText(text);
//            }
//        }
//    }

    /**
     * 得到右边的TextView
     *
     * @return
     */
//    public TextView getRightText() {
//        View view = findViewById(R.id.tv_titlebar_right);
//        if (view != null) {
//            if (view instanceof TextView) {
//                TextView textView = (TextView) view;
//                return textView;
//            }
//        }
//        return null;
//    }

    /**
     * 得到中间的ImageView
     *
     * @return
     */
//    public ImageView getImageCenter() {
//        View view = findViewById(R.id.img_titlebar_center);
//        ImageView imageView = null;
//        if (view != null) {
//            imageView = (ImageView) view;
//        }
//        return imageView;
//    }

    /**
     * 为右边的图片设置监听器
     * 邀请页，需要要分享的h5页面WebActivity
     */
//    public void setOnClick2RightImage(OnClickListener listener) {
//        View view = findViewById(R.id.img_titlebar_share);
//        if (view != null) {
//            view.setOnClickListener(listener);
//        }
//    }

    /**
     * 左边返回按钮
     */
    public void setOnClick2LeftBack(OnClickListener listener) {
        View view = findViewById(R.id.img_titlebar_back);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 得到右侧的分享ImageView
     *
     * @return
     */
//    public ImageView getImageRight() {
//        View view = findViewById(R.id.img_titlebar_share);
//        ImageView imageView = null;
//        if (view != null) {
//            imageView = (ImageView) view;
//        }
//        return imageView;
//    }

    /**
     * 得到消息未读点ImageView
     * @return
     */
//    public ImageView getPoint(){
//        View view = findViewById(R.id.img_titlebar_point);
//        ImageView imageView = null;
//        if (view != null) {
//            imageView = (ImageView) view;
//        }
//        return imageView;
//    }


    /**
     * 普通标题
     */
    private OnClickListener commonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_titlebar_back:
                    if (getContext() instanceof BaseActivity) {
                        BaseActivity activity = (BaseActivity) getContext();
                        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
                            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        activity.finish();
                    }
                    break;
            }
        }

    };


//    private OnClickListener homeListener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.tv_titlebar_title:
//                    if (SJApplication.getInstance().isLogin()) {
//                        Intent intent = new Intent(context, MainActivity.class);
//                        intent.putExtra(AppConstants.INTENT_KEY_TAB_TAG, AppConstants.TAB_TAG_MY);
//                        context.startActivity(intent);
//                    } else {
//                        context.startActivity(new Intent(context, LoginActivity.class));
//                    }
//                    break;
//
//                case R.id.img_titlebar_more:
//                    context.startActivity(new Intent(context, MoreActivity.class));
//                    break;
//            }
//        }
//    };


//    private OnClickListener myListener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            getContext().startActivity(new Intent(getContext(), MessageCenterActivity.class));
//        }
//    };

}
