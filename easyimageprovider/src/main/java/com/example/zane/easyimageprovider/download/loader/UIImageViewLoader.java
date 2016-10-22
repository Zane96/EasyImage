package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.cache.ImageCache;
import com.example.zane.easyimageprovider.download.execute.BitmapCallback;
import com.example.zane.easyimageprovider.download.execute.ContainerDrawable;
import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by Zane on 16/9/25.
 * Email: zanebot96@gmail.com
 */

//渲染以及
class UIImageViewLoader {

    private final BitmapRequest request;
    private final ImageCache cache;
    private final LoadHandler handler;

    private static final int LOAD = 1;
    private static final int LOADING = 2;
    private static final int ERROR = 3;
    private static final int LOAD_CACHE = 4;

    private static final String LOADING_ID = "loading_id";
    private static final String LOADING_CONTAINER = "loading_container";

    UIImageViewLoader(BitmapRequest request) {
        this.request = request;
        this.cache = request.cache;
        handler = new LoadHandler(request);
    }

    /**
     * 在load之前调用
     * @return true表示缓存中没有,false表示缓存中有
     */
    boolean beforeLoad(){
        Log.i("UIImageViewLoader", cache + " cache");
        LeasedDrawable drawable = cache.get(request.uri, request.getImageViewWidth(), request.getImageViewHeight());
        if (drawable != null){
            loadImageView(drawable);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 存入缓存。如果是存入Lru中,那么
     * @param drawable
     */
    private void setInCache(LeasedDrawable drawable){
        if (cache != null && request != null){
            synchronized (cache){
                cache.put(request.uri, drawable);
            }
        } else {
            Log.i("UIImageViewLoader", "cache is " + cache + " request is " + request);
        }
    }

    /**
     * load完毕之后调用,用来渲染
     * @param drawable
     */
    void loadImageView(LeasedDrawable drawable){
        setInCache(drawable);
        final Message message = new Message();
        message.what = LOAD;
        message.obj = drawable;
        handler.sendMessage(message);
    }

    /**
     * 在缓存中加载数据
     */
    void loadImageViewInCache(){
        final Message message = new Message();
        final LeasedDrawable drawable = cache.get(request.uri, request.getImageViewWidth(), request.getImageViewHeight());
        message.what = LOAD_CACHE;
        message.obj = drawable;
        handler.sendMessage(message);
    }

    /**
     * 加载过程中调用,显示占位图
     * @param id
     */
    void showLoading(Map<BitmapCallback, Future> container, int id){
        final Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = LOADING;
        bundle.putInt(LOADING_ID, id);
        bundle.putSerializable(LOADING_CONTAINER, (Serializable) container);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * 加载错误的时候调用,显示错误图片
     * @param id
     */
    void showError(int id){
        Log.i("UIImageViewLoader", "showerror");
        final Message message = new Message();
        message.what = ERROR;
        message.obj = id;
        handler.sendMessage(message);
    }

    private final class LoadHandler extends Handler{

        private final ImageView imageView;

        public LoadHandler(BitmapRequest request){
            super(Looper.getMainLooper());
            imageView = request.getImageView();
        }

        //loading的时候,将map<Callable, Future>注入到loading的Drawable里面去
        @Override
        public void handleMessage(Message msg) {
            Log.i("UIImageViewLoader", msg.what + " what");
            switch (msg.what){
                case LOAD:
                    LeasedDrawable drawable = (LeasedDrawable) msg.obj;
                    //增加一个引用
                    drawable.retain();
                    imageView.setImageDrawable(drawable);
                    break;
                case LOADING:
                    Bundle bundle = msg.getData();
                    Map<BitmapCallback, Future> container = (HashMap<BitmapCallback, Future>) bundle.getSerializable(LOADING_CONTAINER);
                    int id = bundle.getInt(LOADING_ID);
                    if (container != null) {
                        Bitmap bitmap = BitmapFactory.decodeResource(EasyImageLoadConfiguration.getInstance().getmApplicationContext().getResources(), id);
                        ContainerDrawable containerDrawable = new ContainerDrawable(EasyImageLoadConfiguration.getInstance().getmApplicationContext().getResources(), bitmap, container);
                        imageView.setImageDrawable(containerDrawable);
                    } else {
                        imageView.setImageResource(id);
                    }
                    break;
                case ERROR:
                    LeasedDrawable drawable2 = BitmapDecode.decodeRequestBitmap(EasyImageLoadConfiguration.getInstance().getmApplicationContext().getResources(),
                            (int)msg.obj, imageView.getWidth(), imageView.getHeight());
                    drawable2.retain();
                    imageView.setImageDrawable(drawable2);
                    break;
                case LOAD_CACHE:
                    imageView.setImageDrawable((LeasedDrawable) msg.obj);
                    break;
            }
        }
    }
}
