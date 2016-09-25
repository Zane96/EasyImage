package com.example.zane.easyimageprovider.download.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

/**
 * Created by Zane on 16/5/6.
 * 管理LruCache,进行bitmap的内存缓存的类
 */
public final class BitmapLruCache implements ImageCache {

    private static LruCache<String, Bitmap> lruCache;

    static {
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(String url) {
        return lruCache.get(url);
    }

    @Override
    public void remove(String url) {
        lruCache.remove(url);
    }
}
