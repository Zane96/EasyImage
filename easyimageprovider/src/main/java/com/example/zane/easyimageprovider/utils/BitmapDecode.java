package com.example.zane.easyimageprovider.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zane on 16/5/6.
 * 压缩bitmap
 */
public final class BitmapDecode {

    /**
     * NetLoader
     * @param is
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap decodeRequestBitmap(InputStream inputStream, InputStream finalStream, int requestWidth, int requestHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Bitmap finalBitmap;

            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);


            options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
            options.inJustDecodeBounds = false;

            finalBitmap = BitmapFactory.decodeStream(finalStream, null, options);

            return finalBitmap;
    }

    /**
     * 将InputStream转换成byte数组
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024*16];
        int count = -1;
        while((count = in.read(data,0,1024*16)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return outStream.toByteArray();
    }

    /**
     * 将byte数组转换成InputStream
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream byteTOInputStream(byte[] in) throws Exception{
        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }

    /**
     * LocalLoader
     * @param filePath
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap decodeRequestBitmap(String filePath, int requestWidth, int requestHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Bitmap finalBitmap;

        options.inJustDecodeBounds = true;
        //第一次加载
        BitmapFactory.decodeFile(filePath, options);
        //改变options的inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        //重新加载一次
        options.inJustDecodeBounds = false;
        finalBitmap = BitmapFactory.decodeFile(filePath, options);
        return finalBitmap;

    }

    /**
     * ResourceLoader
     * @param r
     * @param resId
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap decodeRequestBitmap(Resources r, int resId, int requestWidth, int requestHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Bitmap finalBitmap;

        options.inJustDecodeBounds = true;
        //第一次加载
        BitmapFactory.decodeResource(r, resId, options);
        //改变options的inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        //重新加载一次
        options.inJustDecodeBounds = false;
        finalBitmap = BitmapFactory.decodeResource(r, resId, options);
        return finalBitmap;

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){

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
