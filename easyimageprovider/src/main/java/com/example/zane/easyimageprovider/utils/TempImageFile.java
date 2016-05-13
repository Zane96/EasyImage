package com.example.zane.easyimageprovider.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Zane on 16/5/11.
 */
public final class TempImageFile {

    private static final File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    public static File createTempImageFile(){
        dir.mkdir();
        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
