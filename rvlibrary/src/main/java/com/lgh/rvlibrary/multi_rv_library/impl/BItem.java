package com.lgh.rvlibrary.multi_rv_library.impl;

import android.content.Context;
import android.widget.TextView;

import com.lgh.rvlibrary.R;
import com.lgh.rvlibrary.multi_rv_library.holder.RViewHolder;
import com.lgh.rvlibrary.multi_rv_library.model.RViewItem;


public class BItem implements RViewItem<UserInfo1> {
    @Override
    public int getItemLayout() {
        return R.layout.item_list1;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public boolean isItemView(UserInfo1 entity, int position) {
        return entity.getType() == 2;
    }

    @Override
    public void convert(RViewHolder holder, UserInfo1 entity, int position) {
        TextView account = holder.getView(R.id.single_account1);
        account.setText(entity.getAccount());
        TextView password = holder.getView(R.id.single_password1);
        password.setText(entity.getPassword());
    }
}
