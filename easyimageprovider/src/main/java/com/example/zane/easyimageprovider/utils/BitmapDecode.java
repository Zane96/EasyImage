package com.example.zane.easyimageprovider.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.zane.easyimageprovider.download.loader.recycle.BitmapPool;
import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.zane.easyimageprovider.download.loader.recycle.BitmapPool.*;

/**
 * Created by Zane on 16/5/6.
 * 压缩bitmap
 */
public final class BitmapDecode {

    //第一个Map,ImageView的size,第二个Map,对应Bitmap的size
    private static final Map<Map<Integer, Integer>, Map<Integer, Integer>> RE_SIZE2BITMAP_SIZE = new HashMap<>();

    /**
     * NetLoader
     * @param inputStream
     * @param finalStream
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static LeasedDrawable decodeRequestBitmap(InputStream inputStream, InputStream finalStream, int requestWidth, int requestHeight){
        final Map<Integer, Integer> bitmapSizeMap = isInRouteMap(requestWidth, requestHeight);
        final Bitmap finalBitmap;
        BitmapFactory.Options options;
        if (bitmapSizeMap != null){

            Map.Entry<Integer, Integer> bitmapSize = bitmapSizeMap.entrySet().iterator().next();
            options = getInstance().getReuseOption(bitmapSize.getKey(), bitmapSize.getValue());

            if (options != null){
                finalBitmap = BitmapFactory.decodeStream(inputStream, null, options);
                return new LeasedDrawable(finalBitmap);
            }
        }

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        options.inJustDecodeBounds = false;
        finalBitmap = BitmapFactory.decodeStream(finalStream, null, options);

        setInMap(requestWidth, requestHeight, finalBitmap.getWidth(), finalBitmap.getHeight());

        return new LeasedDrawable(finalBitmap);
    }

    /**
     * Ugly Code!
     * @param reqWidth
     * @param reqHeight
     * @param bitmapWidth
     * @param bitmapHeight
     */
    private static void setInMap(int reqWidth, int reqHeight, int bitmapWidth, int bitmapHeight){
        Map<Integer, Integer> reqSize = new HashMap<>();
        reqSize.put(reqWidth, reqHeight);

        Map<Integer, Integer> bitmapSize = new HashMap<>();
        bitmapSize.put(bitmapWidth, bitmapHeight);

        RE_SIZE2BITMAP_SIZE.put(reqSize, bitmapSize);
        Log.i("BitmapDecode", "Size: " + RE_SIZE2BITMAP_SIZE.size());
    }


    /**
     * 判断请求的ImageView的size和某个Bitmap的Size是否存在某个关系
     * @param reqWidth
     * @param reqHeight
     * @return null,路由表中不存在,non-null,路由表中存在这个关系
     */
    private static Map<Integer, Integer> isInRouteMap(int reqWidth, int reqHeight){
        Iterator<Map.Entry<Map<Integer, Integer>, Map<Integer, Integer>>> iterator = RE_SIZE2BITMAP_SIZE.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Map<Integer, Integer>, Map<Integer, Integer>> entry = iterator.next();
            Map<Integer, Integer> reqMap = entry.getKey();
            if (reqMap.get(reqWidth) != null){
                if (reqMap.get(reqWidth) == reqHeight){
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * DiskCache
     * @param inputStream
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static LeasedDrawable decodeRequestBitmap(InputStream inputStream, int requestWidth, int requestHeight){

        final Map<Integer, Integer> bitmapSizeMap = isInRouteMap(requestWidth, requestHeight);
        final Bitmap finalBitmap;
        BitmapFactory.Options options;
        if (bitmapSizeMap != null){

            Map.Entry<Integer, Integer> bitmapSize = bitmapSizeMap.entrySet().iterator().next();
            options = getInstance().getReuseOption(bitmapSize.getKey(), bitmapSize.getValue());

            if (options != null){
                finalBitmap = BitmapFactory.decodeStream(inputStream, null, options);
                return new LeasedDrawable(finalBitmap);
            }
        }

        finalBitmap = BitmapFactory.decodeStream(inputStream);
        setInMap(requestWidth, requestHeight, finalBitmap.getWidth(), finalBitmap.getHeight());
        return new LeasedDrawable(finalBitmap);
    }

    /**
     * LocalLoader
     * @param filePath
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static LeasedDrawable decodeRequestBitmap(String filePath, int requestWidth, int requestHeight){
        final Map<Integer, Integer> bitmapSizeMap = isInRouteMap(requestWidth, requestHeight);
        final Bitmap finalBitmap;
        BitmapFactory.Options options;
        if (bitmapSizeMap != null){
            Map.Entry<Integer, Integer> bitmapSize = bitmapSizeMap.entrySet().iterator().next();
            options = getInstance().getReuseOption(bitmapSize.getKey(), bitmapSize.getValue());

            if (options != null){
                finalBitmap = BitmapFactory.decodeFile(filePath, options);
                return new LeasedDrawable(finalBitmap);
            }
        }

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //第一次加载
        BitmapFactory.decodeFile(filePath, options);
        //改变options的inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        //重新加载一次
        options.inJustDecodeBounds = false;

        finalBitmap = BitmapFactory.decodeFile(filePath, options);
        setInMap(requestWidth, requestHeight, finalBitmap.getWidth(), finalBitmap.getHeight());
        return new LeasedDrawable(finalBitmap);
    }

    /**
     * ResourceLoader
     * @param r
     * @param resId
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static LeasedDrawable decodeRequestBitmap(Resources r, int resId, int requestWidth, int requestHeight){
        final Map<Integer, Integer> bitmapSizeMap = isInRouteMap(requestWidth, requestHeight);
        final Bitmap finalBitmap;
        BitmapFactory.Options options;
        if (bitmapSizeMap != null){
            Map.Entry<Integer, Integer> bitmapSize = bitmapSizeMap.entrySet().iterator().next();
            options = getInstance().getReuseOption(bitmapSize.getKey(), bitmapSize.getValue());

            if (options != null){
                finalBitmap = BitmapFactory.decodeResource(r, resId, options);
                return new LeasedDrawable(finalBitmap);
            }
        }

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //第一次加载
        BitmapFactory.decodeResource(r, resId, options);
        //改变options的inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        //重新加载一次
        options.inJustDecodeBounds = false;

        finalBitmap = BitmapFactory.decodeResource(r, resId, options);
        setInMap(requestWidth, requestHeight, finalBitmap.getWidth(), finalBitmap.getHeight());
        return new LeasedDrawable(finalBitmap);

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
        return new ByteArrayInputStream(in);
    }
}
