package com.example.zane.easyimageprovider.provider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.zane.easyimageprovider.ImageCrop;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.utils.TempImageFile;

import java.io.File;

/**
 * Created by Zane on 16/5/13.
 */
public class ImageCropProvider implements ImageCrop{

    private File tempFile;

    @Override
    public Intent getIntent(Uri uri, int x, int y) {
        tempFile = TempImageFile.createTempImageFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", x);
        intent.putExtra("outputY", y);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        return intent;
    }

    @Override
    public int getRequestCode() {
        return ProviderRequestCode.REQUEST_CORP;
    }

    @Override
    public void onActivityResult(boolean isBitmapBack, OnGetImageListener listener, Intent data) {
        if (isBitmapBack){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = bundle.getParcelable("data");
            listener.getDataBack(bitmap);
        } else {
            listener.getDataBack(data.getData());
        }
    }
}
