package com.example.zane.easyimageprovider.download.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;
import com.example.zane.easyimageprovider.utils.BitmapDecode;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Zane on 16/5/6.
 * 管理DiskLruCache的类
 */
public final class BitmapDiskCache implements ImageCache {

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 30;
    private static Context context;
    private static DiskLruCache mDiskLruCache;

    private BitmapDiskCache(){
    }

    public static BitmapDiskCache getInstance(Context context2){
        context = context2;
        init();
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static final BitmapDiskCache instance = new BitmapDiskCache();
    }

    //init diskcache
    private static synchronized void init(){
        File file = getDiskCacheDir(context, "bitmap");
        if (!file.exists()){
            file.mkdirs();
        }

        if (mDiskLruCache == null){
            try {
                mDiskLruCache =  DiskLruCache.open(file, getAppVersion(context),
                        1, DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获得文件的key，通过MD5加密文件名获得key
    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    //将字节流组装成字符串
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //获得app版本号
    private static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //获取缓存的文件路径
    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    //---------------------------------分割线-----------------------------------------------------

    @Override
    public void put(String url, LeasedDrawable drawable) {
        String key = hashKeyForDisk(url);
        try {
            if(mDiskLruCache.get(key) == null){
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if(editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    //压缩质量
                    if (drawable.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                        editor.commit();
                    }
                    else {
                        editor.abort();
                    }
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LeasedDrawable get(String url, int width, int height) {
        String key = hashKeyForDisk(url);
        try {
            if(mDiskLruCache.get(key) == null) {
                return null;
            }
            else{
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                InputStream inputStream = snapshot.getInputStream(0);
                LeasedDrawable drawable = BitmapDecode.decodeRequestBitmap(inputStream, width, height);

                //增加一个引用
                drawable.retain();

                return drawable;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String url) {
        try {
            mDiskLruCache.remove(hashKeyForDisk(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
