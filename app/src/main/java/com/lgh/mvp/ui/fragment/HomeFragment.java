package com.lgh.mvp.ui.fragment;

import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.presenter.impl.HomePresenterImpl;
import com.lgh.mvp.view.IHomeCallback;

public class HomeFragment extends BaseFragment implements IHomeCallback {

    private HomePresenterImpl mHomePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerCallback(this);
    }

    @Override
    protected void loadDatas() {
        mHomePresenter.getCategories();
    }

    @Override
    public void onCategorLoaded(Categories categories) {

    }

    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unregisterCallback(this);
        }

    }
}
