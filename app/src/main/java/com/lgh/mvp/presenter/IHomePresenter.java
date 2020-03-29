package com.lgh.mvp.presenter;

import com.lgh.mvp.view.IHomeCallback;

public interface IHomePresenter {

    /**
     * 获取商品分类
     */
    void getCategories();

    void registerCallback(IHomeCallback callback);

    void unregisterCallback(IHomeCallback callback);

}
