package com.lgh.mvp.ui.rvitem;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgh.multi_rv_library.holder.RViewHolder;
import com.lgh.multi_rv_library.model.RViewItem;
import com.lgh.mvp.R;
import com.lgh.mvp.model.domain.CategoryPager;

import java.io.File;

public class PagerRvItem implements RViewItem<CategoryPager.DataBean> {

    @Override
    public int getItemLayout() {
        return R.layout.item_home_pager_content;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public boolean isItemView(CategoryPager.DataBean dataBean, int i) {
        return true;
    }

    @Override
    public void convert(RViewHolder holder, CategoryPager.DataBean dataBean, int i, Context context) {
        ImageView pic = holder.getView(R.id.goods_pic);
        Glide.with(context).load("http:" + File.separator + dataBean.getPict_url()).into(pic);
        TextView title = holder.getView(R.id.goods_title);
        title.setText(dataBean.getTitle());
        TextView from = holder.getView(R.id.goods_from);
        from.setText(dataBean.getNick());
        TextView offPrice = holder.getView(R.id.goods_off_price);
        offPrice.setText(String.valueOf(dataBean.getCoupon_amount()));
        TextView originalPrice = holder.getView(R.id.goods_original_price);
        originalPrice.setText(String.valueOf(dataBean.getCoupon_start_fee()));
        TextView soldout = holder.getView(R.id.goods_sold_out_count);
        soldout.setText(String.valueOf(dataBean.getCoupon_remain_count()));
    }
}
