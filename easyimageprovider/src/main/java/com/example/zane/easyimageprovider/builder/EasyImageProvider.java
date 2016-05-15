package com.example.zane.easyimageprovider.builder;

import android.content.Context;

import com.example.zane.easyimageprovider.ImageCache;
import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;

/**
 * Created by Zane on 16/5/5.
 * 主体类
 */
public class EasyImageProvider {

//    //provider
//    boolean isCrop;
//    private boolean isFromAlbum;
//    private boolean isFromCamera;
//    //load
//    private boolean isLruCache;
//    private boolean isDiskCache;
//    private boolean isDoubleCache;
    private boolean isProvider = false;
    private boolean isLoad = false;

    private ImageCache imageCache;
    private ImageProvider imageProvider;

    private ImageProviderBuilder imageProviderBuilder;
    private ImageLoadBuidler imageLoadBuidler;

    //开发者回调接口
    private OnGetImageListener onGetImageListener;

    private Context context;

    public EasyImageProvider(ImageProviderBuilder imageProviderBuilder){
        this.imageProviderBuilder = imageProviderBuilder;
        isProvider = true;
    }

    public EasyImageProvider(ImageLoadBuidler imageLoadBuidler){
        this.imageLoadBuidler = imageLoadBuidler;
        isLoad = true;
    }

    public void execute(){

    }

}
