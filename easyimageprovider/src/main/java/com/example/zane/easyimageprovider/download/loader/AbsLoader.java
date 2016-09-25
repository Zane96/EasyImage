package com.example.zane.easyimageprovider.download.loader;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;

/**
 * Created by Zane on 16/9/25.
 * Email: zanebot96@gmail.com
 */

public abstract class AbsLoader implements ImageLoader{

    @Override
    public void loadImage(BitmapRequest request) {

    }

    public abstract Bitmap onLoadImageView()
}
