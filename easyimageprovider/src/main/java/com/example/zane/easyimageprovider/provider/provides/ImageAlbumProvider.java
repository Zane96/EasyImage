package com.example.zane.easyimageprovider.provider.provides;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.zane.easyimageprovider.provider.ProviderRequestCode;
import com.example.zane.easyimageprovider.provider.listener.ImageProvider;
import com.example.zane.easyimageprovider.builder.EasyImageProvideRecord;

/**
 * Created by Zane on 16/5/13.
 */
public class ImageAlbumProvider implements ImageProvider{

    private Context context;

    @Override
    public Intent getIntent(Context context) {
        this.context = context;
        Log.i("ImageAlbumProvider", context + " getIntent context");
        Intent intent = new Intent("android.intent.action.PICK");
        //这里不用做7.0的兼容,已经是content:///
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
            } else if (r.fragment == null){
                Log.i("ImageAlbumProvider", context + " context");
                r.activity.startActivityForResult(r.imageCrop.getIntent(context, data.getData(), r.outputX, r.outputY), r.imageCrop.getRequestCode());
            } else {
                r.fragment.startActivityForResult(r.imageCrop.getIntent(context, data.getData(), r.outputX, r.outputY), r.imageCrop.getRequestCode());
            }
        } else {
            return;
        }
    }
}
