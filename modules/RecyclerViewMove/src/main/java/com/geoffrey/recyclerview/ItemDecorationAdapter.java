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
import java.util.List;

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
public class ItemDecorationAdapter extends RecyclerView.Adapter<ItemDecorationAdapter.DecorationViewHolder> {

    private Context context;
    private List<String> list;

    public ItemDecorationAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DecorationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_decoration_test, parent, false);
        return new DecorationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DecorationViewHolder holder, int position) {
        if (list != null && list.size() > position) {
            holder.tv_title_decoration.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (list != null && list.size() > 0) ? list.size() : 0;
    }

    public static class DecorationViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title_decoration;

        public DecorationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title_decoration = itemView.findViewById(R.id.tv_title_decoration);
        }
    }
}
