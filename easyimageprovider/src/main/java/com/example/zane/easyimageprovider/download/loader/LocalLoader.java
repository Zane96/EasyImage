package com.example.zane.easyimageprovider.download.loader;

import android.net.Uri;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;

import java.io.File;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

public class LocalLoader implements ImageLoader{

    @Override
    public void loadImage(BitmapRequest request) {

        UIImageViewLoader loader = new UIImageViewLoader(request);
        loader.showLoading(null, request.placeHolderId);
        final String imagePath = Uri.parse(request.uri).getPath();
        final File imgFile = new File(imagePath);
        if (!imgFile.exists()) {
            loader.showError(request.errorId);
        }

        loader.loadImageView(BitmapDecode.decodeRequestBitmap(imagePath, request.getImageViewWidth(), request.getImageViewHeight()));
    }
}
