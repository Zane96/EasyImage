package com.example.zane.easyimageprovider.download.policy;

import com.example.zane.easyimageprovider.download.execute.LoadTask;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;

/**
 * Created by Zane on 16/9/23.
 * Email: zanebot96@gmail.com
 */

public interface ImageLoadPolicy {
    int compare(BitmapRequest request1, BitmapRequest request2);
}
