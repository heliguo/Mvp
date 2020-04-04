package com.lgh.mvp.ui.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseActivity;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.ui.fragment.HomeFragment;
import com.lgh.mvp.ui.fragment.RedPacketFragment;
import com.lgh.mvp.ui.fragment.SearchFragment;
import com.lgh.mvp.ui.fragment.SelectFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.main_navigation_bar)
    BottomNavigationView navigationView;
    private HomeFragment mHomeFragment;
    private SearchFragment mSearchFragment;
    private SelectFragment mSelectFragment;
    private RedPacketFragment mRedPacketFragment;


    @Override
    protected void initView() {
        mHomeFragment = new HomeFragment();
        mSelectFragment = new SelectFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSearchFragment = new SearchFragment();
        swichFragment(mHomeFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    swichFragment(mHomeFragment);
                    break;
                case R.id.action_select:
                    swichFragment(mSelectFragment);
                    break;
                case R.id.action_red_packet:
                    swichFragment(mRedPacketFragment);
                    break;
                case R.id.action_search:
                    swichFragment(mSearchFragment);
                    break;
                default:
                    break;
            }
            return true;
        });
    }


    private BaseFragment lastFragment = null;

    private void swichFragment(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!fragment.isAdded()) {
            ft.add(R.id.main_fragment, fragment);
        } else {
            ft.show(fragment);
        }
        if (lastFragment != null) {
            ft.hide(lastFragment);
        }
        lastFragment = fragment;
//        ft.replace(R.id.main_fragment, fragment);//会销毁重建
        ft.commit();
    }


}
