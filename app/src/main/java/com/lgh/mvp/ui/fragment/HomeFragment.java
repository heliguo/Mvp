package com.lgh.mvp.ui.fragment;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.presenter.impl.HomePresenterImpl;
import com.lgh.mvp.ui.adapter.HomePagerAdapter;
import com.lgh.mvp.view.IHomeCallback;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IHomeCallback {

    private HomePresenterImpl mHomePresenter;
    private HomePagerAdapter mAdapter;

    @BindView(R.id.home_indicator)
    TabLayout mTabLayout;
    @BindView(R.id.home_pager)
    ViewPager mViewPager;


    protected int getRootViewLayoutId() {
        return R.layout.base_home_fragment_layout;
    }

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
        if (mAdapter != null) {
            setStates(State.SUCCESS);
            mAdapter.setGategoris(categories);
        }
    }

    @Override
    public void onError(Object... objects) {
        setStates(State.ERROR);
    }

    @Override
    public void onLoading(Object... objects) {
        setStates(State.LOADING);
    }

    @Override
    public void onEmpty(Object... objects) {
        setStates(State.EMPTY);
    }


    @Override
    protected void onRetry() {
        if (mHomePresenter != null) {
            mHomePresenter.getCategories();
        }
    }

    @Override
    protected void initView(View rootView) {
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
    }

    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unregisterCallback(this);
        }

    }
}
