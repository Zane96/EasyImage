package com.example.zane.easyimageprovider.provider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.zane.easyimageprovider.ImageCrop;
import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.builder.EasyImageProvideRecord;
import com.example.zane.easyimageprovider.utils.BitmapDecode;
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
            }else {
                r.activity.startActivityForResult(r.imageCrop.getIntent(Uri.fromFile(tempFile), r.outputX, r.outputY), r.imageCrop.getRequestCode());
            }
        } else {
            return;
        }
    }
}
