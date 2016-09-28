package com.example.zane.easyimageprovider.builder.factory;

import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.core.EasyImageLoad;
import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public class LoadFactory implements EasyImageFactory {

    private ImageLoadBuidler buidler;

    public LoadFactory(ImageLoadBuidler buidler) {
        this.buidler = buidler;
    }

    @Override
    public EasyImage init() {
        return EasyImageLoad.newInstance(buidler);
    }
}
