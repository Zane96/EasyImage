package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;

/**
 * Created by Zane on 16/5/11.
 */
public interface ImageCache {
    void put(String url, Bitmap bitmap);
    Bitmap get(String url);
    void remove(String url);
}
