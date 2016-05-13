package com.example.zane.easyimageprovider.provider;

import android.graphics.Bitmap;

/**
 * Created by Zane on 16/5/13.
 */
public interface OnGetBitmapListener extends OnGetImageListener{
    void onImageLoaded(Bitmap bitmap);
}
