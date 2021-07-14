package com.geoffrey.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/10/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class StickyHeadItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaintDrawRect, mPaintDrawText;
    private float density;
    private final int FONT_SIZE = 12;
    private final int TEXT_BASE_LINE = 40;

    public StickyHeadItemDecoration(Context context) {
        mPaintDrawRect = new Paint();
        mPaintDrawRect.setColor(Color.BLUE);
        mPaintDrawText = new Paint();
        if (context != null) {
            density = context.getResources().getDisplayMetrics().density;
        }
        mPaintDrawText.setTextSize(FONT_SIZE * density);
        mPaintDrawText.setColor(Color.WHITE);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position % 5 == 0) {
            outRect.set(0, 50, 0, 0);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //adapter可见范围内child数量
        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childView = parent.getChildAt(i);
//            //获取到view在整个adapter中的index
//            int childAdapterIndex = parent.getChildAdapterPosition(childView);
//            if (childAdapterIndex % 5 == 0) {
//                //绘制索引为5的倍数的item顶部的head
//                c.drawRect(childView.getLeft(), childView.getTop() - 50, childView.getRight(), childView.getTop(), mPaintDrawRect);
//            }
//        }

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //获取整个RecyclerView的childView数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            //获取到该childView在整个adapter中的索引
            int childAdapterIndex = parent.getChildAdapterPosition(childView);
            //获取到有header的item
            if (childAdapterIndex % 5 == 0) {
                int item = childAdapterIndex / 5;
                if (i < 5) {
                    //位于屏幕可见item顺位第二,且带sticky head,距离顶部距离小于两个header的高度
                    if (i == 1 && childView.getTop() < 100) {
                        int left = childView.getLeft();
                        int top = childView.getTop() - 100;
                        int right = childView.getRight();
                        int bottom = childView.getTop() - 50;
                        c.drawRect(left, top, right, bottom, mPaintDrawRect);
                        c.drawText("条目:" + item + ",adapterIndex = " + childAdapterIndex, left, top + TEXT_BASE_LINE, mPaintDrawText);

                    } else {
                        //其他位置的,且自带stickyhead 的item
                        int left = childView.getLeft();
                        int top = 0;
                        int right = childView.getRight();
                        int bottom = 50;
                        c.drawRect(left, top, right, bottom, mPaintDrawRect);
                        c.drawText("条目:" + item + ",adapterIndex = " + childAdapterIndex, left, top + TEXT_BASE_LINE, mPaintDrawText);
                    }
                }
                if (i != 0) {
                    //如果不是可见item的第一顺位,则正常绘制header
                    int left = childView.getLeft();
                    int top = childView.getTop() - 50;
                    int right = childView.getRight();
                    int bottom = childView.getTop();
                    c.drawRect(left, top, right, bottom, mPaintDrawRect);
                    c.drawText("条目:" + item + ",adapterIndex = " + childAdapterIndex, left, top + TEXT_BASE_LINE, mPaintDrawText);
                }
            }
        }
    }
}
