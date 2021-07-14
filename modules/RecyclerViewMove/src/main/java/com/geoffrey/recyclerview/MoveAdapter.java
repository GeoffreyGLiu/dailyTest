package com.geoffrey.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geoffrey.otherstest.R;

import java.util.ArrayList;

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
public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MoveViewHolder> {

    private ArrayList<MoveTestBean> list;
    private Context context;

    public MoveAdapter(Context context, ArrayList<MoveTestBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_move_test, parent, false);
        return new MoveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoveViewHolder holder, int position) {
        if (list == null || list.size() <= 0 || position >= list.size()) {
            return;
        }
        holder.tv_title_move.setText(list.get(position).getTitle());
        holder.tv_content_move.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list == null || list.size() <= 0 ? 0 : list.size();
    }

    public class MoveViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title_move, tv_content_move;

        public MoveViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title_move = itemView.findViewById(R.id.tv_title_move);
            tv_content_move = itemView.findViewById(R.id.tv_content_move);
        }
    }
}
