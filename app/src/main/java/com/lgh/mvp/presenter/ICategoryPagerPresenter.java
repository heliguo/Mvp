package com.lgh.mvp.presenter;

import com.lgh.mvp.base.IBasePresenter;
import com.lgh.mvp.view.ICategoryPagerCallback;

public interface ICategoryPagerPresenter extends IBasePresenter<ICategoryPagerCallback> {

    void getContentByCategoryId(int id);

    void loadMore(int id);

    void reload(int id);

}
