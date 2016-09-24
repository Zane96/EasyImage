package com.example.zane.easyimageprovider.download.execute;

import com.example.zane.easyimageprovider.builder.EasyImageLoadRecord;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by Zane on 16/9/23.
 * Email: zanebot96@gmail.com
 */

/**
 * 由于我们分发队列
 * @param <Bitmap>
 */
public class LoadTask<Bitmap> extends FutureTask<Bitmap> implements Callable<LoadTask<Bitmap>>{

    //实现和BitmapRequest一样的ID,这是为了在线程的等待对立里面仍然是一种优先级的分发任务
    private int ID;

    public LoadTask(Callable<Bitmap> callable) {
        super(callable);
    }

    @Override
    public LoadTask<Bitmap> call() throws Exception {
        return null;
    }
}
