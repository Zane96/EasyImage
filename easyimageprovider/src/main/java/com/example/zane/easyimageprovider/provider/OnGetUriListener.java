package com.example.zane.easyimageprovider.provider;

import android.net.Uri;

/**
 * Created by Zane on 16/5/13.
 */
public interface OnGetUriListener extends OnGetImageListener{
    void onImageLoaded(Uri uri);
}
