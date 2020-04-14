package com.lgh.mvp.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgh.mvp.R;
import com.lgh.mvp.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索记录，标签流式布局
 */
public class TextFlowLayout extends ViewGroup {

    private static final float DEFAULT_SPACE = 10;

    private float mItemHorizontalSpace = DEFAULT_SPACE;

    private float mItemVerticalSpace = DEFAULT_SPACE;

    private List<String> mTextLists;

    private OnFlowTextItemListener mItemListener;

    public TextFlowLayout(Context context) {
        this(context, null);
    }

    public TextFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextFlowLayout);
        mItemHorizontalSpace = typedArray.getDimension(R.styleable.TextFlowLayout_horizontalSpace,
                DEFAULT_SPACE);
        mItemHorizontalSpace = typedArray.getDimension(R.styleable.TextFlowLayout_verticalSpace,
                DEFAULT_SPACE);
        typedArray.recycle();

    }

    public float getItemHorizontalSpace() {
        return mItemHorizontalSpace;
    }

    public void setItemHorizontalSpace(float itemHorizontalSpace) {
        mItemHorizontalSpace = itemHorizontalSpace;
    }

    public float getItemVerticalSpace() {
        return mItemVerticalSpace;
    }

    public void setItemVerticalSpace(float itemVerticalSpace) {
        mItemVerticalSpace = itemVerticalSpace;
    }

    public void setTextLists(List<String> textLists) {
        this.mTextLists = textLists;
        for (String text : mTextLists) {
            /**
             * true 等价于 addview 操作
             */
//            LayoutInflater.from(getContext()).
//                    inflate(R.layout.flow_text_view, this, true);
            TextView item = (TextView) LayoutInflater.from(getContext()).
                    inflate(R.layout.flow_text_view, this, false);
            item.setText(text);
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener != null) {
                        mItemListener.onFlowItemClick(text);
                    }
                }
            });
            addView(item);
        }
    }

    private List<List<View>> lines = new ArrayList<>();
    private int mSelfWidth;
    private int mSelfHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mSelfWidth = MeasureSpec.getSize(widthMeasureSpec)
                - getPaddingEnd() - getPaddingStart();
        lines.clear();
        List<View> line = null;
        /**
         * 测量子view
         */
        for (int i = 0; i < getChildCount(); i++) {
            View itemView = getChildAt(i);
            if (itemView.getVisibility() != VISIBLE) {
                continue;
            }
            LogUtils.e(this, "  测量前----->  " + itemW(itemView));
            measureChild(itemView, widthMeasureSpec, heightMeasureSpec);
            LogUtils.e(this, "  测量后----->  " + itemH(itemView));
            if (line == null) {
                //说明当前行为空
                line = createNewLine(itemView);
            } else {
                if (canBeAdded(itemView, line)) {
                    line.add(itemView);
                } else {
                    line = createNewLine(itemView);
                }
            }
        }
        mSelfHeight = itemH(getChildAt(0));
        int selfHeight = (int) (lines.size() * mSelfHeight
                + mItemVerticalSpace * (lines.size() + 1) + 0.5f);
        setMeasuredDimension(mSelfWidth, selfHeight);
    }

    private List<View> createNewLine(View itemView) {
        List<View> line = new ArrayList<>();
        line.add(itemView);
        lines.add(line);
        return line;
    }

    /**
     * 判断当前行是否可添加
     *
     * @param itemView
     * @param line
     */
    private boolean canBeAdded(View itemView, List<View> line) {
        float totalWidth = 0f;
        for (int i = 0; i < line.size(); i++) {
            totalWidth += itemW(line.get(i));
        }
        totalWidth += (line.size() + 1) * mItemHorizontalSpace;
        totalWidth += itemW(itemView);
        return totalWidth <= mSelfWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topOffset = (int) mItemVerticalSpace;
        for (List<View> views : lines) {
            int leftOffset = (int) mItemHorizontalSpace;
            for (View view : views) {
                view.layout(leftOffset, topOffset,
                        leftOffset + itemW(view), topOffset + itemH(view));
                leftOffset += itemW(view) + mItemHorizontalSpace;
            }
            topOffset += mSelfHeight + mItemVerticalSpace;
        }

//        View itemOne = getChildAt(0);
//        itemOne.layout(0, 0, itemW(itemOne), itemH(itemOne));
//
//        View itemTwo = getChildAt(1);
//        itemTwo.layout(itemOne.getRight() + (int) mItemHorizontalSpace,
//                0, itemOne.getRight() + (int) mItemHorizontalSpace + itemW(itemTwo),
//                itemH(itemTwo));
    }

    private int itemW(View item) {
        return item.getMeasuredWidth();
    }

    private int itemH(View item) {
        return item.getMeasuredHeight();
    }

    public void setItemListener(OnFlowTextItemListener itemListener) {
        this.mItemListener = itemListener;
    }

    public interface OnFlowTextItemListener {
        void onFlowItemClick(String text);
    }

}
