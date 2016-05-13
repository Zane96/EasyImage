package com.example.zane.easyimageprovider.builder;

import android.content.Context;

import com.example.zane.easyimageprovider.ImageCache;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.download.BitmapDiskCache;
import com.example.zane.easyimageprovider.download.BitmapDoubleCache;
import com.example.zane.easyimageprovider.download.BitmapLruCache;

/**
 * Created by Zane on 16/5/11.
 * 主体类对于load功能的构建类。开放修改和扩展
 */
public class ImageLoadBuidler {

    //是否使用Lru缓存
    boolean isLruCache = false;
    //是否使用Disk缓存
    boolean isDiskCache = false;
    //是否三级缓存
    boolean isDoubleCache = false;
    //是否是开发者自定义
    boolean isCustom = false;

    //缓存抽象
    ImageCache imageCache;
    //回调抽象
    OnGetImageListener listener;

    Context context;

    public ImageLoadBuidler(){

    }

    //-----------------------------------------调用链------------------------------------------

    /**
     * LruCache
     * @param isLruCache
     * @return
     */
    public ImageLoadBuidler useLruCache(boolean isLruCache){
        this.isLruCache = isLruCache;
        imageCache = new BitmapLruCache();

        return this;
    }

    /**
     * Lru+Disk
     * @param isDoubleCache
     * @return
     */
    public ImageLoadBuidler useDoubleCache(boolean isDoubleCache){
        this.isDoubleCache = isDoubleCache;
        imageCache = new BitmapDoubleCache();

        return this;
    }

    /**
     * DiskCache
     * @param isDiskCache
     * @return
     */
    public ImageLoadBuidler useDiskCache(boolean isDiskCache){
        this.isDiskCache = isDiskCache;
        imageCache = new BitmapDiskCache();

        return this;
    }

    /**
     * custom cache
     * @param imageCache
     * @return
     */
    public ImageLoadBuidler useCustomCache(ImageCache imageCache){
        if (! (imageCache instanceof ImageCache)){
            throw new IllegalArgumentException("your custom cache must implement ImageCache");
        }
        this.imageCache = imageCache;
        isCustom = true;

        return this;
    }

    public ImageLoadBuidler getBitmapBack(OnGetBitmapListener listener){
        this.listener = listener;
        return this;
    }

    public ImageLoadBuidler with(Context context){
        this.context = context;
        return this;
    }


    //----------------------------------------分割线-------------------------------------------

    public EasyImageProvider builder(){
        return new EasyImageProvider(this);
    }

}
