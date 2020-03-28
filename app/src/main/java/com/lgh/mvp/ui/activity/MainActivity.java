package com.lgh.mvp.ui.activity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseFragment;
import com.lgh.mvp.ui.fragment.HomeFragment;
import com.lgh.mvp.ui.fragment.RedPacketFragment;
import com.lgh.mvp.ui.fragment.SearchFragment;
import com.lgh.mvp.ui.fragment.SelectFragment;
import com.lgh.mvp.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_navigation_bar)
    BottomNavigationView navigationView;
    private HomeFragment mHomeFragment;
    private SearchFragment mSearchFragment;
    private SelectFragment mSelectFragment;
    private RedPacketFragment mRedPacketFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initListener();
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mSelectFragment = new SelectFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSearchFragment = new SearchFragment();
        swichFragment(mHomeFragment);
    }

    private void initListener() {
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

    private void swichFragment(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_fragment, fragment);
        LogUtils.e(MainActivity.class, fragment.toString());
        ft.commit();
    }

}
