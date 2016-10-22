package com.example.zane.easyimageprovider.download.cache;

import android.util.LruCache;

import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;


/**
 * Created by Zane on 16/10/22.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class LeasedLruCache extends LruCache<String, LeasedDrawable>{

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LeasedLruCache(int maxSize) {
        super(maxSize);
    }

    /**
     * 只有当evicted为true的时候,才是在释放内存。因为调用recycle()
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, LeasedDrawable oldValue, LeasedDrawable newValue) {
        if (evicted){
            oldValue.recycle();
        }
    }
}
