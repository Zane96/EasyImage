package com.example.zane.easyimageprovider.builder.core;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.zane.easyimageprovider.builder.EasyImageLoadRecord;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.EasyImageProvideRecord;
import com.example.zane.easyimageprovider.builder.record.ImageRecord;
import com.example.zane.easyimageprovider.provider.ProviderRequestCode;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public class EasyImageProvider implements EasyImage {

    private static final String TAG = "EasyImageProvider";

    private EasyImageProvideRecord r;

    public EasyImageProvider(ImageProviderBuilder builder) {
        r = new EasyImageProvideRecord(builder);
    }

    @Override
    public void execute() {

        if (r.activity != null){
            r.activity.startActivityForResult(r.imageProvider.getIntent(), r.imageProvider.getRequestCode());
        } else {
            r.activity = r.fragment.getActivity();
            r.fragment.startActivityForResult(r.imageProvider.getIntent(), r.imageProvider.getRequestCode());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, resultCode+" "+String.valueOf(data));
        if (resultCode == r.activity.RESULT_OK){
            if (requestCode != ProviderRequestCode.REQUEST_CORP) {
                r.imageProvider.onActivityResult(r, data);
            } else {
                r.imageCrop.onActivityResult(r.isBitmapBack, r.onGetImageListener, data);
            }
        } else {
            Toast.makeText(r.activity, "操作失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 返回这个提供类的记录类实例
     * @return
     */
    public EasyImageProvideRecord getProvideRecord(){
        return r;
    }
}
