package com.geoffrey.recyclerview;

import android.app.WallpaperColors;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geoffrey.otherstest.R;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/9/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int spanCount, horizontalSpacing, verticalSpacing;
    private boolean includeEdage;
    private Paint mPaint;
    private View child;
    private int left,top,right,bottom;
    private final int dividerHeight = 10;

    public GridItemDecoration(Context context,int spanCount, int horizontalSpacing, int verticalSpacing, boolean includeEdage) {
        this.context = context;
        this.spanCount = spanCount;
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.includeEdage = includeEdage;
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.colorAccent3));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int row = position % spanCount;
        if (includeEdage) {
            outRect.left = (int) (horizontalSpacing - horizontalSpacing * row / spanCount);
            outRect.right = (int) (horizontalSpacing * (row + 1) / spanCount);
            if (position < spanCount) {
                outRect.top = (int) verticalSpacing;
            }
            //如果不加divider,底部宽度不计算dividerHeight
            outRect.bottom = (int) verticalSpacing + dividerHeight;
        } else {
            outRect.left = (int) (row * horizontalSpacing) / spanCount;
            outRect.right = (int) (horizontalSpacing - (row + 1) * horizontalSpacing / spanCount);
            if (position >= spanCount) {
                //如果不加divider,底部宽度不计算dividerHeight
                outRect.top = (int) verticalSpacing + dividerHeight;
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        addDivider(c,parent);
    }

    private void addDivider(Canvas c,RecyclerView parent) {

        int childCount = parent.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++){
                child = parent.getChildAt(i);
                left = child.getLeft();
                top = child.getBottom();
                right = child.getRight();
                bottom = top + dividerHeight;
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public void setIncludeEdage(boolean includeEdage) {
        this.includeEdage = includeEdage;
    }
}
