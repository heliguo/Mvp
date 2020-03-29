package com.lgh.mvp.presenter;

import com.lgh.mvp.base.IBasePresenter;
import com.lgh.mvp.view.IHomeCallback;

public interface IHomePresenter extends IBasePresenter<IHomeCallback> {

    void getCategories();

}
