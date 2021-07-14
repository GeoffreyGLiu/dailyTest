package com.geoffrey.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class MyScrollView extends View {

    private Scroller mScroller;
    private int lastX, lastY;
    private int x, y;
    private int deltaX, deltaY;
    private int sum;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void smoothScrollTo(int dstX, int dstY) {
        int scrollX = getScrollX();
        int delta = dstX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 1000);
        invalidate();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = (int) event.getX();
//                lastY = (int) event.getY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                x = (int) event.getX();
//                y = (int) event.getY();
//                deltaX = x - lastX;
//                deltaY = y = lastY;
//                ((View) getParent()).scrollBy(-deltaX, -deltaY);
//                break;
//
//            case MotionEvent.ACTION_UP:
//                View viewGroup = (View) getParent();
//                mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY(), 500);
//                invalidate();//通知View进行重绘
//                break;
//        }
//        return true;
//    }
}
