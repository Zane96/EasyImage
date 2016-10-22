package com.example.zane.easyimageprovider.download.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;

/**
 * Created by Zane on 16/5/11.
 * 两种缓存
 */
public final class BitmapDoubleCache implements ImageCache{

    private final BitmapDiskCache diskLruCache;
    private final BitmapLruCache lruCache = new BitmapLruCache();


    public BitmapDoubleCache(Context context) {
        diskLruCache = BitmapDiskCache.getInstance(context);
    }

    @Override
    public void put(String url, LeasedDrawable bitmap) {
        diskLruCache.put(url, bitmap);
        lruCache.put(url, bitmap);
    }

    @Override
    public LeasedDrawable get(String url, int width, int height) {
        LeasedDrawable value = lruCache.get(url, width, height);
        if (value == null) {
            value = diskLruCache.get(url, width, height);
            saveBitmapIntoMemory(url, value);
        }
        return value;
    }

    private void saveBitmapIntoMemory(String key, LeasedDrawable drawable) {
        // 如果Value从disk中读取,那么存入内存缓存
        if (drawable != null) {
            lruCache.put(key, drawable);
        }
    }

    @Override
    public void remove(String url) {
        diskLruCache.remove(url);
        lruCache.remove(url);
    }
}
