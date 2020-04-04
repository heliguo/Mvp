package com.lgh.mvp.ui.adapter;

import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.ui.fragment.pager.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Categories.DataBean> mDataBeans = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDataBeans.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return HomePagerFragment.getInstance(mDataBeans.get(position));
    }

    @Override
    public int getCount() {
        return mDataBeans.size();
    }

    public void setGategoris(Categories categories) {
        mDataBeans.clear();
        List<Categories.DataBean> datas = categories.getData();
        mDataBeans.addAll(datas);
        notifyDataSetChanged();
    }


}
