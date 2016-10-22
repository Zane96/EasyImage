package com.example.zane.easyimageprovider.download.loader.recycle;

/**
 * 引用计数来判断里面的bitmap是否应该被recycle
 *
 * Created by Zane on 16/10/19.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface ILeasedDrawable {
    /**
     * 被引用了一次
     */
    void retain();

    /**
     * 被释放了一次
     */
    void recycle();
}
