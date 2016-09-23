package com.example.zane.easyimageprovider.provider.provides;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.zane.easyimageprovider.provider.ProviderRequestCode;
import com.example.zane.easyimageprovider.provider.listener.ImageProvider;
import com.example.zane.easyimageprovider.builder.EasyImageProvideRecord;
import com.example.zane.easyimageprovider.utils.TempImageFile;

import java.io.File;

/**
 * Created by Zane on 16/5/13.
 */
public class ImageCameraProvider implements ImageProvider{

    private File tempFile;

    @Override
    public Intent getIntent() {
        tempFile = TempImageFile.createTempImageFile();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(tempFile));
        return intent;
    }

    @Override
    public int getRequestCode() {
        return ProviderRequestCode.REQUEST_CAMERA;
    }

    @Override
    public void onActivityResult(EasyImageProvideRecord r, Intent data) {
        if (r.onGetImageListener != null) {
            if (r.imageCrop == null) {
                if (r.isBitmapBack){
                    r.onGetImageListener.getDataBack(BitmapFactory.decodeFile(tempFile.getPath()));
                } else {
                    r.onGetImageListener.getDataBack(Uri.fromFile(tempFile));
                }
            }else if (r.fragment == null){
                r.activity.startActivityForResult(r.imageCrop.getIntent(Uri.fromFile(tempFile), r.outputX, r.outputY), r.imageCrop.getRequestCode());
            } else {
                r.fragment.startActivityForResult(r.imageCrop.getIntent(Uri.fromFile(tempFile), r.outputX, r.outputY), r.imageCrop.getRequestCode());
            }
        } else {
            return;
        }
    }
}
