package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;

/**
 * Created by Zane on 16/9/23.
 * Email: zanebot96@gmail.com
 */
//不使用缓存

final public class BitmapNoCache implements ImageCache{

    @Override
    public void put(String url, LeasedDrawable bitmap) {
    }

    @Override
    public LeasedDrawable get(String url, int width, int height) {
        return null;
    }

    @Override
    public void remove(String url) {
    }
}
