package com.example.zane.easyimageprovider.builder.core;

import android.content.Intent;

import com.example.zane.easyimageprovider.builder.EasyImageProvideRecord;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public class EasyImageProvider implements EasyImage {

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
        if (resultCode == r.activity.RESULT_OK){
            r.imageProvider.onActivityResult(r.context, r.isBitmapBack, r.onGetImageListener, data);
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
