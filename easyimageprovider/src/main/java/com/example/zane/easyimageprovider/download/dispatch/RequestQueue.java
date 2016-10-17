package com.example.zane.easyimageprovider.download.dispatch;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

import android.util.Log;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 这个请求队列中只存在加载本地图片和Resource中的图片的BitmapRequest
 */

public class RequestQueue {

    /**
     * 优先队列
     */
    private final BlockingQueue<BitmapRequest> queue;

    private static final int DEFAULT_THREAD_COUNT = Runtime.getRuntime().availableProcessors() + 1;

    //分发线程数
    private final int mDispatchCount;

    //分发线程组
    private final RequestDispatch[] mDispatchers;

    private RequestQueue(){
        queue = new PriorityBlockingQueue<>();
        mDispatchCount = DEFAULT_THREAD_COUNT;
        mDispatchers = new RequestDispatch[mDispatchCount];
    }

    private static class InstanceHolder{
        private static final RequestQueue requestQueue = new RequestQueue();
    }

    public static RequestQueue getIstacne(){
        return InstanceHolder.requestQueue;
    }

    /**
     * 添加到队列
     * @param request
     */
    public void addToQueue(BitmapRequest request){
        if (request == null) throw new IllegalArgumentException("BitmapRequest params in addToQueue cann't be null!");
        if (!queue.contains(request)){
            queue.add(request);
        } else {
            Log.i("RequestQueue", "already add to queue");
        }
    }

    /**
     * 清空队列
     */
    public void clear(){
        queue.clear();
    }

    /**
     * 开始分发
     */
    public void start(){
        for (int i = 0; i < mDispatchCount; i++){
            mDispatchers[i] = new RequestDispatch();
            mDispatchers[i].start();
        }
    }

    /**
     * 非阻塞线程的停止操作
     */
    public void stop(){
        if (!(mDispatchers == null) || mDispatchers.length != 0){
            for(RequestDispatch dispatch : mDispatchers){
                if (!dispatch.isInterrupted()){
                    dispatch.interrupt();
                }
            }
        }
    }

    /**
     * 返回阻塞队列
     * @return
     */
    public BlockingQueue<BitmapRequest> getQueue(){
        return queue;
    }

}
