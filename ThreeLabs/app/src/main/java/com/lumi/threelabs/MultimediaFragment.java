package com.lumi.threelabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MultimediaFragment extends Fragment implements View.OnClickListener {

    private final int REQUEST_CODE_VIDEO = 41;
    private final int REQUEST_CODE_MUSIC = 39;
    private FloatingActionButton mFBtn;
    private VideoView mVideo;
    private ImageView mMusicImage;
    private Button mStartPlayBtn;
    private Button mStopPlayBtn;

    private MediaPlayer mMedialPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multimedia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mFBtn = view.findViewById(R.id.fab_addMultimedia);
        mFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogWindowSelected();
            }
        });
        mVideo = view.findViewById(R.id.video);
        mMusicImage = view.findViewById(R.id.imageMusic);


        mStartPlayBtn = view.findViewById(R.id.startPlay_btn);
        mStartPlayBtn.setOnClickListener(this);
        mStopPlayBtn = view.findViewById(R.id.stopPlay_btn);
        mStopPlayBtn.setOnClickListener(this);
        disableMediaBtn();
    }

    private void disableMediaBtn(){
        mStopPlayBtn.setEnabled(false);
        mStartPlayBtn.setEnabled(false);
    }

    private void openDialogWindowSelected() {
        new AlertDialog.Builder(requireActivity()).setTitle("Что вы хотите добавить?")
                .setPositiveButton("Видео", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Вызов неявного интента на доступ к хранилищу для получение URL. Видео
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("video/*");
                        startActivityForResult(intent, REQUEST_CODE_VIDEO);
                    }
                }).setNegativeButton("Аудио", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Вызов неявного интента для доступа хранилищу для получения URL. Аудио
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("audio/*");
                        startActivityForResult(intent, REQUEST_CODE_MUSIC);
                    }
        }).create().show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startPlay_btn: {
                startPlayer();
                break;
            } case R.id.stopPlay_btn: {
                stopPlayer();
                break;
            }
        }
    }

    private void startPlayer() {
        mMedialPlayer.start();
        mStartPlayBtn.setEnabled(false);
        mStopPlayBtn.setEnabled(true);
    }

    private void stopPlayer() {
        mMedialPlayer.stop();
        mStopPlayBtn.setEnabled(false);
        try {
            mMedialPlayer.prepare();
            mMedialPlayer.seekTo(0);
            mStartPlayBtn.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_CODE_MUSIC: {
                if (data != null && data.getData() != null){
                    mVideo.setVisibility(View.GONE);
                    mMusicImage.setVisibility(View.VISIBLE);
                    mMedialPlayer = MediaPlayer.create(requireActivity(), data.getData());
                    mMedialPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            stopPlayer();
                        }
                    });
                    mStartPlayBtn.setEnabled(false);
                    mStopPlayBtn.setEnabled(true);
                }
                break;
            }
            case REQUEST_CODE_VIDEO: {
                if (data != null && data.getData() != null) {
                    disableMediaBtn();
                    mVideo.setVisibility(View.VISIBLE);
                    mMusicImage.setVisibility(View.GONE);
                    mVideo.setVideoURI(data.getData());
                    mVideo.start();
                }
                break;
            }
        }
    }
}