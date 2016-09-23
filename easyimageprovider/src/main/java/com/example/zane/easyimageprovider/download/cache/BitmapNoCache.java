package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

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
    public Bitmap get(OnGetImageListener listener, String url) {
        return null;
    }

    @Override
    public void remove(String url) {
    }
}
