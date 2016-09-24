package com.example.zane.easyimageprovider.download.loader;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//策略模式,将request分发到线程池或者本地加载中去
public interface ImageLoader {
    void loadImage(BitmapRequest request);
}
