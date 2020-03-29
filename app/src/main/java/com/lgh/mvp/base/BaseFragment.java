package com.lgh.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initView(inflater, container, savedInstanceState);
        initPresenter();
        loadDatas();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @LayoutRes int layoutId = getLayoutId();
        return inflater.inflate(layoutId, container, false);
    }

    protected abstract @LayoutRes
    int getLayoutId();
}
