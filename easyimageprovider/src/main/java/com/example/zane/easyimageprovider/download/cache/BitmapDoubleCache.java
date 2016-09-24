package com.example.zane.easyimageprovider.download.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

/**
 * Created by Zane on 16/5/11.
 * 两种缓存
 */
public final class BitmapDoubleCache implements ImageCache{

    private BitmapDiskCache diskLruCache;
    private BitmapLruCache lruCache = new BitmapLruCache();


    public BitmapDoubleCache(Context context) {
        diskLruCache = BitmapDiskCache.getInstance(context);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        diskLruCache.put(url, bitmap);
        lruCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(OnGetImageListener listener, String url) {
        Bitmap value = lruCache.get(listener, url);
        if (value == null) {
            value = diskLruCache.get(listener, url);
            saveBitmapIntoMemory(url, value);
        }
        return value;
    }

    private void saveBitmapIntoMemory(String key, Bitmap bitmap) {
        // 如果Value从disk中读取,那么存入内存缓存
        if (bitmap != null) {
            lruCache.put(key, bitmap);
        }
    }

    @Override
    public void remove(String url) {
        diskLruCache.remove(url);
        lruCache.remove(url);
    }
}
