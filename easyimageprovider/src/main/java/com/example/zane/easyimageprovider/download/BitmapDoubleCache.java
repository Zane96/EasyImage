package com.example.zane.easyimageprovider.download;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.ImageCache;

/**
 * Created by Zane on 16/5/11.
 * 两种缓存
 */
public class BitmapDoubleCache implements ImageCache{

    @Override
    public void put(String url, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(String url) {
        return null;
    }
}
