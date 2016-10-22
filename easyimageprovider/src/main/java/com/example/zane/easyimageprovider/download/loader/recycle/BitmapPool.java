package com.example.zane.easyimageprovider.download.loader.recycle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zane on 16/10/19.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public final class BitmapPool {

    //Bitmap中的bitmap存储数据结构,需要在代码中进行线程安全的操作
    private final List<Bitmap> mBitmaps = new LinkedList<>();

    //位图池是否正在销毁
    boolean isRecycled;

    private BitmapPool(){
        isRecycled = false;
    }

    //保持单例
    private static final class InstanceHolder{
        private static final BitmapPool instance = new BitmapPool();
    }

    public static BitmapPool getInstance(){
        return InstanceHolder.instance;
    }

    /**
     * inBitmap这个字段从Android 3.0才添加,而4.4之后才要求我如下代码的要求。这里我们只要大于3.0的版本
     * 我们都满足如下要求,因为本来这里就是为生成大量Bitmap的具有回收机制的控件而设计的(item中Bitmap的config相同)
     * @param width
     * @param height
     * @return true 在Bitmap中找到了合适的Bitmap内存空间,false 没有找到合适的Bitmap,需要自己生成
     */
    public BitmapFactory.Options getReuseOption(int width, int height){
        synchronized (mBitmaps){
            Log.i("BitmapPool", "Prereuse " + mBitmaps.size()+ " size");
            BitmapFactory.Options options = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                Bitmap reuseBitmap = getBitmapBySize(width, height);
                if (reuseBitmap != null) {
                    Log.i("BitmapPool", "reuse");
                    options = new BitmapFactory.Options();
                    options.inBitmap = reuseBitmap;
                    options.inSampleSize = 1;
                    options.inMutable = true;
                }
            }
            return options;
        }
    }

    /**
     * 将Lru中
     * @param bitmap
     */
    void putReuseBitmap(Bitmap bitmap){
        synchronized (mBitmaps){
            Log.i("BitmapPool", "putin");
            mBitmaps.add(bitmap);
        }
    }

    /**
     * 根据传入的所需要的Bitmap的宽和高来返回Bitmap池中符合要求的Bitmap.
     * 必须是宽高一致。不然无法复用
     * 注意不要出现ConcurrentModificationException
     * @param width
     * @param height
     * @return
     */
    private Bitmap getBitmapBySize(int width, int height){
        List<Bitmap> removeBitmaps = new LinkedList<>();
        Bitmap returnBitmap = null;
            for (Bitmap bitmap : mBitmaps){
                if (bitmap.isRecycled()) {
                    removeBitmaps.add(bitmap);
                } else {
                    Log.i("BitmapPool", "width height " + width + " " + height + " bitmap: " + bitmap.getWidth() + " " + bitmap.getHeight());
                    if (bitmap.getWidth() == width && bitmap.getHeight() == height){

                        removeBitmaps.add(bitmap);
                        returnBitmap = bitmap;
                    }
                }
            }
            mBitmaps.removeAll(removeBitmaps);
            return returnBitmap;
    }

    /**
     * 清空BitmapPool缓存的Bitmap内存空间
     */
    public void clearBitmapPool(){
        synchronized (mBitmaps){
            isRecycled = true;
            int i = 0;
            while (!mBitmaps.isEmpty()){
                Bitmap bitmap = mBitmaps.remove(i);
                i++;
                if (!bitmap.isRecycled()){
                    bitmap.recycle();
                }
            }
        }
    }

}
