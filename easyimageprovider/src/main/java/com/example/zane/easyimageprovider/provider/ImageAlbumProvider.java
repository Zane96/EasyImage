package com.example.zane.easyimageprovider.provider;

import android.content.Intent;
import android.provider.MediaStore;

import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;

/**
 * Created by Zane on 16/5/13.
 */
public class ImageAlbumProvider implements ImageProvider{

    @Override
    public Intent getIntent() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    @Override
    public int getRequestCode() {
        return ProviderRequestCode.REQUEST_ALBUM;
    }

    @Override
    public void onActivityResult(OnGetImageListener listener, int requestCode, int resultCode, Intent data) {

    }
}
