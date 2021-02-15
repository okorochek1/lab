package com.lumi.labs_4;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicViewHolder extends RecyclerView.ViewHolder {

    private TextView mTrackName;

    public MusicViewHolder(@NonNull View itemView) {
        super(itemView);
        mTrackName = itemView.findViewById(R.id.trackName_textView);
    }

    public void bind(Song song, MusicClickListener onClickListener){
        mTrackName.setText(song.getTrackName());
        itemView.setOnClickListener(view -> {
            onClickListener.clickTrack(song);
        });
    }
}
