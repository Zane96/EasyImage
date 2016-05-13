package com.example.zane.easyimageprovider.builder;

import android.content.Context;

import com.example.zane.easyimageprovider.ImageCache;
import com.example.zane.easyimageprovider.download.BitmapDiskCache;
import com.example.zane.easyimageprovider.download.BitmapDoubleCache;
import com.example.zane.easyimageprovider.download.BitmapLruCache;

/**
 * Created by Zane on 16/5/11.
 */
public class EasyImageProvideRecord {
//
//    //provider
//    boolean isCrop;
//    boolean isAlbum;
//    boolean isCamera;
//    //load
//    boolean isLruCache;
//    boolean isDiskCache;
//    boolean isDoubleCache;
//
//    private ImageCache imageCache;
//    private boolean isProvider;
//    private boolean isLoad;
//    Context context;
//
//    public EasyImageProvideRecord(ImageProviderBuilder imageProviderBuilder){
//        isCrop = imageProviderBuilder.isCrop;
//        isAlbum = imageProviderBuilder.isAlbum;
//        isCamera = imageProviderBuilder.isCamera;
//        context = imageProviderBuilder.context;
//        isProvider = true;
//        isLoad = false;
//
//        build();
//    }
//
//    public EasyImageProvideRecord(ImageLoadBuidler imageLoadBuidler){
//        isLruCache = imageLoadBuidler.isLruCache;
//        isDiskCache = imageLoadBuidler.isDiskCache;
//        isDoubleCache = imageLoadBuidler.isDoubleCache;
//        isLoad = true;
//        isProvider = false;
//
//        build();
//    }
//
//    public EasyImageProvider build(){
//        //return new EasyImageProvider(this);
//        if (isLoad){
//            if (isDoubleCache){
//                return new EasyImageProvider(new BitmapDoubleCache());
//            } else if(isDiskCache){
//                return new EasyImageProvider(new BitmapDiskCache());
//            } else if (isLruCache){
//                return new EasyImageProvider(new BitmapLruCache());
//            } else {
//                throw new IllegalStateException("can't be no state from load");
//            }
//        } else {
//            if (isAlbum){
//
//            }
//        }
//    }

}
