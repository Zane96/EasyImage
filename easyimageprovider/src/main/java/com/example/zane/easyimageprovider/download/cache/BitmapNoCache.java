package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;

/**
 * Created by Zane on 16/9/23.
 * Email: zanebot96@gmail.com
 */
//不使用缓存

final public class BitmapNoCache implements ImageCache{

    @Override
    public void put(String url, Bitmap bitmap) {
    }

    @Override
    public Bitmap get(String url) {
        return null;
    }

    @Override
    public void remove(String url) {
    }
}
