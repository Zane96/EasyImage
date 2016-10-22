package com.example.zane.easyimageprovider.download.loader.recycle;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**LeasedDrawable作为和BitmapPool进行交易的中间层
 * 通过引用计数来管理Bitmap的回收/销毁 与否
 *
 * Created by Zane on 16/10/20.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */
public class LeasedDrawable extends BitmapDrawable implements ILeasedDrawable {

    private AtomicInteger mReferenceCount;
    private AtomicBoolean isRecycle = new AtomicBoolean(false);
    private final BitmapPool bitmapPool = BitmapPool.getInstance();

    public LeasedDrawable(Bitmap bitmap){
        super(bitmap);
        mReferenceCount = new AtomicInteger(0);
    }

    @Override
    public void retain() {
        mReferenceCount.incrementAndGet();
    }

    @Override
    public void recycle() {
        if (!isRecycle.get()){
            mReferenceCount.decrementAndGet();
            Bitmap bitmap = getBitmap();
            Log.i("LeasedDrawable", "reference count: " + mReferenceCount);
            if (mReferenceCount.get() < 0){
                throw new IllegalStateException("reference to bitmap can't smaller than 0");
            } else if (mReferenceCount.get() == 0){
                isRecycle.set(true);
                if (bitmapPool.isRecycled){
                    bitmap.recycle();
                } else {
                    //回收内存
                    Log.i("LeasedDrawable", "recycle");
                    bitmapPool.putReuseBitmap(bitmap);
                }
            }
        }
    }
}
