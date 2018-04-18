/**
 * Copyright © YOLANDA. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sujin.sjpay.util;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sujin.sjpay.android.SJApplication;

/**
 * Created in Jan 31, 2016 4:15:36 PM.
 *
 * @author YOLANDA;
 */
public class ToastUtil {

    private static CharSequence oldMsg = StringUtil.EMPTY;
    private static int oldStringId = 0;
    public static Toast toast;
    private static long oneTime = 0;
    private static long twoTime = 0;


    public static void show(CharSequence msg) {
        if (toast == null) {
            toast = Toast.makeText(SJApplication.getInstance(), msg, Toast.LENGTH_LONG);
            try {
                toast.show();
            } catch (Exception e) {

            }
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    try {
                        toast.show();
                    } catch (Exception e) {

                    }
                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                try {
                    toast.show();
                } catch (Exception e) {

                }
            }
        }
        oneTime = twoTime;
    }

    public static void show(@StringRes int stringId) {
        int oldStringId = 0;
        if (toast == null) {
            toast = Toast.makeText(SJApplication.getInstance(), stringId, Toast.LENGTH_LONG);
            try {
                toast.show();
            } catch (Exception e) {

            }
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (stringId == oldStringId) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    try {
                        toast.show();
                    } catch (Exception e) {

                    }
                }
            } else {
                oldStringId = stringId;
                toast.setText(stringId);
                try {
                    toast.show();
                } catch (Exception e) {

                }
            }
        }
        oneTime = twoTime;
    }

    public static void showShort(CharSequence msg) {
        Toast.makeText(SJApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(@StringRes int stringId) {
        Toast.makeText(SJApplication.getInstance(), stringId, Toast.LENGTH_SHORT).show();
    }

    public static void showShortCenter(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(SJApplication.getInstance(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            try {
                toast.show();
            } catch (Exception e) {

            }
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    try {
                        toast.show();
                    } catch (Exception e) {

                    }
                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                try {
                    toast.show();
                } catch (Exception e) {

                }
            }
        }
        oneTime = twoTime;
    }

    public static void showShortCenter(@StringRes int stringId) {
        showShortCenter(SJApplication.getInstance().getString(stringId));
    }

    private void showText(Toast toast) {
        if (this.toast == null) {
            this.toast = toast;
        } else {
            View view = toast.getView();
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                this.toast.setText(textView.getText());
            }
        }
        try {
            this.toast.show();
        } catch (Exception e) {

        }
    }

}
