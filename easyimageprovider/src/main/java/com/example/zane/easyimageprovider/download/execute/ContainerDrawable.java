package com.example.zane.easyimageprovider.download.execute;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 通过虚引用保存一个Map,里面是Callable,通过Callable来获得ImageView对应的Task的
 * url,和BitmapRequest的url进行比对,如果不相同那么将value Future实例cancle掉
 * Created by Zane on 16/10/16.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ContainerDrawable extends BitmapDrawable{

    private final Reference<Map<BitmapCallback, Future>> container;

    public ContainerDrawable(Resources resources, Bitmap bitmap, Map<BitmapCallback, Future> containerMap){
        super(resources, bitmap);
        container = new WeakReference<Map<BitmapCallback, Future>>(containerMap);
    }

    public Map<BitmapCallback, Future> getContainerMap(){
        return container.get();
    }
}
