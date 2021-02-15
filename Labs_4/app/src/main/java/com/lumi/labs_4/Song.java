package com.lumi.labs_4;

import android.net.Uri;

public class Song {

    private String mTrackName;

    private Uri mUriSong;

    public Song(String mTrackName, Uri mUriSong) {
        this.mTrackName = mTrackName;
        this.mUriSong = mUriSong;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String mTrackName) {
        this.mTrackName = mTrackName;
    }

    public Uri getUriSong() {
        return mUriSong;
    }

    public void setUriSong(Uri mUriSong) {
        this.mUriSong = mUriSong;
    }
}
