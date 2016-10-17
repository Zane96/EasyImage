package com.example.zane.easyimageprovider.download.dispatch;

import com.example.zane.easyimageprovider.download.loader.LoaderManager;
import com.example.zane.easyimageprovider.download.loader.LocalLoader;
import com.example.zane.easyimageprovider.download.loader.ResourceLoader;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//请求队列的事件分发线程,这里分发的是本地或者resource中加载的图片
public final class RequestDispatch extends Thread{

    private final BlockingQueue<BitmapRequest> queue;

    public RequestDispatch(){
        RequestQueue requestQueue = RequestQueue.getIstacne();
        queue = requestQueue.getQueue();
    }

    @Override
    public void run() {
        super.run();
        while (!Thread.interrupted()){
            try {
                BitmapRequest request = queue.take();
                String uriHead = request.uriHead;
                switch (uriHead) {
                    case DispatchConfig.FILE:
                        LoaderManager.getInstance().loadImageView(new LocalLoader(), request);
                        break;
                    case DispatchConfig.RESOURCE:
                        LoaderManager.getInstance().loadImageView(new ResourceLoader(), request);
                        break;
                    default:
                        throw new IllegalArgumentException("uriHeader should be file or resource in dispatchThread!");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
