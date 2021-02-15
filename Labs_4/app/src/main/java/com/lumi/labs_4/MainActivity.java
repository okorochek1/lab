package com.lumi.labs_4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MusicPlayer, MusicClickListener {

    private final int REQUEST_CODE_MUSIC = 100;
    private final int READ_STORAGE_REQUEST_CODE = 101;
    
    
    private ImageButton mRewindFastBtn;
    private ImageButton mForwardFastBtn;
    private ImageButton mResetBtn;

    private RadioButton mStartRadioBtn;
    private RadioButton mPauseRadioBtn;

    private SeekBar mProgressPlay;
    private TextView mTrackName;
    private FloatingActionButton mAddTracksFabBtn;
    private Button mClearMusicListBtn;

    private RecyclerView mRecycler;
    private MusicAdapter mMusicAdapter;

    private MediaPlayer mMusicPlayer;

    private DatabaseHelper<Song> mDatabaseHelper;

    private Uri mCurrentTrack = null;

    private boolean isEnablePlayerButton = false;
    private Handler mThreadHandler = new Handler();

    private UpdateSeekBar mUpdateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callPermissionReadExternalStorage();
        initDataBase();
        initViews();
        setStateEnableButton();
        mUpdateSeekBar = new UpdateSeekBar(mMusicPlayer, mProgressPlay, mThreadHandler);
    }

    private void setStateEnableButton() {
        mRewindFastBtn.setEnabled(isEnablePlayerButton);
        mForwardFastBtn.setEnabled(isEnablePlayerButton);
        mResetBtn.setEnabled(isEnablePlayerButton);
        mStartRadioBtn.setEnabled(isEnablePlayerButton);
        mPauseRadioBtn.setEnabled(isEnablePlayerButton);
    }



    @Override
    public void startPlay() {
        int duration = mMusicPlayer.getDuration();
        int currentPosition = mMusicPlayer.getCurrentPosition();
        if (currentPosition == 0) {
            mProgressPlay.setMax(duration);
        }
        mMusicPlayer.start();
        mUpdateSeekBar.setMediaPlayer(mMusicPlayer);
        mThreadHandler.postDelayed(mUpdateSeekBar, 50);
    }

    @Override
    public void stopPlay() {
        if (mMusicPlayer != null)mMusicPlayer.pause();
    }

    @Override
    public void resetPlay() {
        mMusicPlayer.seekTo(0);
        startPlay();
    }

    @Override
    public void rewindPlay() {
        int currentPosition = mMusicPlayer.getCurrentPosition();
        int SUBTRACT_TIME = 10000;
        if (currentPosition - SUBTRACT_TIME > 0) {
            mMusicPlayer.seekTo(currentPosition - SUBTRACT_TIME);
        }

    }

    @Override
    public void forwardPlay() {
        int currentPosition = mMusicPlayer.getCurrentPosition();
        int duration = mMusicPlayer.getDuration();
        int ADD_TIME = 10000;
        if ((currentPosition + ADD_TIME) < duration) {
            mMusicPlayer.seekTo(currentPosition + ADD_TIME);
        }
    }

    @Override
    public void clickTrack(Song song) {
        //Выбор действующего трека
        if (mMusicPlayer != null) {
            mMusicPlayer.stop();
            mMusicPlayer.seekTo(0);
        }
        mCurrentTrack = song.getUriSong();
        mTrackName.setText(song.getTrackName());
        mMusicPlayer = MediaPlayer.create(this, mCurrentTrack);
        mStartRadioBtn.setChecked(true);
        isEnablePlayerButton = true;
        setStateEnableButton();
        startPlay();
    }

    private void addMusic() {
        //Вызов интента на добавления песни
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, REQUEST_CODE_MUSIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_MUSIC: {
                if (data != null && data.getData() != null){
                    Uri uriSong = data.getData();
                    String trackName = getRealPathFromURI(this, uriSong);
                    Song song = new Song(trackName, uriSong);
                    mDatabaseHelper.addData(song);
                    mMusicAdapter.addData(song);
                }
                break;
            }
            case READ_STORAGE_REQUEST_CODE: {
                if (resultCode != PackageManager.PERMISSION_GRANTED){
                    callPermissionReadExternalStorage();
                }
                break;
            }
        }
    }

    private void clearList() {

        mDatabaseHelper.clearDatabase();
        mMusicAdapter.clearData();
        if (mMusicPlayer != null){
            mMusicPlayer.seekTo(0);
            mMusicPlayer.stop();
        }
        mMusicPlayer = null;
        mTrackName.setText(R.string.currentSong_tv_mainActivity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rewindFastBut: {
                rewindPlay();
                break;
            } case R.id.forwardFastBut: {
                forwardPlay();
                break;
            } case R.id.play_radioBtn: {
                startPlay();
                break;
            } case R.id.resetBut: {
                resetPlay();
                break;
            } case R.id.pause_radioBtn: {
                stopPlay();
                break;
            } case R.id.addMusic_fabBtn: {
                addMusic();
                break;
            } case R.id.clearList_btn: {
                clearList();
                break;
            }
        }
    }

    private void initDataBase() {
        mDatabaseHelper = new DbHelper(this);
    }

    private void initViews() {
        mRewindFastBtn = findViewById(R.id.rewindFastBut);
        mRewindFastBtn.setOnClickListener(this);

        mForwardFastBtn = findViewById(R.id.forwardFastBut);
        mForwardFastBtn.setOnClickListener(this);

        mResetBtn = findViewById(R.id.resetBut);
        mResetBtn.setOnClickListener(this);

        mStartRadioBtn = findViewById(R.id.play_radioBtn);
        mStartRadioBtn.setOnClickListener(this);

        mPauseRadioBtn = findViewById(R.id.pause_radioBtn);
        mPauseRadioBtn.setOnClickListener(this);

        mProgressPlay = findViewById(R.id.progressControl);
        mRecycler = findViewById(R.id.recyclerView);

        mTrackName = findViewById(R.id.currentTrack_textView);

        mAddTracksFabBtn = findViewById(R.id.addMusic_fabBtn);
        mAddTracksFabBtn.setOnClickListener(this);

        mClearMusicListBtn = findViewById(R.id.clearList_btn);
        mClearMusicListBtn.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMusicAdapter = new MusicAdapter(mDatabaseHelper.getData(), this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mMusicAdapter);
    }

    private void callPermissionReadExternalStorage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_REQUEST_CODE);
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String fileName = cursor.getString(column_index);
            int pos = fileName.lastIndexOf("/");
            int dot = fileName.lastIndexOf(".");
            if (pos > 0) return fileName.substring(pos + 1, dot);
            else return fileName;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static class UpdateSeekBar implements Runnable {

        private MediaPlayer mediaPlayer;
        private SeekBar seekBar;
        private Handler threadHandler;

        public UpdateSeekBar(MediaPlayer mediaPlayer, SeekBar seekBar, Handler threadHandler) {
            this.mediaPlayer = mediaPlayer;
            this.seekBar = seekBar;
            this.threadHandler = threadHandler;
        }

        public void setMediaPlayer(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }

        @Override
        public void run() {
            if (mediaPlayer != null){
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                threadHandler.postDelayed(this, 50);
            }
        }
    }
}