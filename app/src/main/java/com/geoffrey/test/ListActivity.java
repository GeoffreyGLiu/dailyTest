package com.geoffrey.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：1/29/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class ListActivity extends AppCompatActivity {

    private final int LIST_SIZE = 6;
    private RecyclerView rv_list;
    private ItemSelectAdapter itemSelectAdapter;
    private ArrayList<ItemSelectBean> stringArrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initData();
    }

    private void initView() {
        rv_list = findViewById(R.id.rv_list);
    }

    private void initData() {
        stringArrayList = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            stringArrayList.add(new ItemSelectBean("标题" + i));
        }
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        itemSelectAdapter = new ItemSelectAdapter(this);
        rv_list.setAdapter(itemSelectAdapter);
        itemSelectAdapter.addAll(stringArrayList);
    }
}
