package com.lgh.rvlibrary.multi_rv_library.listener;

import android.view.View;

/**
 * author:lgh on 2019-11-14 16:12
 */
public interface ItemListener<T> {

    void onItemClick(View view, T entity, int position);

    boolean onItemLongClick(View view, T entity, int position);
}
