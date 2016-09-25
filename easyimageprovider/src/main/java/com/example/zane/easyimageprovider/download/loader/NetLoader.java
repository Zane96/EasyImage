package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.download.execute.BitmapCallback;
import com.example.zane.easyimageprovider.download.execute.LoadTask;
import com.example.zane.easyimageprovider.download.execute.LoadThreadPoolExecutor;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//网络加载类
public class NetLoader implements ImageLoader{

    private BitmapCallback callback;
    private LoadTask<Bitmap> futureTask;
    private LoadThreadPoolExecutor executor;

    public NetLoader(LoadThreadPoolExecutor executorService){
        executor = executorService;
    }

    @Override
    public void loadImage(BitmapRequest request) {
        callback = new BitmapCallback(request);
        futureTask = new LoadTask<>(callback);
        executor.submit(futureTask);

    }
}
