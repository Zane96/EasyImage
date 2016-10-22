package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.download.execute.BitmapCallback;
import com.example.zane.easyimageprovider.download.execute.ContainerDrawable;
import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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


    private Future<LeasedDrawable> future;
    private final ThreadPoolExecutor executor;
    private LeasedDrawable drawable;
    private UIImageViewLoader loader;

    private Thread startLoader;
    private final Map<BitmapCallback, Future> container;

    public NetLoader(ThreadPoolExecutor executorService){
        executor = executorService;
        container = new HashMap<>();
    }

    @Override
    public void loadImage(final BitmapRequest request) {
        loader = new UIImageViewLoader(request);
        startTask(request);
    }

    private void startTask(final BitmapRequest request) {
        if (cancelBeforeTask(request.uri, request.getImageView())){
            if (loader.beforeLoad()) {
                BitmapCallback callback = new BitmapCallback(request);
                future = executor.submit(callback);
                container.put(callback, future);

                loader.showLoading(container, request.placeHolderId);

                startLoader = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!Thread.currentThread().isInterrupted()){
                                drawable = future.get();
                            }
                        } catch (InterruptedException e){
                            Thread.currentThread().interrupt();
                        } catch (CancellationException e){
                            Thread.currentThread().interrupt();
                        } catch (ExecutionException e){
                            Log.i("NetLoader", "computation error");
                        }

                        if (request.getImageView() != null){
                            if (drawable != null){
                                //注意,这里的bitmap已经是压缩了的
                                loader.loadImageView(drawable);
                            } else {
                                if (Thread.currentThread().isInterrupted()){
                                    Log.i("NetLoader", "error by thread intrrupted");
                                } else {
                                    loader.showError(request.errorId);
                                }
                            }
                        }
                    }
                });
                startLoader.start();
            } else {
                loader.loadImageViewInCache();
            }
        }
    }

    /**
     * 根据传入的ImageView来获取callable来判断uri是否相同,防止RecycleView加载错位
     * @param url
     * @param imageView
     * @return
     */
    private boolean cancelBeforeTask(String url, ImageView imageView){
        Map<BitmapCallback, Future> container = getContainer(imageView);
        if (container != null) {
            Iterator<BitmapCallback> it = container.keySet().iterator();
            BitmapCallback callback = it.next();
            if (callback != null){
                if (callback.getTaskUrl().equals(url)){
                    return false;
                } else {
                    container.get(callback).cancel(true);
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * 如果Task已经完成,那么setBitmap之后,ImageView的drawable会被重新赋值一个Drawable(非ContainerDrawable)
     * @param imageview
     * @return null 需要重新开始Load, not null传入container去给cancelBeforeTask()函数判断url是否相同
     */
    private Map<BitmapCallback, Future> getContainer(ImageView imageview){
        Drawable drawable = imageview.getDrawable();
        if (drawable instanceof ContainerDrawable){
            return ((ContainerDrawable) drawable).getContainerMap();
        } else if (drawable instanceof  LeasedDrawable){
            //回收内存
            ((LeasedDrawable) drawable).recycle();
            Log.i("NetLoaderRecycle", " recycle");
        }
        return null;
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
