package com.lgh.mvp.ui.rvitem;

import android.content.Context;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgh.mvp.R;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.utils.UrilUtils;
import com.lgh.rvlibrary.multi_rv_library.holder.RViewHolder;
import com.lgh.rvlibrary.multi_rv_library.model.RViewItem;

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
    public void convert(RViewHolder holder, CategoryPager.DataBean dataBean, int i) {
        ImageView pic = holder.getView(R.id.goods_pic);
        ViewGroup.LayoutParams layoutParams = pic.getLayoutParams();
        int width = layoutParams.width;
        int height = layoutParams.height;
        int coverSize = (Math.max(width, height)) / 2;
//        LogUtils.e(PagerRvItem.class, "picture width:  " + width
//                + "   picture heigt:  " + height);
        Context context = holder.itemView.getContext();
        Glide.with(context)
                .load(UrilUtils.getCoverPath(dataBean.getPict_url(), coverSize))
                .into(pic);
        TextView title = holder.getView(R.id.goods_title);
        title.setText(dataBean.getTitle());
        TextView from = holder.getView(R.id.goods_from);
        from.setText(dataBean.getNick());
        TextView offPrice = holder.getView(R.id.goods_off_price);
        offPrice.setText(String.valueOf(dataBean.getCoupon_amount()));
        TextView originalPrice = holder.getView(R.id.goods_original_price);
        originalPrice.setText(String.valueOf(dataBean.getCoupon_start_fee()));
        originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//删除线
        TextView soldout = holder.getView(R.id.goods_sold_out_count);
        soldout.setText(String.format(context.getString(R.string.txt_goods_sold_count),
                dataBean.getCoupon_remain_count()));
    }
}
