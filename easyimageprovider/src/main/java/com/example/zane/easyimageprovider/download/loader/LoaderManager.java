package com.example.zane.easyimageprovider.download.loader;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//策略模式的分配类
public class LoaderManager {

    private LoaderManager(){
    }

    private static class InstacneHolder{
        private static final LoaderManager instance = new LoaderManager();
    }

    public static LoaderManager getInstance(){
        return InstacneHolder.instance;
    }

    public void loadImageView(ImageLoader imageLoader, BitmapRequest request){
        imageLoader.loadImage(request);
    }
}
