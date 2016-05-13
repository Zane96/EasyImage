package com.example.zane.easyimageprovider.builder;

import com.example.zane.easyimageprovider.ImageProvider;

/**
 * Created by Zane on 16/5/11.
 * 主体类对于provide功能的构建类。开放修改
 */
public class ImageProviderBuilder {

    //是否调用剪裁
    boolean isCrop = false;
    //是否调用相机
    boolean isCamera = false;
    //是否调用相册
    boolean isAlbum = false;

    ImageProvider imageProvider;

    public ImageProviderBuilder(){

    }

    //------------------------------调用链-------------------------------------
    public ImageProviderBuilder useCrop(boolean isCrop){
        this.isCrop = isCrop;v
        return this;
    }

    public ImageProviderBuilder useCamera(boolean isCamera){
        this.isCamera = isCamera;
        return this;
    }

    public ImageProviderBuilder userAlbum(boolean isAlbum){
        this.isAlbum = isAlbum;
        return this;
    }


    //-----------------------------分割线--------------------------------------
    public EasyImageProvider builder(){
        return new EasyImageProvider(this);
    }

}
