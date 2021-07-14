package com.geoffrey.test.fragments;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geoffrey.test.R;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：2/7/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class FragmentTestActivity extends FragmentActivity implements View.OnClickListener {

    public static final String FRAGMENT_TEST = "fragment_test";

    private TextView show_fragment,hide_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        initView();
    }

    private void initView() {
        show_fragment = findViewById(R.id.show_fragment);
        hide_fragment = findViewById(R.id.hide_fragment);

        show_fragment.setOnClickListener(this);
        hide_fragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.show_fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TEST);
            if (fragment != null){
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.show(fragment);
                ft.commitAllowingStateLoss();
            }else{
                FragmentTransaction ftElse = fragmentManager.beginTransaction();
                TestFragment testFragment = TestFragment.newInstance();
                ftElse.add(R.id.fl,testFragment,FRAGMENT_TEST);
                ftElse.commit();
            }
        }else if (id == R.id.hide_fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TEST);
            if (fragment != null){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(fragment);
                fragmentTransaction.commit();
            }
        }
    }
}
