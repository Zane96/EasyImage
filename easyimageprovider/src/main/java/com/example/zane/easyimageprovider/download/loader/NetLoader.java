package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.zane.easyimageprovider.download.execute.BitmapCallback;
import com.example.zane.easyimageprovider.download.execute.LoadTask;
import com.example.zane.easyimageprovider.download.execute.LoadThreadPoolExecutor;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;

import java.util.concurrent.CancellationException;
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

    private Thread startLoader;

    public NetLoader(ThreadPoolExecutor executorService){
        executor = executorService;
    }

    @Override
    public void loadImage(final BitmapRequest request) {
        loader = new UIImageViewLoader(request);
        startTask(request);
    }

    private void startTask(final BitmapRequest request) {
        if (loader.beforeLoad()) {
            callback = new BitmapCallback(request);
            future = executor.submit(callback);
            loader.showLoading(request.placeHolderId);

            startLoader = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!Thread.currentThread().isInterrupted()){
                            bitmap = future.get();
                        }
                    } catch (InterruptedException e){
                        Log.i("NetLoader", "thread intrrupt");
                        Thread.currentThread().interrupt();
                    } catch (CancellationException e){
                        Log.i("NetLoader", "cancle computation");
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e){
                        Log.i("NetLoader", "computation error");
                    }

                    Log.i("NetLoader", bitmap + " getBitmap " + request.ID);
                    if (request.getImageView() != null){
                        if (bitmap != null && !request.getImageView().getTag().equals(request.uri)){
                            //注意,这里的bitmap已经是压缩了的
                            loader.loadImageView(bitmap);
                        } else {
                            Log.i("NetLoader", "error " + request.getImageView().getTag().equals(request.uri));
                            if (Thread.currentThread().isInterrupted()){
                                Log.i("NetLoader", "error by thread intrrupted");
                            } else {
                                loader.showError(request.errorId);
                            }
                        }
                    } else {
                        Log.i("NetLoader", "imageview reference is null!");
                    }
                }
            });

            startLoader.start();

        } else {
            loader.loadImageViewInCache();
        }
    }

    /**
     * 中断加载线程
     */
    public void cancelLoader(){
        if (startLoader.isAlive() && !startLoader.isInterrupted()){
            startLoader.interrupt();
        }
    }
}
