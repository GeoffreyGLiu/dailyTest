package com.geoffrey.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geoffrey.otherstest.R;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/8/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class RecyclerTestActivity extends FragmentActivity implements View.OnClickListener {

    private final String TAG = "RecyclerMoveActivity";
    private Button btn_move_test,btn_decoration_test,btn_sticky_head;
    private Button btn_flipper_test,btn_empty_one,btn_empty_two;
    private FrameLayout fl_container;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        btn_move_test = findViewById(R.id.btn_move_test);
        btn_decoration_test = findViewById(R.id.btn_decoration_test);
        btn_sticky_head = findViewById(R.id.btn_sticky_head);
        btn_flipper_test = findViewById(R.id.btn_flipper_test);
        btn_empty_one = findViewById(R.id.btn_empty_one);
        btn_empty_two = findViewById(R.id.btn_empty_two);
        fl_container = findViewById(R.id.fl_container);
    }

    private void initListener() {
        btn_move_test.setOnClickListener(this);
        btn_decoration_test.setOnClickListener(this);
        btn_sticky_head.setOnClickListener(this);
        btn_flipper_test.setOnClickListener(this);
        btn_empty_one.setOnClickListener(this);
        btn_empty_two.setOnClickListener(this);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_move_test){
            //move test
           loadRecyclerMove();
        }else if (id == R.id.btn_decoration_test){
            //item decoration
            loadItemDecoration();
        }else if (id == R.id.btn_sticky_head){
            //stickyHead
            loadItemStickyHead();
        }else if (id == R.id.btn_flipper_test){
            //flipper
            loadFlipperTest();
        }else if (id == R.id.btn_empty_one){
            //广告位招租1
            Toast.makeText(this,"广告位招租1",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.btn_empty_two){
            //广告位招租2
            Toast.makeText(this,"广告位招租2",Toast.LENGTH_SHORT).show();
        }
    }


    //移动item到指定的位置
    private void loadRecyclerMove() {

        RecyclerMoveFragment moveFragment = null;
        Fragment fragment = fragmentManager.findFragmentByTag(RecyclerMoveFragment.class.getSimpleName());
        if (fragment instanceof RecyclerMoveFragment){
            moveFragment = (RecyclerMoveFragment) fragment;
        }else{
            moveFragment = new RecyclerMoveFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container,moveFragment,RecyclerMoveFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    //添加RecyclerView的divider
    private void loadItemDecoration() {
        RecyclerDecorationFragment itemFragment = null;
        Fragment fragment = fragmentManager.findFragmentByTag(RecyclerDecorationFragment.class.getSimpleName());
        if (fragment instanceof RecyclerDecorationFragment){
            itemFragment = (RecyclerDecorationFragment) fragment;
        }else{
            itemFragment = new RecyclerDecorationFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container,itemFragment,RecyclerDecorationFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    //加载粘性头部
    private void loadItemStickyHead() {

        RecyclerStickyHeadFragment itemFragment = null;
        Fragment fragment = fragmentManager.findFragmentByTag(RecyclerStickyHeadFragment.class.getSimpleName());
        if (fragment instanceof RecyclerStickyHeadFragment){
            itemFragment = (RecyclerStickyHeadFragment) fragment;
        }else{
            itemFragment = new RecyclerStickyHeadFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container,itemFragment,RecyclerStickyHeadFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
    
    //加载ViewFlipper
    private void loadFlipperTest() {
        ViewFlipperFragment itemFragment = null;
        Fragment fragment = fragmentManager.findFragmentByTag(ViewFlipperFragment.class.getSimpleName());
        if (fragment instanceof ViewFlipperFragment){
            itemFragment = (ViewFlipperFragment) fragment;
        }else{
            itemFragment = new ViewFlipperFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container,itemFragment,ViewFlipperFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
