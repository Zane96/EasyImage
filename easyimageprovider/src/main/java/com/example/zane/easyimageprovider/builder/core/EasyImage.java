package com.example.zane.easyimageprovider.builder.core;

import android.content.Intent;

import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.LoadFactory;
import com.example.zane.easyimageprovider.builder.factory.ProviderFactory;

/**
 * Created by Zane on 16/10/14.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public final class EasyImage {

    private static EasyImageFactory factory;
    private final EasyImageIn easyImageIn;

    private EasyImage(){
        this.easyImageIn = factory.init();
    }

    /**
     * 接受图片提供的Builder类
     * @param builder
     * @return
     */
    public static EasyImage creat(ImageProviderBuilder builder){
        factory = new ProviderFactory(builder);
        return new EasyImage();
    }

    /**
     * 接受图片加载的Builder类
     * @param builder
     * @return
     */
    public static EasyImage creat(ImageLoadBuidler builder){
        factory = new LoadFactory(builder);
        return new EasyImage();
    }

    /**
     *相当于代理的转发
     */
    public void execute(){
        easyImageIn.execute();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        easyImageIn.onActivityResult(requestCode, resultCode, data);
    }
}
