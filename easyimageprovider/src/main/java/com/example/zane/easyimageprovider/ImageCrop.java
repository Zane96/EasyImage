package com.example.zane.easyimageprovider;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Zane on 16/5/14.
 * 提供给开发者自定义和系统默认 裁剪功能 的抽象接口，开放修改
 */
public interface ImageCrop {
    Intent getIntent(Uri uri, int outputX, int outputY);
    int getRequestCode();
    void onActivityResult(boolean isBitmapBack, OnGetImageListener listener, Intent data);
}
