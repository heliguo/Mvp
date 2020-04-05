package com.lgh.mvp.base;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
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
