package com.example.zane.easyimageprovider.builder.core;

import android.content.Intent;

import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.core.EasyImage;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public class EasyImageLoad implements EasyImage {

    private ImageLoadBuidler buidler;

    public EasyImageLoad(ImageLoadBuidler buidler) {
        this.buidler = buidler;
    }

    @Override
    public void execute() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
