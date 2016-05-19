package com.example.zane.easyimageprovider.builder.factory;

import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.core.EasyImageProvider;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public class ProviderFactory implements EasyImageFactory {

    private ImageProviderBuilder builder;

    public ProviderFactory(ImageProviderBuilder builder) {
        this.builder = builder;
    }

    @Override
    public EasyImage init() {
        return new EasyImageProvider(builder);
    }
}
