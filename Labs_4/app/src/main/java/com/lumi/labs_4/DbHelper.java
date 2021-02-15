package com.lumi.labs_4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper implements DatabaseHelper<Song> {

    public DbHelper(@Nullable Context context) {
        super(context, "DB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE Songs (track_name text, uri_track text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    @Override
    public void addData(Song data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("track_name", data.getTrackName());
        contentValues.put("uri_track", data.getUriSong().toString());
        getWritableDatabase().insert("Songs", null, contentValues);
    }

    @Override
    public List<Song> getData() {
        List<Song> songs = new ArrayList<>();
        Cursor c = getReadableDatabase().query("Songs", null, null, null, null,null, null);
        if (c.moveToFirst()){
            int trackNameIndex = c.getColumnIndex("track_name");
            int uriNoParseIndex = c.getColumnIndex("uri_track");
            do {
                Song song = new Song(c.getString(trackNameIndex), Uri.parse(c.getString(uriNoParseIndex)));
                songs.add(song);
            }while (c.moveToNext());
        }
        c.close();
        return songs;
    }

    @Override
    public Song getTrackUriByName(String trackName) {
        return null;
    }

    @Override
    public void clearDatabase() {
        getWritableDatabase().delete("Songs", null, null);
    }
}
