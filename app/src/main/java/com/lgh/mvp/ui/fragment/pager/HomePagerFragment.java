package com.lgh.mvp.ui.fragment.pager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.presenter.impl.HomePagerPresenterImpl;
import com.lgh.mvp.presenter.impl.TicketPresenterImpl;
import com.lgh.mvp.ui.activity.TicketActivity;
import com.lgh.mvp.ui.adapter.LooperPagerAdapter;
import com.lgh.mvp.ui.custom.CustomNestedScrollView;
import com.lgh.mvp.ui.custom.CustomRecyclerview;
import com.lgh.mvp.ui.rvitem.PagerRvItem;
import com.lgh.mvp.utils.Constants;
import com.lgh.mvp.utils.PresenterManager;
import com.lgh.mvp.utils.SizeUtils;
import com.lgh.mvp.utils.ToastUtils;
import com.lgh.mvp.view.ICategoryPagerCallback;
import com.lgh.rvlibrary.multi_rv_library.RViewHelper;
import com.lgh.rvlibrary.multi_rv_library.SwipeRefreshHelper;
import com.lgh.rvlibrary.multi_rv_library.base.RViewAdapter;
import com.lgh.rvlibrary.multi_rv_library.core.RViewCreate;
import com.lgh.rvlibrary.multi_rv_library.listener.ItemListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback,
        RViewCreate, SwipeRefreshHelper.SwipeRefreshListener {

    private Context mContext;
    private HomePagerPresenterImpl mPagerPresent;
    private int materialId;

    @BindView(R.id.home_rv_height)
    LinearLayout mLlRvContainer;
    @BindView(R.id.looper_vp)
    ViewPager looperVp;
    @BindView(R.id.home_pager_title)
    TextView mTitle;
    @BindView(R.id.looper_point_container)
    LinearLayout mLinearLayout;
    @BindView(R.id.select_point)
    ImageView selectPoint;
    private float selectPointPos;
    @BindView(R.id.home_pager_refresh)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.custom_nested_scroll_view)
    CustomNestedScrollView mScrollView;
    @BindView(R.id.home_pager_no_scroll_ll)
    LinearLayout noScrollViewLl;

    private LooperPagerAdapter mLooperAdapter;

    private RViewAdapter<CategoryPager.DataBean> mAdapter;

    public static HomePagerFragment getInstance(Categories.DataBean dataBean) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, dataBean.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIALID, dataBean.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    /**
     * 实例化一个Handler 让轮播自动循环
     */

    private static class LooperHandler extends Handler {

        private final WeakReference<HomePagerFragment> mReference;

        private LooperHandler(HomePagerFragment reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            HomePagerFragment fragment = mReference.get();
            if (fragment != null) {
                int item = fragment.looperVp.getCurrentItem() + 1;
                fragment.looperVp.setCurrentItem(item);
                fragment.mHandler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    }

    private final LooperHandler mHandler = new LooperHandler(this);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        setStates(State.SUCCESS);
        mContext = getContext();
        (new RViewHelper.Builder(this, this)).build();
        looperVp.setTag(materialId);
        mLooperAdapter = new LooperPagerAdapter();
        looperVp.setAdapter(mLooperAdapter);

        mHandler.sendEmptyMessageDelayed(0, 1000);

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(true);

    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (mPagerPresent != null) {
                    mPagerPresent.loadMore(materialId);
                }
            }

        });

        mLlRvContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int measuredHeight = mLlRvContainer.getMeasuredHeight();
                if (measuredHeight != 0)
                    mLlRvContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
                layoutParams.height = measuredHeight;
                mRecyclerView.setLayoutParams(layoutParams);
            }
        });

        noScrollViewLl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int noScrollViewLlHeight = noScrollViewLl.getMeasuredHeight();
                if (noScrollViewLlHeight != 0) {
//                    mScrollView.setRvHeight(noScrollViewLlHeight);
                    noScrollViewLl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        mRecyclerView.setNestedScrollingEnabled(false);

        mAdapter.setItemListener(new ItemListener<CategoryPager.DataBean>() {
            @Override
            public void onItemClick(View view, CategoryPager.DataBean entity, int position) {
                Log.e("TAG", "onItemClick: ");
                String title = entity.getTitle();
                String pict_url = entity.getPict_url();
                String click_url = entity.getClick_url();
                TicketPresenterImpl ticketPresenter = PresenterManager.
                        getInstance().getTicketPresenter();
                ticketPresenter.getTicket(title, click_url, pict_url);
                startActivity(new Intent(mContext, TicketActivity.class));
                view.findViewById(R.id.goods_pic).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("doid", "onClick: " + "图片点击");
                    }
                });
            }

            @Override
            public boolean onItemLongClick(View view, CategoryPager.DataBean entity, int position) {
                return false;
            }
        });
    }

    @Override
    protected void initPresenter() {
        mPagerPresent = PresenterManager.getInstance().getHomePagerPresent();
        mPagerPresent.registerCallback(this);
    }

    @Override
    protected void loadDatas() {
        Bundle bundle = getArguments();
        String title = bundle.getString(Constants.KEY_HOME_PAGER_TITLE);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        materialId = bundle.getInt(Constants.KEY_HOME_PAGER_MATERIALID);
        if (mPagerPresent != null) {
            mPagerPresent.getContentByCategoryId(materialId);
        }
    }

    @Override
    public void onLoadSuccess(CategoryPager categoryPager, Object... objects) {
        mDataBeanList.clear();
        mDataBeanList.addAll(categoryPager.getData());
        setStates(State.SUCCESS);
        mRefreshLayout.setEnableLoadmore(true);
    }

    @Override
    public void onError(Object... objects) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnableLoadmore(false);
        }
        setStates(State.ERROR);
    }

    @Override
    public void onLoading(Object... objects) {
        setStates(State.LOADING);
    }

    @Override
    public void onEmpty(Object... objects) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnableLoadmore(false);
        }
        setStates(State.EMPTY);
    }

    @Override
    public int materialId() {
        return materialId;
    }

    @Override
    public void onLoaderMoreError(int id) {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onLoaderMoreEmpty(int id) {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
        ToastUtils.showToast("没有更多数据了");
    }

    @Override
    public void onLoaderMoreLoading(List<CategoryPager.DataBean> dataBeans, int categoryId) {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
        mAdapter.addDatasRange(dataBeans);
        ToastUtils.showToast("加载了" + dataBeans.size() + "条数据");
    }

    @Override
    public void onLooperListLoaded(List<CategoryPager.DataBean> dataBeans, int categoryId) {
        mLooperAdapter.setDatas(dataBeans);
        // 保证imageViews的整数倍 Integer.MAX_VALUE=2147483647
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dataBeans.size();
        looperVp.setCurrentItem(item);
        mLinearLayout.removeAllViews();
        GradientDrawable normalDrawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.shape_point_normal);
        for (int i = 0; i < dataBeans.size(); i++) {
            View point = new View(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (SizeUtils.dp2px(mContext, 8), SizeUtils.dp2px(mContext, 8));

            if (i > 0)
                layoutParams.leftMargin = SizeUtils.dp2px(mContext, 10);
            point.setLayoutParams(layoutParams);
            point.setBackground(normalDrawable);
            mLinearLayout.addView(point);
        }
        looperVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int p = position % dataBeans.size();
                float distance = positionOffset * selectPointPos + selectPointPos * p;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) selectPoint.getLayoutParams();
                if (p == dataBeans.size() - 1) {
                    if (positionOffset > 0.5) {
                        params.leftMargin = 0;
                    } else
                        params.leftMargin = ((int) (selectPointPos * p));
                } else {
                    params.leftMargin = ((int) distance);
                }
                selectPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_DRAGGING) { // 拖动
                    mHandler.removeCallbacksAndMessages(null);
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) { // 持续(滚动)
                    mHandler.removeCallbacksAndMessages(null);
                } else if (state == ViewPager.SCROLL_STATE_IDLE) { // 闲置(静止)
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                }
            }

        });

        selectPoint.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (mLinearLayout != null && mLinearLayout.getChildCount() > 1) {
                            selectPointPos = mLinearLayout.getChildAt(1).getLeft() -
                                    mLinearLayout.getChildAt(0).getLeft();
                            if (selectPointPos != 0) {
                                selectPoint.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                            }

                        }
                    }
                });
    }


    @Override
    protected void release() {
        if (mPagerPresent != null) {
            mPagerPresent.unregisterCallback(this);
            mHandler.removeCallbacksAndMessages(null);
        }

    }

    @BindView(R.id.rv_home_pager_content)
    CustomRecyclerview mRecyclerView;
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
        mAdapter = new RViewAdapter<>(mDataBeanList, new PagerRvItem());
        return mAdapter;
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    @Override
    public void OnRefresh() {

    }

}