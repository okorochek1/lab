package com.lumi.secondlabs.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumi.secondlabs.model.Cat;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected TextView mName;
    protected TextView mDescription;
    protected ImageView mImage;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    protected abstract void bind(Cat cat, OnItemClickListener onItemClickListener);

}
