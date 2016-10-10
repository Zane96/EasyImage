package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.zane.easyimageprovider.download.execute.BitmapCallback;
import com.example.zane.easyimageprovider.download.execute.LoadTask;
import com.example.zane.easyimageprovider.download.execute.LoadThreadPoolExecutor;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//网络加载类
public class NetLoader implements ImageLoader{

    private BitmapCallback callback;
    private Future<Bitmap> future;
    private ThreadPoolExecutor executor;
    private Bitmap bitmap;
    private UIImageViewLoader loader;

    public NetLoader(ThreadPoolExecutor executorService){
        executor = executorService;
    }

    @Override
    public void loadImage(final BitmapRequest request) {
        loader = new UIImageViewLoader(request);
        if (loader.beforeLoad()){
            callback = new BitmapCallback(request);
            future = executor.submit(callback);
            Log.i("NetLoader", request.placeHolderId+" placeId");
            loader.showLoading(request.placeHolderId);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bitmap = future.get();
                        Log.i("NetLoader", bitmap + " getBitmap");
                        if (request.getImageView() != null){
                            if (bitmap != null){
                                //注意,这里的bitmap已经是压缩了的
                                loader.loadImageView(bitmap);
                            } else if (!request.getImageView().getTag().equals(request.uri)) {
                                //防止Recycleview的回收机制导致显示了错误图片
                                loader.showLoading(request.placeHolderId);
                            } else {
                                Log.i("NetLoader", "error " + request.getImageView().getTag().equals(request.uri));
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
            }).start();
        } else {
            loader.loadImageViewInCache();
        }
    }
}
