package com.example.zane.easyimageprovider.builder.core;

import android.content.Intent;

import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.core.EasyImage;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public class EasyImageProvider implements EasyImage {

    ImageProviderBuilder builder;

    public EasyImageProvider(ImageProviderBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void execute() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
