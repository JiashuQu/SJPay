package com.sujin.sjpay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sujin.sjpay.util.CommonUtil;
import com.sujin.sjpay.util.ToastUtil;

public class PasswordEditText extends EditText {
	private String TAG = "PasswordEditText";

	public PasswordEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PasswordEditText(Context context) {
		super(context);
	}
     
	@Override
	protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
		
		String editableText = getText().toString();
		if (!"".equals(editableText)) {
		 	String stringFilter = CommonUtil.StringFilter(editableText);
			if (!stringFilter.equals(editableText)) {
				 //不满足要求输入
				ToastUtil.show("密码必须为6~18位字母，数字，部分符号");
				this.setText(stringFilter);
				this.setSelection(stringFilter.length()); // 光标置后
			}
		}
	}
}
