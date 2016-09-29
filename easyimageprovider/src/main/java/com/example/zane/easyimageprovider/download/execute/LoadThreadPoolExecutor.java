package com.example.zane.easyimageprovider.download.execute;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.zane.easyimageprovider.download.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

public class LoadThreadPoolExecutor extends ThreadPoolExecutor{

    private static final String NAME = LoadThreadPoolExecutor.class.getSimpleName();

    public LoadThreadPoolExecutor(int maximumPoolSize){
        this(NAME, maximumPoolSize);
    }

    public LoadThreadPoolExecutor(String name, int maximumPoolSize){
        this(maximumPoolSize, maximumPoolSize, 0, TimeUnit.SECONDS, new MyCoustomeThreadFactory(name));
        Log.i("LoadThreadPoolExecutor", maximumPoolSize + " thread count");
    }

    //优先队列
    public LoadThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, ThreadFactory factory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new PriorityBlockingQueue<Runnable>(), factory);
    }

    //自定义线程工厂,设置优先级和自定义name
    private static class MyCoustomeThreadFactory implements ThreadFactory {

        private String name;
        private int threadNum = 0;

        public MyCoustomeThreadFactory(){
            this(NAME);
        }

        public MyCoustomeThreadFactory(String name){
            this.name = name;
        }


        //这里实际传入的是FutureTask
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, name + "-" + threadNum){
                @Override
                public void run() {
                    //设置线程优先级为后台线程
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                    super.run();
                }
            };
            threadNum++;
            return thread;
        }
    }

}
