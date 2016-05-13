package com.example.zane.easyimageprovider.builder;

import android.graphics.Bitmap;

import com.example.zane.easyimageprovider.OnGetImageListener;

/**
 * Created by Zane on 16/5/13.
 */
public interface OnGetBitmapListener extends OnGetImageListener {
    void onImageLoaded(Bitmap bitmap);
}
