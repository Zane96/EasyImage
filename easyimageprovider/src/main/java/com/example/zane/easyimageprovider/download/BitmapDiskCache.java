package com.example.zane.easyimageprovider.download;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.zane.easyimageprovider.ImageCache;
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

    static {
        synchronized (BitmapDiskCache.class){
            File file = getDiskCacheDir(context, "bitmap");
            if (!file.exists()){
                file.mkdirs();
            }

            if (mDiskLruCache == null){
                try {
                    mDiskLruCache =  DiskLruCache.open(file, getAppVersion(context),
                            1, 10 * 1024 * 1024);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
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
    public void put(String url, Bitmap bitmap) {
        String key = hashKeyForDisk(url);
        try {
            if(mDiskLruCache.get(key) == null){
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if(editor == null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    //压缩质量
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
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
    public Bitmap get(String url) {
        String key = hashKeyForDisk(url);
        try {
            if(mDiskLruCache.get(key) == null) {
                return null;
            }
            else{
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
