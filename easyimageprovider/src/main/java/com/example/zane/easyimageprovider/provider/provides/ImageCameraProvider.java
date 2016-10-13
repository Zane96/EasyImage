package com.example.zane.easyimageprovider.provider.provides;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.zane.easyimageprovider.BuildConfig;
import com.example.zane.easyimageprovider.provider.ProviderRequestCode;
import com.example.zane.easyimageprovider.provider.listener.ImageProvider;
import com.example.zane.easyimageprovider.builder.EasyImageProvideRecord;
import com.example.zane.easyimageprovider.utils.TempImageFile;
import com.example.zane.easyimageprovider.utils.UriShemeChanger;

import java.io.File;

/**
 * Created by Zane on 16/5/13.
 */
public class ImageCameraProvider implements ImageProvider{

    private File tempFile;
    private Uri contentUri;
    private Context context;

    @Override
    public Intent getIntent(Context context) {
        this.context = context;
        tempFile = TempImageFile.createTempImageFile();
        Log.i("ImageCameraProvider", tempFile.getPath() + " path1");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //兼容7.0 api
        contentUri = UriShemeChanger.FileUri2ContentUri(context, tempFile);
        //intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("output", contentUri);
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
                    r.onGetImageListener.getDataBack(contentUri);
                }
            }else if (r.fragment == null){
                Log.i("ImageCameraProvider", context + " context");
                r.activity.startActivityForResult(r.imageCrop.getIntent(context, contentUri, r.outputX, r.outputY), r.imageCrop.getRequestCode());
            } else {
                r.fragment.startActivityForResult(r.imageCrop.getIntent(context, contentUri, r.outputX, r.outputY), r.imageCrop.getRequestCode());
            }
        } else {
            return;
        }
    }
}
