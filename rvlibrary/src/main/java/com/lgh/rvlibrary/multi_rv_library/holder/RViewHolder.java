package com.lgh.rvlibrary.multi_rv_library.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author:lgh on 2019-11-14 15:58
 */
public class RViewHolder extends RecyclerView.ViewHolder {

    //控件收集，作为缓存
    private SparseArray<View> mViews;//view控件集合
    //提供当前view
    private View mConvertView;

     private RViewHolder(@NonNull View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    //对外提供创建viewholder的方法
    public static RViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RViewHolder(itemView);
    }

    //通过ID获取控件对象
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            //key:R.id.xxx  value:textview/edittext/...
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}
