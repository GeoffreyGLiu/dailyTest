package com.geoffrey.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
public class ItemSelectAdapter extends RecyclerView.Adapter<ItemSelectAdapter.ItemSelectViewHolder>{

    private ArrayList<ItemSelectBean> itemSelectList;
    private ItemSelectBean selectItemSelectBean = null;
    private Context context;

    public ItemSelectAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ItemSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemSelectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_select, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemSelectViewHolder holder, final int position) {

        if (itemSelectList != null && position < itemSelectList.size()){
            final ItemSelectBean itemSelectBean = itemSelectList.get(position);
            holder.tv_item_select.setText(itemSelectBean.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    clickToChoose(itemSelectBean);
                }
            });
            boolean isSelect = selectItemSelectBean != null && selectItemSelectBean.equals(itemSelectBean);
            holder.tv_item_select.setBackgroundColor(context.getColor(isSelect?R.color.colorAccent3:R.color.color_white));
        }
    }

    private void clickToChoose(ItemSelectBean itemSelectBean) {

        if (selectItemSelectBean != null && selectItemSelectBean.equals(itemSelectBean)){
            int index0 = itemSelectList.indexOf(selectItemSelectBean);
            selectItemSelectBean = null;
            notifyItemChanged(index0);
            return;
        }

        ItemSelectBean lastItemSelectBean = selectItemSelectBean;
        selectItemSelectBean = itemSelectBean;
        if (lastItemSelectBean != null){
            int index = itemSelectList.indexOf(lastItemSelectBean);
            if (index >= 0){
                notifyItemChanged(index);
            }
        }
        if (selectItemSelectBean != null){
            int index1 = itemSelectList.indexOf(selectItemSelectBean);
            if (index1 >= 0){
                notifyItemChanged(index1);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (itemSelectList == null || itemSelectList.size() <= 0){
            return 0;
        }
        return itemSelectList.size();
    }

    public void addAll(ArrayList<ItemSelectBean> list){
        itemSelectList = list;
        notifyDataSetChanged();
    }

    static class ItemSelectViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_item_select;
        public ItemSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            tv_item_select = itemView.findViewById(R.id.tv_item_select);
        }
    }
}
