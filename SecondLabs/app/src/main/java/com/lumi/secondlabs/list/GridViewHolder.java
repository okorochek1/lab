package com.lumi.secondlabs.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import com.lumi.secondlabs.R;
import com.lumi.secondlabs.model.Cat;

public class GridViewHolder extends BaseViewHolder {


    public GridViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false));
        mName = itemView.findViewById(R.id.catName_grid_textView);
        mDescription = itemView.findViewById(R.id.catDescription_grid_textView);
        mImage = itemView.findViewById(R.id.catImg_grid_imgView);
    }

    @Override
    protected void bind(Cat cat, OnItemClickListener onItemClickListener) {
        mName.setText(cat.getName());
        mDescription.setText(cat.getDescription());
        mImage.setImageResource(cat.getUrlImgRes());

        itemView.setOnClickListener(v-> onItemClickListener.clickItem(cat));
    }

}
