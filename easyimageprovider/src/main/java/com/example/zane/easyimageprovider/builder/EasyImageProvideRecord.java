package com.example.zane.easyimageprovider.builder;

/**
 * Created by Zane on 16/5/11.
 * imageprovider的记录类
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
//    public EasyImageProvider_chang build(){
//        //return new EasyImageProvider_chang(this);
//        if (isLoad){
//            if (isDoubleCache){
//                return new EasyImageProvider_chang(new BitmapDoubleCache());
//            } else if(isDiskCache){
//                return new EasyImageProvider_chang(new BitmapDiskCache());
//            } else if (isLruCache){
//                return new EasyImageProvider_chang(new BitmapLruCache());
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
