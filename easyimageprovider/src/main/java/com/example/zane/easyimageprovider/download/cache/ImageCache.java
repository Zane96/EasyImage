package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;

/**
 * Created by Zane on 16/5/11.
 */
public interface ImageCache {
    void put(String url, LeasedDrawable drawable);
    LeasedDrawable get(String url, int width, int height);
    void remove(String url);
}
