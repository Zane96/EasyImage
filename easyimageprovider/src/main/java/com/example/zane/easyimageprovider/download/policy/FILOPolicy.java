package com.example.zane.easyimageprovider.download.policy;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;

/**
 * Created by Zane on 16/9/23.
 * Email: zanebot96@gmail.com
 */

//先进后出策略

public class FILOPolicy implements ImageLoadPolicy{
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return 0;
    }
}
