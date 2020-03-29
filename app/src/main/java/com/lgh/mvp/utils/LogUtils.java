package com.lgh.mvp.utils;

import android.util.Log;

/**
 * 日志工具类
 * currentLev = 4 打印全部日志
 * currentLev < 1 关闭打印日志
 */
public class LogUtils {

    private static int currentLev = 4;
    private static final int DEBUG_LEV = 4;
    private static final int INFO_LEV = 3;
    private static final int WARNING_LEV = 2;
    private static final int ERROR_LEV = 1;

    public static void d(Object object, String log) {
        if (currentLev >= DEBUG_LEV) {
            Log.d(object.getClass().getSimpleName(), "d: " + log);
        }
    }

    public static void i(Object object, String log) {
        if (currentLev >= INFO_LEV) {
            Log.i(object.getClass().getSimpleName(), "i: " + log);
        }
    }

    public static void w(Object object, String log) {
        if (currentLev >= WARNING_LEV) {
            Log.w(object.getClass().getSimpleName(), "w: " + log);
        }
    }

    public static void e(Object object, String log) {
        if (currentLev >= ERROR_LEV) {
            Log.e(object.getClass().getSimpleName(), "e: " + log);
        }
    }
}
