package com.geoffrey.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

class ViewTest extends ViewGroup {

    public ViewTest(Context context) {
        super(context);
    }

    public ViewTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
