package com.lgh.mvp.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.lgh.mvp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


/**
 * 自定义自动轮播图
 */
public class AutoLoopViewPager extends ViewPager {

    private static final long DEFAULT_DURATION = 3000;
    private long mDuration = DEFAULT_DURATION;
    private boolean isLoop = false;


    public AutoLoopViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //读取属性
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLoopViewPager);
        mDuration = typedArray.getInteger(R.styleable.AutoLoopViewPager_atuo_duration, (int) DEFAULT_DURATION);
        typedArray.recycle();
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public void startLoop() {
        isLoop = true;
        post(mTask);
    }

    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            if (isLoop) {
                postDelayed(this, mDuration);
            }
        }
    };

    public void stopLoop() {
        isLoop = false;
        removeCallbacks(mTask);
    }
}
