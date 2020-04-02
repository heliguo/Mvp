package com.lgh.rvlibrary.multi_rv_library.impl;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lgh.rvlibrary.multi_rv_library.base.RViewAdapter;
import com.lgh.rvlibrary.multi_rv_library.listener.ItemListener;

import java.util.ArrayList;
import java.util.List;

public class RViewAdapterActivity extends BaseAdapterActivity {

    private List<UserInfo1> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    protected void initDatas() {
        if (datas.isEmpty()) {
            for (int i = 0; i < 100; i++) {
                UserInfo1 userInfo = new UserInfo1();
                if (i < 50) {
                    userInfo.setType(1);
                    userInfo.setAccount("account" + i);
                    userInfo.setPassword(i + "");
                } else {
                    userInfo.setType(2);
                    userInfo.setAccount("account_" + i);
                    userInfo.setPassword(i + "_");
                }

                datas.add(userInfo);
            }
        }
    }

    @Override
    public void OnRefresh() {
        initDatas();
        datas.add(new UserInfo1("hhh","345",1));
        notifyAdapterDataSetChanged(datas);
    }

    @Override
    public RViewAdapter createRViewAdapter() {
//        //单一布局
//        RViewItem<UserInfo> item1 = new RViewItem<UserInfo>() {
//            @Override
//            public int getItemLayout() {
//                return R.layout.item_list;
//            }
//
//            @Override
//            public boolean openClick() {
//                return true;
//            }
//
//            @Override
//            public boolean isItemView(UserInfo entity, int position) {
//                return true;
//            }
//
//            @Override
//            public void convert(RViewHolder holder, UserInfo entity, int position) {
//                TextView account = holder.getView(R.id.single_account);
//                account.setText(entity.getAccount());
//                TextView password = holder.getView(R.id.single_password);
//                password.setText(entity.getPassword());
//            }
//        };
//        RViewAdapter adapter = new RViewAdapter<UserInfo>(datas, item1);
        MutiAdapter adapter = new MutiAdapter(datas);
        adapter.setItemListener(new ItemListener<UserInfo1>() {
            @Override
            public void onItemClick(View view, UserInfo1 entity, int position) {
                Log.e("123455666777", "onItemClick: " + position);
            }

            @Override
            public boolean onItemLongClick(View view, UserInfo1 entity, int position) {
                return false;
            }
        });
        return adapter;
    }
}
