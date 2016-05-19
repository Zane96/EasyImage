package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.ImageCache;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.jakewharton.disklrucache.DiskLruCache;

/**
 * Created by Zane on 16/5/11.
 * 两种缓存
 */
public final class BitmapDoubleCache implements ImageCache{

    private DiskLruCache diskLruCache;

    @Override
    public void put(String url, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(OnGetImageListener listener, String url) {
        return null;
    }

}
