package com.example.zane.easyimageprovider.download.execute;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

public class BitmapCallback implements Callable<Bitmap>{

    private BitmapRequest request;

    public BitmapCallback(BitmapRequest request){
        this.request  = request;
    }

    @Override
    public Bitmap call() throws Exception {
        final URL imgURL;
        Bitmap bitmap = null;
        InputStream is = null;
        try{
            imgURL = new URL(request.uri);
            HttpURLConnection conn = (HttpURLConnection) imgURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            is = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapDecode.decodeRequestBitmapFromIs(is, request.getImageViewWidth(), request.getImageViewHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            is.close();
        }
        return bitmap;
    }

}