package com.lgh.rvlibrary.multi_rv_library.impl;

import android.content.Context;
import android.widget.TextView;

import com.lgh.rvlibrary.R;
import com.lgh.rvlibrary.multi_rv_library.holder.RViewHolder;
import com.lgh.rvlibrary.multi_rv_library.model.RViewItem;

public class AItem implements RViewItem<UserInfo1> {
    @Override
    public int getItemLayout() {
        return R.layout.item_list;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public boolean isItemView(UserInfo1 entity, int position) {
        return entity.getType() == 1;
    }

    @Override
    public void convert(RViewHolder holder, UserInfo1 entity, int position) {
        TextView account = holder.getView(R.id.single_account);
        account.setText(entity.getAccount());
        TextView password = holder.getView(R.id.single_password);
        password.setText(entity.getPassword());
    }
}

