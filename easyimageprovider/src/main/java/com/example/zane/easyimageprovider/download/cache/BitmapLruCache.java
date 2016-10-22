package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;

/**
 * Created by Zane on 16/5/6.
 * 管理LruCache,进行bitmap的内存缓存的类
 */
public final class BitmapLruCache implements ImageCache {

    private static final LeasedLruCache lruCache;

    static {
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        lruCache = new LeasedLruCache(cacheSize){
            @Override
            protected int sizeOf(String key, LeasedDrawable value) {
                Bitmap bitmap = value.getBitmap();
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public void put(String url, LeasedDrawable bitmap) {
        lruCache.put(url, bitmap);
    }

    @Override
    public LeasedDrawable get(String url, int width, int height) {
        return lruCache.get(url);
    }

    @Override
    public void remove(String url) {
        lruCache.remove(url);
    }
}
