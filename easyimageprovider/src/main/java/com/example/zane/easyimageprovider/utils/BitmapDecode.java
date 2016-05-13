package com.example.zane.easyimageprovider.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Zane on 16/5/6.
 * 压缩bitmap
 */
public final class BitmapDecode {

    public Bitmap decodeRequestBitmapFromResource(Resources res, int resId
                                                         , int requestWidth, int requestHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //第一次加载
        BitmapFactory.decodeResource(res, resId, options);
        //改变options的inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        //重新加载一次
        options.inJustDecodeBounds = false;
        Bitmap finalBitmap = BitmapFactory.decodeResource(res, resId, options);
        return finalBitmap;

    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){

        final int oriHeight = options.outHeight;
        final int oriWidth = options.outWidth;

        int sampleSize = 1;
        if (oriHeight > reqHeight && oriWidth > reqWidth){
            final int helfOriHeight = oriHeight / 2;
            final int helfOriWidth = oriWidth / 2;
            //如果还是大于，那么就把sampleSize乘2
            while ((helfOriHeight / sampleSize) > reqHeight && (helfOriWidth /sampleSize) > reqWidth){
                sampleSize *= 2;
            }
        }

        return sampleSize;
    }
}
