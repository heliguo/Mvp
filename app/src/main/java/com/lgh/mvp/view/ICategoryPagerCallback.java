package com.lgh.mvp.view;

import com.lgh.mvp.base.IBaseCallback;
import com.lgh.mvp.model.domain.CategoryPager;

import java.util.List;

public interface ICategoryPagerCallback extends IBaseCallback<CategoryPager> {

    int materialId();

    void onLoaderMoreError(int id);

    void onLoaderEmpty(int id);

    /**
     * 加载更多
     *
     * @param dataBeans
     */
    void onLoaderMoreLoading(List<CategoryPager.DataBean> dataBeans,int categoryId);

    /**
     * 轮播图
     *
     * @param dataBeans
     */
    void onLooperListLoaded(List<CategoryPager.DataBean> dataBeans,int categoryId);

}
