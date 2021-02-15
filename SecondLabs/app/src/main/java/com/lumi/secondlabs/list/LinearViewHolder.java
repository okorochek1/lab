package com.lumi.secondlabs.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumi.secondlabs.R;
import com.lumi.secondlabs.model.Cat;

public class LinearViewHolder extends BaseViewHolder {

    public LinearViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear, parent, false));

        mName = itemView.findViewById(R.id.catName_linear_textView);
        mDescription = itemView.findViewById(R.id.catDescription_linear_textView);
        mImage = itemView.findViewById(R.id.catImg_linear_imgView);
    }

    @Override
    protected void bind(Cat cat, OnItemClickListener onItemClickListener) {
        mName.setText(cat.getName());
        mDescription.setText(cat.getDescription());
        mImage.setImageResource(cat.getUrlImgRes());

        itemView.setOnClickListener(v-> onItemClickListener.clickItem(cat));
    }
}
