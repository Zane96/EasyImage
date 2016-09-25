package com.example.zane.easyimageprovider.download.execute;

/**
 * Created by Zane on 16/9/25.
 * Email: zanebot96@gmail.com
 */

//callback实现,然后在线程池队列中提供一个优先级的比较
public interface ThreadPoolQueuePolicy {
    int getPolicy();
}
