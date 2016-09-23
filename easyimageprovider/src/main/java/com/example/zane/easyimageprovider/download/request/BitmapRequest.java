package com.example.zane.easyimageprovider.download.request;

import com.example.zane.easyimageprovider.builder.EasyImageLoadRecord;
import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;

import java.util.Comparator;

/**
 * Created by Zane on 16/9/23.
 * Email: zanebot96@gmail.com
 */

//封装的请求类型Request,存入队列
public class BitmapRequest implements Comparator<BitmapRequest>{

    private EasyImageLoadRecord record;



    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return 0;
    }
}
