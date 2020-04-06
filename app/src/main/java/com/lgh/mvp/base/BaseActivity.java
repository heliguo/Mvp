package com.lgh.mvp.base;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private BaseFragment.State currentState = BaseFragment.State.NONE;

    public enum State {
        NONE, LOADING, SUCCESS, ERROR, EMPTY
    }

    private FrameLayout mFrameLayout;

    private View mSuccessView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initListener();
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

//        //全局变灰
//        Paint paint = new Paint();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        //关键点 开启了硬件加速
//        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        return super.onCreateView(name, context, attrs);
    }

    protected abstract void initPresenter();

    public void initListener() {

    }

    protected abstract void initView();

    protected abstract @LayoutRes
    int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        this.release();
    }

    protected void release() {
    }
}
