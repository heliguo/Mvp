package com.lgh.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.utils.UrilUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class LooperPagerAdapter extends PagerAdapter {

    private List<CategoryPager.DataBean> dataBeans = new ArrayList<>();

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realSize = position % dataBeans.size();
        CategoryPager.DataBean dataBean = dataBeans.get(realSize);
        String pict_url = UrilUtils.getCoverPath(dataBean.getPict_url());
        ImageView iv = new ImageView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(iv.getContext()).load(pict_url).into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public int getCount() {
        if (dataBeans.size() == 1)
            return 1;
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((View) object));
    }

    public void setDatas(List<CategoryPager.DataBean> dataBeans) {
        this.dataBeans.addAll(dataBeans);
        notifyDataSetChanged();
    }
}
