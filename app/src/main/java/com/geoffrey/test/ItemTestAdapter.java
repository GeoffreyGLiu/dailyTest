package com.geoffrey.test;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ItemTestAdapter extends RecyclerView.Adapter<ItemTestAdapter.TestViewHolder> {

    private ArrayList<TestListBean> testArrayList;
    private TestListBean itemInfo;
    private int size;
    private int[] colorList;
    private int colorCurrent;
    private int colorListLengh;
    private Context context;

    public ItemTestAdapter(Context context){
        this.context = context;
        testArrayList = new ArrayList<>();
        colorList = new int[]{R.color.colorAccent,R.color.colorAccent1,R.color.colorAccent2,R.color.colorAccent3};
        colorListLengh = colorList.length;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_list,parent,false);
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, final int position) {
        size = testArrayList.size();
        itemInfo = testArrayList.get(position);
        colorCurrent = colorList[itemInfo.getType() % colorListLengh];
        holder.tv_item.setText(itemInfo.getValue());
        holder.tv_item.setBackgroundColor(context.getColor(colorCurrent));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != 0 && position < size) {
                    Collections.swap(testArrayList, 0, position);
                    notifyItemRangeChanged(0, position + 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return testArrayList.size();
    }

    public void addAll(ArrayList<TestListBean> list){
        testArrayList.addAll(list);
        notifyDataSetChanged();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_item;
        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}
