package com.example.zane.easyimageprovider.download.loader;

import android.content.Context;

import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;

/**
 * Created by Zane on 16/9/25.
 * Email: zanebot96@gmail.com
 */

public class ResourceLoader implements ImageLoader{

    private UIImageViewLoader loader;

    @Override
    public void loadImage(BitmapRequest request) {
        loader = new UIImageViewLoader(request);
        loader.showLoading(request.placeHolderId);
        final Context context = EasyImageLoadConfiguration.getInstance().getmApplicationContext();

        if (request.uri != null){
            loader.loadImageView(BitmapDecode.decodeRequestBitmap(context.getResources(), Integer.parseInt(request.uri), request.getImageViewWidth(), request.getImageViewHeight()));
        } else {
            loader.showError(request.placeHolderId);
        }
    }
}
