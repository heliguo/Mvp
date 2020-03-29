package com.lgh.mvp.ui.fragment;

import android.view.View;

import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;

public class SearchFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View rootView) {
        setStates(State.SUCCESS);
    }
}
