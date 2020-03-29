package com.lgh.mvp.ui.fragment.pager;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.lgh.multi_rv_library.RViewHelper;
import com.lgh.multi_rv_library.SwipeRefreshHelper;
import com.lgh.multi_rv_library.base.RViewAdapter;
import com.lgh.multi_rv_library.core.RViewCreate;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.presenter.impl.HomePagerPresentImpl;
import com.lgh.mvp.ui.rvitem.PagerRvItem;
import com.lgh.mvp.utils.Constants;
import com.lgh.mvp.view.ICategoryPagerCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback,
        RViewCreate, SwipeRefreshHelper.SwipeRefreshListener {

    private HomePagerPresentImpl mPagerPresent;
    private int materialId;

    public static HomePagerFragment getInstance(Categories.DataBean dataBean) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, dataBean.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIALID, dataBean.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        helper = (new RViewHelper.Builder(this, this)).build();
        setStates(State.SUCCESS);
    }

    @Override
    protected void initPresenter() {
        mPagerPresent = HomePagerPresentImpl.getInstance();
        mPagerPresent.registerCallback(this);
    }

    @Override
    protected void loadDatas() {
        Bundle bundle = getArguments();
        String title = bundle.getString(Constants.KEY_HOME_PAGER_TITLE);
        materialId = bundle.getInt(Constants.KEY_HOME_PAGER_MATERIALID);
        if (mPagerPresent != null) {
            mPagerPresent.getContentByCategoryId(materialId);
        }
    }

    @Override
    public void onCategorLoaded(CategoryPager categoryPager) {
        mDataBeanList.clear();
        mDataBeanList.addAll(categoryPager.getData());
        setStates(State.SUCCESS);
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
    public int materialId() {
        return materialId;
    }

    @Override
    public void onLoaderMoreError(int id) {

    }

    @Override
    public void onLoaderEmpty(int id) {

    }

    @Override
    public void onLoaderMoreLoading(List<CategoryPager.DataBean> dataBeans, int categoryId) {
        setStates(State.SUCCESS);
    }

    @Override
    public void onLooperListLoaded(List<CategoryPager.DataBean> dataBeans, int categoryId) {

    }

    @Override
    protected void release() {
        if (mPagerPresent != null) {
            mPagerPresent.unregisterCallback(this);
        }

    }

    private RViewHelper helper;
    @BindView(R.id.rv_home_pager_content)
    RecyclerView mRecyclerView;
    private List<CategoryPager.DataBean> mDataBeanList = new ArrayList<>();

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return null;
    }

    @Override
    public RecyclerView createRecyclerView() {
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 5;
                outRect.bottom = 5;
            }
        });
        return mRecyclerView;
    }

    @Override
    public RViewAdapter createRViewAdapter() {
        return new RViewAdapter<>(mDataBeanList, new PagerRvItem());
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    @Override
    public void OnRefresh() {

    }
}
