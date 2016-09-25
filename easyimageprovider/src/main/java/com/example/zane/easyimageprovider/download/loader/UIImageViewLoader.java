package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;

import com.example.zane.easyimageprovider.download.cache.ImageCache;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

import java.lang.ref.Reference;
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
    private Message message;

    private static final int LOAD = 1;
    private static final int LOADING = 2;
    private static final int ERROR = 3;

    protected UIImageViewLoader(BitmapRequest request) {
        this.request = request;
        this.cache = request.cache;
        handler = new LoadHandler(request);
        message = new Message();
    }

    /**
     * 在load之前调用
     * @return true表示缓存中没有,false表示缓存中有
     */
    protected boolean beforeLoad(){
        if (cache.get(request.uri) != null){
            loadImageView(cache.get(request.uri));
            return false;
        } else {
            return true;
        }
    }

    private void setInCache(Bitmap bitmap){
        cache.put(request.uri, bitmap);
    }

    /**
     * load完毕之后调用,用来渲染
     * @param bitmap
     */
    protected void loadImageView(Bitmap bitmap){
        setInCache(bitmap);
        message.what = LOAD;
        message.obj = bitmap;
        handler.sendMessage(message);
    }

    /**
     * 加载过程中调用,显示占位图
     * @param id
     */
    protected void showLoading(int id){
        message.what = LOADING;
        message.obj = id;
        handler.sendMessage(message);
    }

    /**
     * 加载错误的时候调用,显示错误图片
     * @param id
     */
    protected void showError(int id){
        message.what = ERROR;
        message.obj = id;
        handler.sendMessage(message);
    }

    private final static class LoadHandler extends Handler{

        private int errorId;
        private int holderPlaceId;
        private WeakReference<BitmapRequest> reference;

        public LoadHandler(BitmapRequest request){
            super(Looper.getMainLooper());
            reference = new WeakReference<BitmapRequest>(request);
            errorId = reference.get().errorId;
            holderPlaceId = reference.get().placeHolderId;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOAD:

                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        }
    }
}
