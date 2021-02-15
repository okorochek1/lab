package com.lumi.secondlabs.model;

import java.io.Serializable;

public class Cat implements Serializable {
    private int mId;
    private int mUrlImgRes;
    private String mName;
    private String description;

    public Cat(int mId, int mUrlImgRes, String mName, String description) {
        this.mId = mId;
        this.mUrlImgRes = mUrlImgRes;
        this.mName = mName;
        this.description = description;
    }

    public int getId() {
        return mId;
    }

    public int getUrlImgRes() {
        return mUrlImgRes;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return description;
    }

}
