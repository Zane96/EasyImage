package com.example.zane.easyimageprovider.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Zane on 16/10/13.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

//通过ContentResolve来更新和获取我们需要访问的Image文件的content URI
public final class UriShemeChanger {

    public static Uri FileUri2ContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        //如果File path之前不存在
        if (cursor != null && cursor.moveToFirst()) {
            //获得file uri的id
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            //将content Uri的baseUri和之前获得的ID拼接在一起
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                //构建一个ContextValues存入ContentResolver,然后获得返回的content Uri
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}
