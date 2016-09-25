package com.example.zane.easyimageprovider.download.execute;

import com.example.zane.easyimageprovider.builder.EasyImageLoadRecord;
import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

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

    public LoadTask(Callable<Bitmap> callable) {
        super(callable);
        if (!(callable instanceof ThreadPoolQueuePolicy)){
            throw new IllegalArgumentException("callable should be implements ThreadPoolQueuePolicy!");
        }
        policy = ((ThreadPoolQueuePolicy) callable).getPolicy();
    }


    @Override
    public int compareTo(LoadTask<Bitmap> another) {
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
        return policy * 31 + 1;
    }
}
