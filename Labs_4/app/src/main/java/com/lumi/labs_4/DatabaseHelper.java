package com.lumi.labs_4;

import java.util.List;

public interface DatabaseHelper<T> {

    void addData(T data);

    List<T> getData();

    T getTrackUriByName(String trackName);

    void clearDatabase();
}
