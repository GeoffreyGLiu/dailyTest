package com.geoffrey.test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class FramelayoutTest extends FrameLayout {
    public FramelayoutTest(@NonNull Context context) {
        super(context);
    }

    public FramelayoutTest(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FramelayoutTest(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FramelayoutTest(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
