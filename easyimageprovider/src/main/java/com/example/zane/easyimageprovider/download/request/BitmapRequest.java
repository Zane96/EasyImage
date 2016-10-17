package com.example.zane.easyimageprovider.download.request;

import android.util.Log;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.builder.EasyImageLoadRecord;
import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.cache.ImageCache;
import com.example.zane.easyimageprovider.download.policy.ImageLoadPolicy;
import com.example.zane.easyimageprovider.utils.ImageViewHelper;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//封装的请求类型Request ,存入分发队列
public class BitmapRequest implements Comparable<BitmapRequest>{

    private final EasyImageLoadRecord r;
    private Reference<ImageView> imageViewReference;
    private ImageLoadPolicy policy;

    public String uri;
    //非net加载的时候使用,resource/file
    public String uriHead;
    //请求ID = thread ID
    public int ID;
    public ImageCache cache;
    public int errorId;
    public int placeHolderId;


    public BitmapRequest(EasyImageLoadRecord r){
        this.r = r;
        init();
    }

    private void init(){
        policy = EasyImageLoadConfiguration.getInstance().getLoadPolicy();
        uri = r.uri;
        uriHead = r.uriHead;
        r.imageView.setTag(uri);
        imageViewReference = new WeakReference<ImageView>(r.imageView);
        cache = r.imageCache;
        errorId = r.errorId;
        placeHolderId = r.holderPlaceId;
        ID = r.ID;

        Log.i("BitmapRequest", ID + " ID");
    }

    @Override
    public int compareTo(BitmapRequest another) {
        return policy.compare(this, another);
    }

    public ImageView getImageView() {
        return imageViewReference.get();
    }

    public int getImageViewWidth() {
        return ImageViewHelper.getImageViewWidth(imageViewReference.get());
    }

    public int getImageViewHeight() {
        return ImageViewHelper.getImageViewHeight(imageViewReference.get());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        result = prime * result + ((imageViewReference == null) ? 0 : imageViewReference.get().hashCode());
        result = prime * result + ID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        if (imageViewReference == null) {
            if (other.imageViewReference != null)
                return false;
        } else if (!imageViewReference.get().equals(other.imageViewReference.get())){
            return false;
        }else if (ID != other.ID) {
            return false;
        }
        return true;
    }
}
