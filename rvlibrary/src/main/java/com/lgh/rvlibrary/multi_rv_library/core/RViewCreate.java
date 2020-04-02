package com.lgh.rvlibrary.multi_rv_library.core;


import android.content.Context;

import com.lgh.rvlibrary.multi_rv_library.base.RViewAdapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * 创建ViewHelper所需的数据
 * 其实现类为ViewHelper提供数据
 */
public interface RViewCreate<T> {

    /**
     * 上下文
     */
    Context context();

    /**
     * 下拉刷新
     */
    SwipeRefreshLayout createSwipeRefresh();

    /**
     * 创建RecyclerView
     */
    RecyclerView createRecyclerView();

    /**
     * 创建adapter
     */
    RViewAdapter<T> createRViewAdapter();

    /**
     * 是否分页
     */
    boolean isSupportPaging();

}
