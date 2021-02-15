package com.lumi.secondlabs.list;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lumi.secondlabs.model.Cat;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final static int COUNT_LINEAR = 1;
    //Todo подлежит удалению?
    private final static int COUNT_GRID = 3;

    private ArrayList<Cat> mList;
    private GridLayoutManager mLayoutManger;
    private OnItemClickListener mOnItemClickListener;

    public CatAdapter(ArrayList<Cat> mList, GridLayoutManager mLayoutManger, OnItemClickListener mOnItemClickListener) {
        this.mList = mList;
        this.mLayoutManger = mLayoutManger;
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ViewType.TYPE_LINEAR) return new LinearViewHolder(parent);
        else return new GridViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mList.get(position), mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mLayoutManger.getSpanCount() == COUNT_LINEAR) return ViewType.TYPE_LINEAR;
        else return ViewType.TYPE_GRID;
    }


    private static class ViewType {
        public static final int TYPE_GRID = 1;
        public static final int TYPE_LINEAR = 0;
    }
}
