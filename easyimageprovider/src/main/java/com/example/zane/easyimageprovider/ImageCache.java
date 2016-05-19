package com.example.zane.easyimageprovider;

import android.graphics.Bitmap;

/**
 * Created by Zane on 16/5/11.
 */
public interface ImageCache {
    void put(String url, Bitmap bitmap);
    Bitmap get(OnGetImageListener listener, String url);
    void remove(String url);
}
