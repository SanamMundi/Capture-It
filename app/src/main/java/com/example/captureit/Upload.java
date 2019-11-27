package com.example.captureit;

import android.text.style.UpdateLayout;

public class Upload {

    private String mName;
    private String mImageUrl;

    public Upload(){}


    public Upload(String name, String imageUrl){
        if(name.trim().equals("")){
            name = "noName";
        }

        mName = name;
        mImageUrl = imageUrl;

    }


    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
