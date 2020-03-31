package com.lgh.mvp.utils;

import android.content.Context;
import android.util.TypedValue;

public class SizeUtils {

    public static int dp2px(Context context,int px){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,px,
                context.getResources().getDisplayMetrics());
    }
}
