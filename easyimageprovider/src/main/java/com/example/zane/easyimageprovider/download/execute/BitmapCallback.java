package com.example.zane.easyimageprovider.download.execute;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.zane.easyimageprovider.download.loader.recycle.LeasedDrawable;
import com.example.zane.easyimageprovider.download.request.BitmapRequest;
import com.example.zane.easyimageprovider.utils.BitmapDecode;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import static com.example.zane.easyimageprovider.utils.BitmapDecode.InputStreamTOByte;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

public class BitmapCallback implements Callable<LeasedDrawable>, ThreadPoolQueuePolicy{

    private final BitmapRequest request;

    public BitmapCallback(BitmapRequest request){
        this.request  = request;
    }

    @Override
    public LeasedDrawable call() throws Exception {
        final URL imgURL;
        LeasedDrawable drawable = null;
        InputStream is = null;
        InputStream finalStream = null;
        InputStream inputStream = null;

        HttpURLConnection conn = null;
        try{
            imgURL = new URL(request.uri);
            Log.i("BitmapCallback", imgURL + " url");
            conn = (HttpURLConnection) imgURL.openConnection();
            conn.setConnectTimeout(10000);
            //conn.setRequestMethod("GET");
            is = conn.getInputStream();

            byte[] data = InputStreamTOByte(is);
            finalStream = BitmapDecode.byteTOInputStream(data);
            inputStream = BitmapDecode.byteTOInputStream(data);

            Log.i("BitmapCallback", conn.getInputStream() + " is " + request.ID);
            //高效加载
            drawable = BitmapDecode.decodeRequestBitmap(inputStream, finalStream, request.getImageViewWidth(), request.getImageViewHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                is.close();
            }
            if (finalStream != null){
                finalStream.close();
            }
            if (inputStream != null){
                inputStream.close();
            }
            if (conn != null){
                conn.disconnect();
            }
        }
        return drawable;
    }

    @Override
    public int getPolicy() {
        return request.ID;
    }

    /**
     * 提供task的url
     * @return
     */
    public String getTaskUrl(){
        return request.uri;
    }
}
