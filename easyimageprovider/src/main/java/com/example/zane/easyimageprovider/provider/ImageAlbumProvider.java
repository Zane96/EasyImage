package com.example.zane.easyimageprovider.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;

import java.io.File;

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
    public void onActivityResult(Context context, boolean isBitmapBack, OnGetImageListener listener, Intent data) {
        if (isBitmapBack){
            Bitmap bitmap = null;
            try {
                //获得绝对路径
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                //获取照片路径
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                bitmap = BitmapFactory.decodeFile(picturePath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            listener.getDataBack(bitmap);
        } else {
            listener.getDataBack(data.getData());
        }
    }
}
