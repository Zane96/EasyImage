package com.example.zane.easyimageprovider.download.loader;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//策略模式的分配类
public class LoaderManager {

    private ImageLoader imageLoader;
    private BitmapRequest request;
    private static LoaderManager loaderManager;

    private LoaderManager(ImageLoader imageLoader, BitmapRequest request){
        this.imageLoader = imageLoader;
        this.request = request;
    }

    public static synchronized LoaderManager getInstance(ImageLoader imageLoader, BitmapRequest request){
        if (loaderManager == null){
            loaderManager = new LoaderManager(imageLoader, request);
        }
        return loaderManager;
    }

    public void loadImageView(){
        imageLoader.loadImage(request);
    }
}
