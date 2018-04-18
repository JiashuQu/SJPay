package com.sujin.sjpay.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sujin.sjpay.R;
import com.sujin.sjpay.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 银行卡cell
 * Created by czb on 2016/8/1.
 */
public class BankCellView extends RelativeLayout {
    @BindView(R.id.img_my_card_icon)
    ImageView imgViewBankIcon;
    @BindView(R.id.tv_my_card_name)
    TextView tvViewBankName;
    @BindView(R.id.tv_my_card_tip)
    TextView tvViewBankTip;
    @BindView(R.id.img_my_card_action)
    ImageView imgViewBankAction;
    @BindView(R.id.tv_view_bank_select)
    TextView tvViewBankSelect;
    @BindView(R.id.ll_my_card)
    LinearLayout llViewBank;
    @BindView(R.id.tv_my_card_action)
    TextView tvMyCardAction;

    private String bankName, bankNo, bankCode;
    private int iconSize;

    public BankCellView(Context context) {
        this(context, null);
    }

    public BankCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_bank_cell, this);
        ButterKnife.bind(this);
        iconSize = context.getResources().getDimensionPixelSize(R.dimen.bank_icon_size);
    }

    /**
     * 设置数据
     *
     * @param bankName 银行名称
     * @param tip      限额
     * @param bankNo   银行卡号（需要显示后四位时传参，不需要时传""或null）
     * @param resId    右侧的图片（需要显示时传参，不需要时-1）
     */
    public void setData(String bankName, String bankNo, String bankCode, String tip, @DrawableRes int resId) {
        this.bankName = bankName;
        this.bankNo = bankNo;
        this.bankCode = bankCode;
        // icon
        ViewGroup.LayoutParams layoutParams = imgViewBankIcon.getLayoutParams();
        layoutParams.height = iconSize;
        layoutParams.width = iconSize;
//        BitmapUtil.showImage(getContext(), String.format(ApiHostUtil.BANK_ICON_HOST, bankCode), imgViewBankIcon);
        // 限额描述
        tvViewBankTip.setVisibility(GONE);
        if (!TextUtils.isEmpty(tip)) {
            tvViewBankTip.setText(tip);
        } else {
            tvViewBankTip.setText("");
        }
        // 选择银行卡描述
        tvViewBankSelect.setVisibility(GONE);

        llViewBank.setVisibility(VISIBLE);
        imgViewBankIcon.setVisibility(GONE);
        // 银行名称
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvViewBankName.getLayoutParams();
        params.gravity = Gravity.TOP;
        tvViewBankName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.textSize_32px));
        if (TextUtils.isEmpty(bankNo)) {
            tvViewBankName.setText(bankName);
        } else {
            tvViewBankName.setText(bankName + String.format(getResources().getString(R.string.end_number), StringUtil.end4(bankNo)));
        }
        // 右侧图片
        if (resId != -1) {
            imgViewBankAction.setVisibility(VISIBLE);
            imgViewBankAction.setImageResource(resId);
        } else {
            imgViewBankAction.setVisibility(GONE);
        }
    }

    /**
     * 没有限额描述 隐藏右侧图片
     *
     * @param bankName
     * @param bankNo
     * @param bankCode
     */
    public void setData(String bankName, String bankNo, String bankCode) {
        setData(bankName, bankNo, bankCode, "", -1);
    }

    /**
     * 右边图片为
     *
     * @param bankName
     * @param bankCode
     * @param tip
     * @param tipColor
     */
    public void setDataWithColor(String bankName, String bankCode, String tip, @ColorRes int tipColor) {
        this.bankName = bankName;
        this.bankCode = bankCode;

        ViewGroup.LayoutParams layoutParams = imgViewBankIcon.getLayoutParams();
        layoutParams.height = iconSize;
        layoutParams.width = iconSize;
//        BitmapUtil.showImage(getContext(), String.format(ApiHostUtil.BANK_ICON_HOST, bankCode), imgViewBankIcon);

        tvViewBankTip.setVisibility(VISIBLE);
        tvViewBankTip.setTextColor(getContext().getResources().getColor(tipColor));
        if (!TextUtils.isEmpty(tip)) {
            tvViewBankTip.setText(tip);
        } else {
            tvViewBankTip.setText("");
        }
        tvViewBankName.setText(bankName);
        tvViewBankSelect.setVisibility(GONE);
        llViewBank.setVisibility(VISIBLE);
        imgViewBankIcon.setVisibility(VISIBLE);
        tvViewBankName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.textSize_32px));
    }

    /**
     * 添加银行卡样式
     */
    public void setNoCard() {
        tvViewBankTip.setVisibility(View.GONE);
        tvViewBankName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.textSize_34px));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvViewBankName.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL;
        tvViewBankName.setText(getContext().getResources().getString(R.string.add_card));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_VERTICAL);
        imgViewBankIcon.setLayoutParams(layoutParams);
        imgViewBankIcon.setImageResource(R.drawable.icon_add);
    }

    /**
     * 设置为选择银行样式
     */
    public void setSelectBank() {
        tvViewBankSelect.setVisibility(VISIBLE);
        llViewBank.setVisibility(GONE);
        imgViewBankIcon.setVisibility(GONE);
    }

    /**
     * 设置银行限额描述
     *
     * @param tip
     */
    public void setBankTip(String tip, @ColorRes int textColor) {
        tvViewBankTip.setText(tip);
        tvViewBankTip.setTextColor(getContext().getResources().getColor(textColor));
    }

    /**
     * 存管银行卡
     * @param bankIcon
     * @param bankNo
     * @param bankName
     */
    public void setBankData(@DrawableRes int bankIcon, String bankNo, String bankName){
        // icon
        ViewGroup.LayoutParams layoutParams = imgViewBankIcon.getLayoutParams();
        layoutParams.height = iconSize;
        layoutParams.width = iconSize;
        imgViewBankIcon.setVisibility(VISIBLE);
        imgViewBankIcon.setImageResource(bankIcon);
        // 银行名
        tvViewBankTip.setVisibility(VISIBLE);
        tvViewBankTip.setText(bankName);

        // 选择银行卡描述
        tvViewBankSelect.setVisibility(GONE);
        llViewBank.setVisibility(VISIBLE);
        // 银行卡号
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvViewBankName.getLayoutParams();
        params.gravity = Gravity.TOP;
        tvViewBankName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.textSize_32px));
        if (!TextUtils.isEmpty(bankNo)){
            String noFront = "";
            if (bankNo.length() > 5){
                noFront = bankNo.substring(0, 5);
            }
            String noRear = "";
            if (bankNo.length() > 13){
                noRear = bankNo.substring(13);
            }
            String noMiddle = "********";
            tvViewBankName.setText(StringUtil.formatBankNumberFirst5(noFront + noMiddle + noRear));
        }

        // 右侧图片
        imgViewBankAction.setVisibility(GONE);

        tvMyCardAction.setVisibility(VISIBLE);
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public TextView getTvAction(){
        return tvMyCardAction;
    }
}
