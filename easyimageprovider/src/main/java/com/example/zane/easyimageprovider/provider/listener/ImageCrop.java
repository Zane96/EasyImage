package com.example.zane.easyimageprovider.provider.listener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

/**
 * Created by Zane on 16/5/14.
 * 提供给开发者自定义和系统默认 裁剪功能 的抽象接口，开放修改
 */
public interface ImageCrop {
    Intent getIntent(Context context, Uri uri, int outputX, int outputY);
    int getRequestCode();
    void onActivityResult(boolean isBitmapBack, OnGetImageListener listener, Intent data);
}
