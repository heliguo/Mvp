package com.lgh.rvlibrary.multi_rv_library.model;


import android.content.Context;

import com.lgh.rvlibrary.multi_rv_library.holder.RViewHolder;


/**
 * author:lgh on 2019-11-14 15:48
 * javabean，记录条目,面向接口编程
 */
public interface RViewItem<T> {

    //布局
    int getItemLayout();

    //是否可点击
    boolean openClick();

    //是否是当前item所需布局
    boolean isItemView(T entity, int position);

    //将item控件与数据绑定
    void convert(RViewHolder holder, T entity, int position);

}
