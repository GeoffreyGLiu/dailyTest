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
public class RecyclerMoveFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "RecyclerMoveFragment";
    private RecyclerView rv_move_other;
    private EditText et_input_other;
    private Button btn_move_other;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_move, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        if (getView() == null) {
            return;
        }
        rv_move_other = getView().findViewById(R.id.rv_move_other);
        et_input_other = getView().findViewById(R.id.et_input_other);
        btn_move_other = getView().findViewById(R.id.btn_move_other);
    }

    private void initData() {

        ArrayList<MoveTestBean> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            list.add(new MoveTestBean("标题 " + i, "content " + i));
        }
        rv_move_other.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_move_other.setAdapter(new MoveAdapter(getActivity(), list));
    }

    private void initListener() {
        btn_move_other.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_move_other) {
            //移动点击事件处理
//            moveToPosition();
            moveToPositionNew();
        }
    }

    private void moveToPosition() {
        //要移动的索引,可见item的顶部位置,item可见的底部位置
        int index, firstPosition, lastPosition;
        CharSequence input = et_input_other.getText();
        if (input != null && input.length() > 0) {

            index = Integer.parseInt(input.toString());
            firstPosition = rv_move_other.getChildLayoutPosition(rv_move_other.getChildAt(0));
            lastPosition = rv_move_other.getChildLayoutPosition(rv_move_other.getChildAt(rv_move_other.getChildCount() - 1));

            if (index < firstPosition || index > lastPosition) {
                rv_move_other.smoothScrollToPosition(index);
            } else {
                int movePosition = index - firstPosition;
                int moveDistance = rv_move_other.getChildAt(movePosition).getTop();
                rv_move_other.scrollBy(0, moveDistance);
            }
            Log.e(TAG, "firstPosition = " + firstPosition);
            Log.e(TAG, "lastPosition = " + lastPosition);
            Log.e(TAG, "index = " + index);
        }
    }

    private void moveToPositionNew() {
        //要移动的索引,可见item的顶部位置,item可见的底部位置
        int index, firstPosition, lastPosition;
        CharSequence input = et_input_other.getText();
        if (input != null && input.length() > 0) {

            index = Integer.parseInt(input.toString());
            RecyclerView.LayoutManager layoutManager = rv_move_other.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (index < firstPosition || index > lastPosition) {
                    rv_move_other.smoothScrollToPosition(index);
                } else {
                    int movePosition = index - firstPosition;
                    int moveDistance = rv_move_other.getChildAt(movePosition).getTop();
                    rv_move_other.scrollBy(0, moveDistance);
                }
                Log.e(TAG, "firstPosition = " + firstPosition);
                Log.e(TAG, "lastPosition = " + lastPosition);
                Log.e(TAG, "index = " + index);
            }
        }
    }
}
