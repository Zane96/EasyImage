package com.example.zane.easyimageprovider.builder;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.record.ImageRecord;
import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.cache.BitmapDiskCache;
import com.example.zane.easyimageprovider.download.cache.BitmapDoubleCache;
import com.example.zane.easyimageprovider.download.cache.BitmapLruCache;
import com.example.zane.easyimageprovider.download.cache.BitmapNoCache;
import com.example.zane.easyimageprovider.download.cache.ImageCache;
import com.example.zane.easyimageprovider.download.dispatch.DispatchConfig;
import com.example.zane.easyimageprovider.download.policy.ImageLoadPolicy;

/**
 * Created by Zane on 16/5/19.
 * load的记录和两类config判断类
 */
public class EasyImageLoadRecord{

    private ImageLoadBuidler buidler;
    //是否使用Lru缓存
    private boolean isLruCache = false;
    //是否使用Disk缓存
    private boolean isDiskCache = false;
    //是否两层缓存
    private boolean isDoubleCache = false;
    //是否是开发者自定义缓存模式
    private boolean isCustom = false;
    //是否不使用缓存
    private boolean isNoCache = false;

    private EasyImageLoadConfiguration configuration;

    //显示的imageview控件
    public ImageView imageView;
    //占位图resID
    public int holderPlaceId = -1;
    //错误之后的图片resid
    public int errorId = -1;
    //缓存抽象
    public ImageCache imageCache;
    //uri
    public String uri;
    //file/resource
    public String uriHead;
    private Context context;


    public EasyImageLoadRecord(ImageLoadBuidler builder){
        this.buidler = builder;
        configuration = EasyImageLoadConfiguration.getInstance();
        initParams();
    }

    private void initParams() {

        isDiskCache = buidler.isDiskCache;
        isCustom = buidler.isCustom;
        isDoubleCache = buidler.isDoubleCache;
        isLruCache = buidler.isLruCache;
        isNoCache = buidler.isNoCache;
        context = buidler.context;

        if (context == null){
            throw new IllegalStateException("with() method should be invoked first!");

        }

        if (isDiskCache && !isNoCache && !isLruCache && !isDoubleCache && !isCustom){

            imageCache = BitmapDiskCache.getInstance(context);
        } else if (isNoCache && !isDiskCache && !isLruCache && !isDoubleCache && !isCustom){
            imageCache = new BitmapNoCache();
        } else if (isLruCache && !isDiskCache && !isNoCache && !isDoubleCache && !isCustom){
            imageCache = new BitmapLruCache();
        } else if (isDoubleCache && !isDiskCache && !isNoCache && !isDoubleCache && !isCustom){
            imageCache = new BitmapDoubleCache(context);
        } else if (isCustom && !isDiskCache && !isNoCache && !isDoubleCache && !isDoubleCache) {
            imageCache = buidler.imageCache;
        }

        if (buidler.imageView == null) throw new IllegalStateException("ImageView shouldn't br null");
        else imageView = buidler.imageView;

        if (buidler.uri == null) throw new IllegalArgumentException("uri should not be null!");
        else uri = buidler.uri;

        parseSchema(uri);
    }

    private void parseSchema(String uri) {
        if (uri.contains("://")) {
            if (uri.split("://")[0].equals("http") || uri.split("://")[0].equals("https")){
                uriHead = DispatchConfig.NET;
            } else {
                uriHead = DispatchConfig.FILE;
            }
        } else {
            uriHead = DispatchConfig.RESOURCE;
        }
        Log.i("EasyImageLoadRecord", uriHead + " uriHead in EasyImageLoadRecord");
    }
}
