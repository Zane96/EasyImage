package com.example.zane.easyimageprovider.download.execute;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 通过虚引用保存一个Map,里面是Callable,通过Callable来获得ImageView对应的Task的
 * url,和BitmapRequest的url进行比对,如果不相同那么将value Future实例cancle掉
 * Created by Zane on 16/10/16.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ContainerDrawable extends BitmapDrawable{

    private Reference<Map<Callable, Future>> container;

    public ContainerDrawable(Map<Callable, Future> containerMap){
        container = new WeakReference<Map<Callable, Future>>(containerMap);
    }

    public Map<Callable, Future> getContainerMap(){
        return container.get();
    }
}
