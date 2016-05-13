package com.example.zane.easyimageprovider;

import android.net.Uri;

/**
 * Created by Zane on 16/5/13.
 * 回调抽象
 */
public interface OnGetImageListener {
    void getDataBack(Uri uri);
}
