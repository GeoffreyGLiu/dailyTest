package com.geoffrey.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.geoffrey.otherstest.R;

import java.util.ArrayList;

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
public class ViewFlipperFragment extends Fragment {

    private ViewFlipper vf_top_test, vf_foot_test;
    private LinearLayout ll_top_container;
    private final int FLIPPER_INTERVAL = 2000;
    private float density = 0;
    private final int dotSize = 5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_flipper, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initData();
        //填充ViewFlipper的top的图片
        initViewFlipperTop();
        //填充ViewFlipper的底部
        initViewFlipperBottom();

        //设置初始的dot,第一个
        ll_top_container.getChildAt(0).setBackgroundColor(getActivity().getResources().getColor(R.color.color_gray_green));
    }

    private void initViews() {
        if (getView() != null) {
            vf_top_test = getView().findViewById(R.id.vf_top_test);
            vf_foot_test = getView().findViewById(R.id.vf_foot_test);
            ll_top_container = getView().findViewById(R.id.ll_top_container);
        }
    }

    private void initData() {

        //获取设备的density
        if (getActivity() != null) {
            density = getActivity().getResources().getDisplayMetrics().density;
        }
    }

    private void initViewFlipperTop() {
        ArrayList<Integer> topImgList = new ArrayList<>();
        topImgList.add(R.mipmap.mipmap1);
        topImgList.add(R.mipmap.mipmap2);
        topImgList.add(R.mipmap.mipmap3);
        topImgList.add(R.mipmap.mipmap4);
        for (int i = 0; i < topImgList.size(); i++) {
            View viewTemp = getLayoutInflater().inflate(R.layout.item_viewfillper_top, null);
            ImageView imageView = viewTemp.findViewById(R.id.iv_top_test);
            if (getActivity() != null) {
                Glide.with(getActivity()).load(topImgList.get(i)).into(imageView);
            }
            vf_top_test.addView(viewTemp);
            initViewFlipperTopIndicator();

        }

        //初始化头部的ViewFlipper
        vf_top_test.setInAnimation(getActivity(), R.anim.anim_horizontal_in);
        vf_top_test.setOutAnimation(getActivity(), R.anim.anim_horizontal_out);
        vf_top_test.setFlipInterval(FLIPPER_INTERVAL);
        vf_top_test.startFlipping();

        //设置ViewFlipper的动画监听
        vf_top_test.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                int childCount = ll_top_container.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i == vf_top_test.getDisplayedChild()) {
                        final int finalI = i;
                        ll_top_container.post(new Runnable() {
                            @Override
                            public void run() {
                                ll_top_container.getChildAt(finalI).setBackgroundColor(getActivity().getResources().getColor(R.color.color_gray_green));
                            }
                        });
                    } else {
                        final int finalI = i;
                        ll_top_container.post(new Runnable() {
                            @Override
                            public void run() {
                                ll_top_container.getChildAt(finalI).setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
                            }
                        });
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initViewFlipperTopIndicator() {
        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.height = (int) (dotSize * density);
        params.width = (int) (dotSize * density);
        params.setMargins((int) (5 * density), 0, 0, 0);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
        ll_top_container.addView(textView);
    }

    private void initViewFlipperBottom() {
        ArrayList<String> bottomList = new ArrayList<>();
        bottomList.add("白虎");
        bottomList.add("朱雀");
        bottomList.add("玄武");
        bottomList.add("青龙");
        for (int i = 0; i < bottomList.size(); i++) {
            View viewTemp = getLayoutInflater().inflate(R.layout.item_viewfillper_foot, null);
            TextView textView = viewTemp.findViewById(R.id.tv_bottom_test);
            textView.setText(bottomList.get(i));
            vf_foot_test.addView(viewTemp);
        }

        //初始化底部的ViewFlipper
        vf_foot_test.setInAnimation(getActivity(), R.anim.anim_vertical_in);
        vf_foot_test.setOutAnimation(getActivity(), R.anim.anim_vertical_out);
        vf_foot_test.setFlipInterval(FLIPPER_INTERVAL);
        vf_foot_test.startFlipping();
    }

}
