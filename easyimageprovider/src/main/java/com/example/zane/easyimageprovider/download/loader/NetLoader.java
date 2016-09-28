package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.zane.easyimageprovider.download.execute.BitmapCallback;
import com.example.zane.easyimageprovider.download.execute.LoadTask;
import com.example.zane.easyimageprovider.download.execute.LoadThreadPoolExecutor;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//网络加载类
public class NetLoader implements ImageLoader{

    private BitmapCallback callback;
    private LoadTask<Bitmap> futureTask;
    private ThreadPoolExecutor executor;
    private Bitmap bitmap;
    private UIImageViewLoader loader;

    public NetLoader(ThreadPoolExecutor executorService){
        executor = executorService;
    }

    @Override
    public void loadImage(BitmapRequest request) {

        loader = new UIImageViewLoader(request);
        callback = new BitmapCallback(request);
        futureTask = new LoadTask<>(callback);
        executor.submit(futureTask);

        loader.showLoading(request.placeHolderId);

        try {
            bitmap = futureTask.get();
            if (request.getImageView() != null){
                if (bitmap != null && request.getImageView().getTag().equals(request.uri)){
                    //注意,这里的bitmap已经是压缩了的
                    loader.loadImageView(bitmap);
                } else {
                    loader.showError(request.errorId);
                }
            } else {
                Log.i("NetLoader", "imageview reference is null!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
