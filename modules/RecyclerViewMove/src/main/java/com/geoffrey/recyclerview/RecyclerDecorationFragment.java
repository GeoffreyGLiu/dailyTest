package com.geoffrey.recyclerview;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geoffrey.otherstest.R;

import java.util.ArrayList;
import java.util.List;

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
public class RecyclerDecorationFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rv_decoration_move;
    private EditText et_input_decoration;
    private Button btn_decoration_edage, btn_decoration_noedage;
    private ItemDecorationAdapter itemDecorationAdapter;
    private final int spanCount = 3;
    private final int itemCount = 15;
    private GridItemDecoration gridItemDecoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_decoration, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        if (getView() == null) {
            return;
        }
        rv_decoration_move = getView().findViewById(R.id.rv_decoration_move);
        et_input_decoration = getView().findViewById(R.id.et_input_decoration);
        btn_decoration_noedage = getView().findViewById(R.id.btn_decoration_noedage);
        btn_decoration_edage = getView().findViewById(R.id.btn_decoration_edage);
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            list.add("标题" + i);
        }
        rv_decoration_move.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        gridItemDecoration = new GridItemDecoration(getActivity(),spanCount, 20, 20, true);
        rv_decoration_move.addItemDecoration(gridItemDecoration);
        itemDecorationAdapter = new ItemDecorationAdapter(getActivity(), list);
        rv_decoration_move.setAdapter(itemDecorationAdapter);
    }

    private void initListener() {
        btn_decoration_noedage.setOnClickListener(this);
        btn_decoration_edage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_decoration_noedage) {
            //无边界的ItemDecoration
            showWithoutEdage();
        } else if (id == R.id.btn_decoration_edage) {
            //带边界的ItemDecoration
            showWithEdage();
        }
    }

    private void showWithoutEdage() {
        CharSequence charSequence = et_input_decoration.getText();
        if (!TextUtils.isEmpty(charSequence)) {
            String[] strings = charSequence.toString().split(",");
            if (strings.length >= 2) {
                int horizontal = Integer.parseInt(strings[0]);
                int vertical = Integer.parseInt(strings[1]);
                gridItemDecoration.setHorizontalSpacing(horizontal);
                gridItemDecoration.setVerticalSpacing(vertical);
                gridItemDecoration.setIncludeEdage(false);
                rv_decoration_move.invalidateItemDecorations();
            }
        }
    }

    private void showWithEdage() {
        CharSequence charSequence = et_input_decoration.getText();
        if (!TextUtils.isEmpty(charSequence)) {
            String[] strings = charSequence.toString().split(",");
            if (strings.length >= 2) {
                int horizontal = Integer.parseInt(strings[0]);
                int vertical = Integer.parseInt(strings[1]);
                gridItemDecoration.setHorizontalSpacing(horizontal);
                gridItemDecoration.setVerticalSpacing(vertical);
                gridItemDecoration.setIncludeEdage(true);
                rv_decoration_move.invalidateItemDecorations();
            }
        }
    }
}
