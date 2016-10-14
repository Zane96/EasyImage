package com.example.zane.easyimageprovider.builder.factory;

import com.example.zane.easyimageprovider.builder.core.EasyImageIn;
import com.example.zane.easyimageprovider.builder.core.EasyImageInProvider;
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
    public EasyImageIn init() {
        return new EasyImageInProvider(builder);
    }
}
