package com.lumi.labs_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicViewHolder> {

    private List<Song> mSongList = new ArrayList<>();
    private MusicClickListener mOnClickListener;

    public MusicAdapter(List<Song> mSongList, MusicClickListener mOnClickListener) {
        this.mSongList = mSongList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new MusicViewHolder(inflater.inflate(R.layout.item_track, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.bind(mSongList.get(position), mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mSongList == null ? 0 : mSongList.size();
    }

    public void addData(List<Song> songList){
        mSongList.addAll(songList);
        notifyDataSetChanged();
    }

    public void clearData(){
        mSongList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addData(Song song){
        mSongList.add(song);
        notifyDataSetChanged();
    }

}

