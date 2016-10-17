package com.example.zane.easyimageprovider.download.execute;

import android.util.Log;

import com.example.zane.easyimageprovider.builder.EasyImageLoadRecord;
import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by Zane on 16/9/23.
 * Email: zanebot96@gmail.com
 */

/**
 * 线程池中的优先级等待队列
 * @param <Bitmap>
 */
public class LoadTask<Bitmap> extends FutureTask<Bitmap> implements Comparable<LoadTask<Bitmap>>{

    //实现和BitmapRequest一样的ID,这是为了在线程的等待对立里面仍然是一种优先级的分发任务
    private int policy;
    //进行错位判断
    private String url;

    public LoadTask(Callable<Bitmap> callable) {
        super(callable);

        if (!(callable instanceof ThreadPoolQueuePolicy)){
            throw new IllegalArgumentException("callable should be implements ThreadPoolQueuePolicy!");
        }
        policy = ((ThreadPoolQueuePolicy) callable).getPolicy();
        Log.i("LoadTask", policy + " ID 1");
    }

    public String getUrl(){
        return url;
    }

//
//    public LoadTask(Runnable runnable, Bitmap result){
//        super(runnable, result);
//        if (!(runnable instanceof Comparable)){
//            throw new IllegalArgumentException("runnable should be implements Comparable!");
//        }
//
//    }

    @Override
    public int compareTo(LoadTask<Bitmap> another) {
        Log.i("LoadTask", policy + " " + another.policy + " ID 2");
        return EasyImageLoadConfiguration.getInstance().getLoadPolicy().compare(policy, another.policy);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LoadTask){
            LoadTask<Bitmap> anthor = (LoadTask<Bitmap>) o;
            return anthor.policy == policy;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return policy * 31 + 17;
    }
}
