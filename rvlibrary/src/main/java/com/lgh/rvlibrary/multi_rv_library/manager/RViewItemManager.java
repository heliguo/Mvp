package com.lgh.rvlibrary.multi_rv_library.manager;


import android.content.Context;


import com.lgh.rvlibrary.multi_rv_library.holder.RViewHolder;
import com.lgh.rvlibrary.multi_rv_library.model.RViewItem;

import androidx.collection.SparseArrayCompat;

/**
 * author:lgh on 2019-11-14 16:15
 * 条目管理，配合Adapter
 */
public class RViewItemManager<T> {

    //key:int viewType value RViewItem
    private SparseArrayCompat<RViewItem<T>> styles = new SparseArrayCompat<>();

    public void addStyle(RViewItem<T> item) {
        if (item != null) {
            styles.put(styles.size(), item);
        }
    }

    public int getItemViewStylesCount() {
        return styles.size();
    }

    //获取集合中的key
    public int getItemViewType(T entity, int position) {
        //避免开发者增删集合抛出异常
        for (int i = styles.size() - 1; i >= 0; i--) {
            RViewItem<T> item = styles.valueAt(i);
            //校验
            if (item.isItemView(entity, position)) {
                return styles.keyAt(i);
            }

        }
        throw new RuntimeException(RViewItemManager.class.getSimpleName() + "异常类型");
    }

    public RViewItem getRViewItem(int viewType) {
        return styles.get(viewType);
    }

    public void convert(RViewHolder holder, T entity, int position) {
        for (int i = 0; i < styles.size(); i++) {
            RViewItem<T> item = styles.valueAt(i);
            if (item.isItemView(entity, position)) {
                item.convert(holder, entity, position);
                return;
            }
        }
        throw new RuntimeException(RViewItemManager.class.getSimpleName() + "data convert failed");
    }
}
