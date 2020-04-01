package com.lgh.mvp.ui.fragment.pager;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgh.multi_rv_library.RViewHelper;
import com.lgh.multi_rv_library.SwipeRefreshHelper;
import com.lgh.multi_rv_library.base.RViewAdapter;
import com.lgh.multi_rv_library.core.RViewCreate;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.presenter.impl.HomePagerPresentImpl;
import com.lgh.mvp.ui.adapter.LooperPagerAdapter;
import com.lgh.mvp.ui.rvitem.PagerRvItem;
import com.lgh.mvp.utils.Constants;
import com.lgh.mvp.utils.LogUtils;
import com.lgh.mvp.utils.SizeUtils;
import com.lgh.mvp.view.ICategoryPagerCallback;

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
    private HomePagerPresentImpl mPagerPresent;
    private int materialId;

    @BindView(R.id.looper_vp)
    ViewPager looperVp;
    @BindView(R.id.home_pager_title)
    TextView mTitle;
    @BindView(R.id.looper_point_container)
    LinearLayout mLinearLayout;
    @BindView(R.id.select_point)
    ImageView selectPoint;
    private float selectPointPos;

    private LooperPagerAdapter mLooperAdapter;

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

    private static class LooperHandler extends Handler{

        private final WeakReference<HomePagerFragment> mReference;

        private LooperHandler(HomePagerFragment reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            HomePagerFragment fragment = mReference.get();
            if (fragment!=null){
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
        mContext = getContext();

        (new RViewHelper.Builder(this, this)).build();
        setStates(State.SUCCESS);
        mLooperAdapter = new LooperPagerAdapter();
        looperVp.setAdapter(mLooperAdapter);
        mHandler.sendEmptyMessageDelayed(0, 1000);
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
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
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
        LogUtils.e(this, dataBeans.toString());
        mLooperAdapter.setDatas(dataBeans);
        // 保证imageViews的整数倍 Integer.MAX_VALUE=2147483647
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dataBeans.size();
        looperVp.setCurrentItem(item);
        mLinearLayout.removeAllViews();
        GradientDrawable normalDrawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.shape_point);
        normalDrawable.setColor(ContextCompat.getColor(mContext, R.color.white));
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
                        selectPointPos = mLinearLayout.getChildAt(1).getLeft() -
                                mLinearLayout.getChildAt(0).getLeft();
                        selectPoint.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
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
