package com.example.zane.easyimageprovider.provider.provides;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.zane.easyimageprovider.provider.ProviderRequestCode;
import com.example.zane.easyimageprovider.provider.listener.ImageCrop;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;
import com.example.zane.easyimageprovider.utils.TempImageFile;

import java.io.File;

/**
 * Created by Zane on 16/5/13.
 */
public class ImageCropProvider implements ImageCrop{

    private File tempFile;
    private Uri contentUri;

    @Override
    public Intent getIntent(Context context, Uri uri, int x, int y) {
        tempFile = TempImageFile.createTempImageFile();
        //contentUri = UriShemeChanger.FileUri2ContentUri(context, tempFile);
        Log.i("ImageCrop", uri + " uri");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", x);
        intent.putExtra("outputY", y);
        intent.putExtra("scale", true);
        //注意这里crop不能提供content uri,不然会报安全异常(提示FileProvider的exported不为true,本来也不应该为true)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        return intent;
    }

    @Override
    public int getRequestCode() {
        return ProviderRequestCode.REQUEST_CORP;
    }

    @Override
    public void onActivityResult(boolean isBitmapBack, OnGetImageListener listener, Intent data) {
        if (listener != null) {
            if (isBitmapBack){
                listener.getDataBack(BitmapFactory.decodeFile(tempFile.getPath()));
            } else {
                listener.getDataBack(Uri.fromFile(tempFile));
            }
        }
    }
}
