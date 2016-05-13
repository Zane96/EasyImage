package com.example.zane.easyimageprovider.provider;

import android.content.Intent;
import android.net.Uri;

import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;

import java.io.File;

/**
 * Created by Zane on 16/5/13.
 */
public class ImageCameraProvider implements ImageProvider{

    private File tempFile;

    @Override
    public Intent getIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(tempFile));
        return intent;
    }

    @Override
    public int getRequestCode() {
        return ProviderRequestCode.REQUEST_CAMERA;
    }

    @Override
    public void onActivityResult(OnGetImageListener listener, int requestCode, int resultCode, Intent data) {

    }
}
