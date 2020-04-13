package com.lgh.mvp.ui.fragment;

import android.view.View;

import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.model.domain.SearchRecommend;
import com.lgh.mvp.model.domain.SearchResult;
import com.lgh.mvp.presenter.impl.SearchPresenterImpl;
import com.lgh.mvp.utils.PresenterManager;
import com.lgh.mvp.view.ISearchCallback;

import java.util.List;

public class SearchFragment extends BaseFragment implements ISearchCallback {

    private SearchPresenterImpl mSearchPresenter;

    @Override
    protected void initPresenter() {
        mSearchPresenter = PresenterManager.getInstance().getSearchPresenter();
        mSearchPresenter.registerCallback(this);
        mSearchPresenter.getRecommedWords();//推荐
        mSearchPresenter.getHistoris();//搜索记录

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected int getRootViewLayoutId() {
        return R.layout.fragment_search_layout;
    }

    @Override
    protected void initView(View rootView) {
        setStates(State.SUCCESS);
    }

    @Override
    public void onHistoriesLoaded(List<String> histories) {

    }

    @Override
    public void onHistoriesDeleted() {

    }

    @Override
    public void onRecommendWordsLoaded(List<SearchRecommend.DataBean> recommend) {

    }

    @Override
    public void onLoadSuccess(SearchResult searchResult, Object... objects) {

    }

    @Override
    public void onError(Object... objects) {

    }

    @Override
    public void onLoading(Object... objects) {

    }

    @Override
    public void onEmpty(Object... objects) {

    }

    @Override
    protected void release() {
        mSearchPresenter.unregisterCallback(this);
    }
}
