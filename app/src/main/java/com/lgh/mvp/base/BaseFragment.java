package com.lgh.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lgh.mvp.R;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * fragment 基类
 */
public abstract class BaseFragment extends Fragment {

    private State currentState = State.NONE;

    public enum State {
        NONE, LOADING, SUCCESS, ERROR, EMPTY
    }

    private Unbinder mUnbinder;

    private FrameLayout mFrameLayout;

    private View mSuccessView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    @OnClick(R.id.ll_net_error)
    public void retry() {
        onRetry();
    }

    /**
     * 网络错误重试
     */
    protected void onRetry() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = loadRootView(inflater, container);
        mFrameLayout = rootView.findViewById(R.id.base_container);
        loadStateView(inflater, container);
        mUnbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        initListener();
        initPresenter();
        loadDatas();
        return rootView;
    }

    public void initListener() {

    }

    private View loadRootView(LayoutInflater inflater, ViewGroup container) {
        @LayoutRes int id = getRootViewLayoutId();
        if (id != 0) {
            return inflater.inflate(id, container, false);
        }
        return inflater.inflate(R.layout.base_fragment_layout, container, false);
    }

    protected @LayoutRes
    int getRootViewLayoutId() {
        return 0;
    }

    /**
     * 加载状态
     *
     * @param inflater
     * @param container
     */
    private void loadStateView(LayoutInflater inflater, ViewGroup container) {
        //成功
        mSuccessView = loadSuccess(inflater);
        mFrameLayout.addView(mSuccessView);

        //loadingview
        mLoadingView = loadingView(inflater);
        mFrameLayout.addView(mLoadingView);
        //error
        mErrorView = loadErrorView(inflater);
        mFrameLayout.addView(mErrorView);

        //empty
        mEmptyView = loadEmptyView(inflater);
        mFrameLayout.addView(mEmptyView);

        setStates(State.NONE);
    }

    /**
     * 设置加载状态
     *
     * @param states
     */
    public void setStates(State states) {
        this.currentState = states;
        mSuccessView.setVisibility(currentState == State.SUCCESS ? View.VISIBLE : View.GONE);
        mLoadingView.setVisibility(currentState == State.LOADING ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(currentState == State.ERROR ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(currentState == State.EMPTY ? View.VISIBLE : View.GONE);
    }

    /**
     * 外部数据加载成功界面展示，布局自定义（必须）
     *
     * @param inflater
     * @return
     */
    protected View loadSuccess(LayoutInflater inflater) {
        @LayoutRes int layoutId = getLayoutId();
        return inflater.inflate(layoutId, null);
    }

    /**
     * 成功获得数据展示 布局
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 数据为空...
     *
     * @param inflater
     * @return
     */
    protected View loadEmptyView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_empty_view, null);
    }

    /**
     * 数据加载失败...
     *
     * @param inflater
     * @return
     */
    protected View loadErrorView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_error_view, null);
    }

    /**
     * 正在加载...
     *
     * @param inflater
     * @return
     */
    protected View loadingView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_loading_view, null);
    }

    /**
     * 对外暴露根布局
     *
     * @param rootView
     */
    protected void initView(View rootView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        release();

    }

    /**
     * 释放资源
     */
    protected void release() {

    }

    protected void initPresenter() {

    }

    /**
     * 加载数据，不一定都需要
     */
    protected void loadDatas() {

    }

}