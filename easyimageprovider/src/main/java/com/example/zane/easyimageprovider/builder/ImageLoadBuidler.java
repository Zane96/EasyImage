package com.example.zane.easyimageprovider.builder;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.download.cache.BitmapDiskCache;
import com.example.zane.easyimageprovider.download.cache.BitmapDoubleCache;
import com.example.zane.easyimageprovider.download.cache.BitmapLruCache;
import com.example.zane.easyimageprovider.download.cache.BitmapNoCache;
import com.example.zane.easyimageprovider.download.cache.ImageCache;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

/**
 * Created by Zane on 16/5/11.
 * 主体类对于load功能的构建类。开放修改和扩展
 */
public class ImageLoadBuidler {

    //是否使用Lru缓存
    protected boolean isLruCache = false;
    //是否使用Disk缓存
    protected boolean isDiskCache = false;
    //是否两层缓存
    protected boolean isDoubleCache = false;
    //是否是开发者自定义缓存模式
    protected boolean isCustom = false;
    //是否不使用缓存
    protected boolean isNoCache = false;
    //显示的imageview控件
    protected ImageView imageView;
    //占位图resID
    protected int holderPlaceId = -1;
    //错误之后的图片resid
    protected int errorId = -1;
    //缓存抽象
    protected ImageCache imageCache;
    //加载的图片uri
    protected String uri;

    protected Context context;

    public ImageLoadBuidler(){

    }

    //-----------------------------------------调用链------------------------------------------

    /**
     * LruCache
     * @return
     */
    public ImageLoadBuidler useLruCache(){
        this.isLruCache = true;

        return this;
    }

    /**
     * Lru+Disk
     * @return
     */
    public ImageLoadBuidler useDoubleCache(){
        this.isDoubleCache = true;

        return this;
    }

    /**
     * DiskCache
     * @return
     */
    public ImageLoadBuidler useDiskCache(){
        this.isDiskCache = true;

        return this;
    }

    /**
     * no cache
     * @return
     */
    public ImageLoadBuidler useNoCache(){
        this.isNoCache = true;

        imageCache = new BitmapNoCache();
        return this;
    }

    /**
     * custom cache
     * @param imageCache
     * @return
     */
    public ImageLoadBuidler useCustomCache(ImageCache imageCache){
//        if (! (imageCache instanceof ImageCache)){
//            throw new IllegalArgumentException("your custom cache must implement ImageCache");
//        }
        this.imageCache = imageCache;
        isCustom = true;

        return this;
    }

    /**
     *
     * @param holderPlace
     * @return
     */
    public ImageLoadBuidler setHolderPlace(@DrawableRes int holderPlace){
        this.holderPlaceId = holderPlace;
        return this;
    }

    /**
     *
     * @param errorId
     * @return
     */
    public ImageLoadBuidler setError(@DrawableRes int errorId){
        this.errorId = errorId;
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

    public ImageLoadBuidler load(String uri){
        this.uri = uri;
        return this;
    }

    /**
     *
     * @param imageview
     */
    public void into(ImageView imageview){
       this.imageView = imageview;
    }

    //----------------------------------------分割线-------------------------------------------

//    public EasyImageProvider_chang build(){
//        return new EasyImageProvider_chang(this);
//    }

}
