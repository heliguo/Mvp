package com.lgh.mvp.utils;

import android.widget.Toast;

import com.lgh.mvp.base.BaseApplication;

public class ToastUtils {

    private static Toast mToast;

    /**
     * 默认 short
     *
     * @param toast
     */
    public static void showToast(String toast) {
        showToast(toast, false);
    }

    /**
     * @param toast    内容
     * @param duration 时间长短
     */
    public static void showToast(String toast, boolean duration) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getAppContext(), toast, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(toast);
        }
        if (duration) {
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

}
