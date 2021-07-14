package com.geoffrey.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.geoffrey.test.view.MyScrollView;

public class ThirdActivity extends AppCompatActivity {
    public static final String TAG = "ThirdActivity";
    TextView tv_third;
    float density, offset = 50;
    Scroller mScroller;
    ConstraintLayout cl_third;
    float dest = -density * 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        tv_third = findViewById(R.id.tv_third);
        cl_third = findViewById(R.id.cl_third);
        tv_third.setText("third");
        density = getResources().getDisplayMetrics().density;
        mScroller = new Scroller(this);

        final MyScrollView myScrollView = new MyScrollView(ThirdActivity.this);
        myScrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        final float density = getResources().getDisplayMetrics().density;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (density * 80), (int) (density * 60));
        myScrollView.setLayoutParams(layoutParams);
        cl_third.addView(myScrollView);
        myScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dest = -density * 200;
                myScrollView.smoothScrollTo((int) dest, (int) dest);
            }
        });

        tv_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //布局参数margin
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv_third.getLayoutParams();
//                params.leftMargin += 100 * density;
//                tv_third.setLayoutParams(params);

                //scrollBy
//                ((View) tv_third.getParent()).scrollBy((int) (-offset * density), (int) (-offset * density));
                //移动View的内容
//                tv_third.scrollBy((int)(-5 * density), (int)(-5 * density));

                //offsetLeftAndRight和offsetTopAndBottom
//                tv_third.offsetTopAndBottom((int)(50*density));
//                tv_third.offsetLeftAndRight((int)(50*density));

                //layout
//                tv_third.layout((int) (tv_third.getLeft() + offset * density), (int) (tv_third.getTop() + offset * density), (int) (tv_third.getRight() + offset * density), (int) (tv_third.getBottom() + offset * density));
            }
        });

    }


}