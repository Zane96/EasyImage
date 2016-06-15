package com.example.zane.easyimageprovider.builder;

import android.content.Context;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.ImageCache;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.download.cache.BitmapDiskCache;
import com.example.zane.easyimageprovider.download.cache.BitmapDoubleCache;
import com.example.zane.easyimageprovider.download.cache.BitmapLruCache;

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
    //是否是开发者自定义缓存模式
    boolean isCustom = false;
    //指定的核心线程数
    int threadCount;
    //显示的imageview控件
    ImageView imageView;
    //HolderPlace图片


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
     * @return
     */
//    public ImageLoadBuidler useLruCache(){
//        this.isLruCache = true;
//        imageCache = new BitmapLruCache();
//
//        return this;
//    }
//
//    /**
//     * Lru+Disk
//     * @return
//     */
//    public ImageLoadBuidler useDoubleCache(){
//        this.isDoubleCache = true;
//        imageCache = new BitmapDoubleCache();
//
//        return this;
//    }
//
//    /**
//     * DiskCache
//     * @return
//     */
//    public ImageLoadBuidler useDiskCache(){
//        this.isDiskCache = true;
//        imageCache = new BitmapDiskCache();
//
//        return this;
//    }

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


//    /**
//     * 网络图片默认是返回bitmap，就算是从内存中获得
//     * @param listener
//     * @return
//     */
//    public ImageLoadBuidler setGetImageListener(OnGetImageListener listener){
//        this.listener = listener;
//        return this;
//    }

    /**
     *
     * @param context
     * @return
     */
    public ImageLoadBuidler with(Context context){
        this.context = context;
        return this;
    }


    //----------------------------------------分割线-------------------------------------------

//    public EasyImageProvider_chang build(){
//        return new EasyImageProvider_chang(this);
//    }

}
