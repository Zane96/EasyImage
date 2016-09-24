package com.example.zane.easyimageprovider.download.dispatch;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

//请求队列的事件分发线程
public class RequestDispatch extends Thread{

    private AtomicInteger imageviewID = new AtomicInteger(0);

}
