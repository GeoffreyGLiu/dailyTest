package com.geoffrey.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geoffrey.otherstest.R;

import java.util.ArrayList;

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
public class RecyclerStickyHeadFragment extends Fragment {

    private final String TAG = "RecyclerStickyHeadFragment";
    private RecyclerView rv_sticky_head;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_stickyhead, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        if (getView() == null) {
            return;
        }
        rv_sticky_head = getView().findViewById(R.id.rv_sticky_head);
    }

    private void initData() {

        ArrayList<MoveTestBean> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            list.add(new MoveTestBean("标题 " + i, "content " + i));
        }
        rv_sticky_head.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_sticky_head.addItemDecoration(new StickyHeadItemDecoration(getActivity()));
        //复用item,move到特定位置的adapter
        rv_sticky_head.setAdapter(new MoveAdapter(getActivity(), list));
    }

}
