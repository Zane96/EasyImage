package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;
import android.util.Log;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.download.cache.ImageCache;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by Zane on 16/9/25.
 * Email: zanebot96@gmail.com
 */

//渲染以及
public class UIImageViewLoader {

    private BitmapRequest request;
    private ImageCache cache;
    private LoadHandler handler;

    private static final int LOAD = 1;
    private static final int LOADING = 2;
    private static final int ERROR = 3;
    private static final int LOAD_CACHE = 4;

    protected UIImageViewLoader(BitmapRequest request) {
        this.request = request;
        this.cache = request.cache;
        handler = new LoadHandler(request);
    }

    /**
     * 在load之前调用
     * @return true表示缓存中没有,false表示缓存中有
     */
    protected boolean beforeLoad(){
        Log.i("UIImageViewLoader", cache + " cache");
        if (cache.get(request.uri) != null){
            loadImageView(cache.get(request.uri));
            return false;
        } else {
            return true;
        }
    }

    /**
     * 存入缓存
     * @param bitmap
     */
    private void setInCache(Bitmap bitmap){
        if (cache != null && request != null){
            synchronized (cache){
                cache.put(request.uri, bitmap);
            }
        } else {
            Log.i("UIImageViewLoader", "cache is " + cache + " request is " + request);
        }
    }

    /**
     * load完毕之后调用,用来渲染
     * @param bitmap
     */
    protected void loadImageView(Bitmap bitmap){
        setInCache(bitmap);
        final Message message = new Message();
        message.what = LOAD;
        message.obj = bitmap;
        handler.sendMessage(message);
    }

    /**
     * 在缓存中加载数据
     */
    protected void loadImageViewInCache(){
        final Message message = new Message();
        final Bitmap bitmap = cache.get(request.uri);
        message.what = LOAD_CACHE;
        message.obj = bitmap;
        handler.sendMessage(message);
    }

    /**
     * 加载过程中调用,显示占位图
     * @param id
     */
    protected void showLoading(int id){
        final Message message = new Message();
        message.what = LOADING;
        message.obj = id;
        handler.sendMessage(message);
    }

    /**
     * 加载错误的时候调用,显示错误图片
     * @param id
     */
    protected void showError(int id){
        Log.i("UIImageViewLoader", "showerror");
        final Message message = new Message();
        message.what = ERROR;
        message.obj = id;
        handler.sendMessage(message);
    }

    private final static class LoadHandler extends Handler{

        private ImageView imageView;
        private SoftReference<BitmapRequest> reference;
        private String url;

        public LoadHandler(BitmapRequest request){
            super(Looper.getMainLooper());
            reference = new SoftReference<BitmapRequest>(request);
            imageView = reference.get().getImageView();
            url = reference.get().uri;
        }

        @Override
        public void handleMessage(Message msg) {
            if (reference.get() != null){
                Log.i("UIImageViewLoader", msg.what + " what");
                switch (msg.what){
                    case LOAD:
                        imageView.setImageBitmap((Bitmap)msg.obj);
                        break;
                    case LOADING:
                        imageView.setImageResource((int)msg.obj);
                        break;
                    case ERROR:
                        imageView.setImageResource((int)msg.obj);
                        break;
                    case LOAD_CACHE:
                        imageView.setImageBitmap((Bitmap)msg.obj);
                        break;
                }
            } else {
                Log.i("UIImageViewLoader", "reference is null");
            }
        }
    }
}
