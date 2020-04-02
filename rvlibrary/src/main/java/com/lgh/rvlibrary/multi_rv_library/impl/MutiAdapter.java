package com.lgh.rvlibrary.multi_rv_library.impl;



import com.lgh.rvlibrary.multi_rv_library.base.RViewAdapter;

import java.util.List;

public class MutiAdapter extends RViewAdapter<UserInfo1> {

    public MutiAdapter(List<UserInfo1> datas) {
        super(datas);
        addItemStyle(new AItem());
        addItemStyle(new BItem());
    }
}
