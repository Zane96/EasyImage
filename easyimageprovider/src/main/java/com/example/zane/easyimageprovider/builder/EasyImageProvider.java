package com.example.zane.easyimageprovider.builder;

import android.content.Context;

import com.example.zane.easyimageprovider.ImageCache;
import com.example.zane.easyimageprovider.ImageProvider;

/**
 * Created by Zane on 16/5/5.
 */
public class EasyImageProvider {

    //provider
    boolean isCrop;
    private boolean isFromAlbum;
    private boolean isFromCamera;
    //load
    private boolean isLruCache;
    private boolean isDiskCache;
    private boolean isDoubleCache;

    private ImageCache imageCache;
    private ImageProvider imageProvider;

    private ImageProviderBuilder imageProviderBuilder;
    private ImageLoadBuidler imageLoadBuidler;

    private Context context;

    public EasyImageProvider(ImageProviderBuilder imageProviderBuilder){
        this.imageProviderBuilder = imageProviderBuilder;
    }

    public EasyImageProvider(ImageLoadBuidler imageLoadBuidler){
        this.imageLoadBuidler = imageLoadBuidler;
    }

}
