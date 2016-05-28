package com.example.zane.easyimageprovider.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.zane.easyimageprovider.ImageCrop;
import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.builder.EasyImageProvideRecord;

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
    public void onActivityResult(EasyImageProvideRecord r, Intent data) {
        if (r.onGetImageListener != null) {
            if (r.imageCrop == null) {
                if (r.isBitmapBack){
                    Bitmap bitmap = null;
                    try {
                        //获得绝对路径
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        Cursor cursor = r.context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        //获取照片路径
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        bitmap = BitmapFactory.decodeFile(picturePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    r.onGetImageListener.getDataBack(bitmap);
                } else {
                    r.onGetImageListener.getDataBack(data.getData());
                }
            } else {
                r.activity.startActivityForResult(r.imageCrop.getIntent(data.getData(), r.outputX, r.outputY), r.imageCrop.getRequestCode());
            }
        } else {
            return;
        }
    }
}
