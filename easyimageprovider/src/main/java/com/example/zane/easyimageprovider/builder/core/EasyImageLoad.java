package com.example.zane.easyimageprovider.builder.core;

import android.content.Intent;

import com.example.zane.easyimageprovider.builder.EasyImageLoadRecord;
import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.dispatch.DispatchConfig;
import com.example.zane.easyimageprovider.download.dispatch.RequestQueue;
import com.example.zane.easyimageprovider.download.execute.LoadThreadPoolExecutor;
import com.example.zane.easyimageprovider.download.loader.ImageLoader;
import com.example.zane.easyimageprovider.download.loader.LoaderManager;
import com.example.zane.easyimageprovider.download.loader.NetLoader;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public class EasyImageLoad implements EasyImage {

    private EasyImageLoadRecord r;
    private ImageLoader loader;
    private BitmapRequest request;
    private static EasyImageLoad instance;
    private static ThreadPoolExecutor executor = new LoadThreadPoolExecutor("EasyImageProvider", EasyImageLoadConfiguration.getInstance().getThreadCount());

    private EasyImageLoad(ImageLoadBuidler buidler) {
        r = new EasyImageLoadRecord(buidler);
        request = new BitmapRequest(r);
    }

    public static EasyImageLoad newInstance(ImageLoadBuidler buidler){
        return new EasyImageLoad(buidler);
    }


    @Override
    public void execute() {
        request = new BitmapRequest(r);
        if (r.uriHead.equals(DispatchConfig.NET)){
            startNetLoad();
        } else {
            startLoaclLoad();
        }
    }

    /**
     * 网络加载引擎启动
     */
    private void startNetLoad(){
        loader = new NetLoader(executor);
        LoaderManager.getInstance().loadImageView(loader, request);
    }

    /**
     * 本地加载引擎启动
     */
    private void startLoaclLoad(){
        RequestQueue.getIstacne().addToQueue(request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
