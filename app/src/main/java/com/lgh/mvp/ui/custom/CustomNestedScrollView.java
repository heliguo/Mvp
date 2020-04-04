package com.lgh.mvp.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class CustomNestedScrollView extends NestedScrollView {

    private int originHeight;
    private int rvHeight;

    public CustomNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public CustomNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (originHeight < rvHeight) {
            scrollBy(dx, dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.originHeight = t;
        Log.e("TAG", "onScrollChanged: " + t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setRvHeight(int rvHeight) {
        this.rvHeight = rvHeight;
    }
}
