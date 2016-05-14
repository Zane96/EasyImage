package com.example.zane.easyimageprovider;

import android.net.Uri;

/**
 * Created by Zane on 16/5/13.
 * 回调抽象
 */
public interface OnGetImageListener<T> {
    /**
     * t: bitmap or uri
     * @param t
     */
    void getDataBack(T t);
}
