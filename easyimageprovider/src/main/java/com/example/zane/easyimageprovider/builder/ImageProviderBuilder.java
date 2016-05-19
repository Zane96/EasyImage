package com.example.zane.easyimageprovider.builder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.zane.easyimageprovider.ImageCrop;
import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.provider.ImageAlbumProvider;
import com.example.zane.easyimageprovider.provider.ImageCameraProvider;
import com.example.zane.easyimageprovider.provider.ImageCropProvider;

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
    //是否返回bitmap
    boolean isBitmapBack = false;
    //是否返回Uri
    boolean isUriBack = false;
    //crop x, y
    int outputX;
    int outputY;

    ImageProvider imageProvider;
    ImageCrop imageCrop;
    OnGetImageListener listener;

    Activity activity;
    Fragment fragment;

    public ImageProviderBuilder(){

    }

    //------------------------------调用链-------------------------------------

    /**
     * crop
     * @param outputX
     * @param outputY
     * @return
     */
    public ImageProviderBuilder useCrop(int outputX, int outputY){
        this.isCrop = true;
        imageCrop = new ImageCropProvider();
        this.outputX = outputX;
        this.outputY = outputY;
        return this;
    }

    /**
     * custom crop
     * 开发者的自定义剪裁类必须要继承ImageCrop接口
     * @param imageCrop
     * @param outputX
     * @param outputY
     * @return
     */
    public ImageProviderBuilder useCustomCrop(ImageCrop imageCrop, int outputX, int outputY){
        this.isCrop = true;
        this.outputX = outputX;
        this.outputY = outputY;
        return this;
    }

    /**
     * camera
     * @return
     */
    public ImageProviderBuilder useCamera(){
        this.isCamera = true;
        imageProvider = new ImageCameraProvider();
        return this;
    }

    /**
     * album
     * @return
     */
    public ImageProviderBuilder userAlbum(){
        this.isAlbum = true;
        imageProvider = new ImageAlbumProvider();
        return this;
    }



    /**
     * 设置回调接口，用户设置是需要返回bitmap还是uri
     * @param dataType "bitmap", "uri"
     * @param listener
     * @return
     */
    public ImageProviderBuilder setGetImageListener(String dataType, OnGetImageListener listener){
        this.listener = listener;
        if (dataType.equals("bitmap")){
            isBitmapBack = true;
        } else if (dataType.equals("uri")){
            isUriBack = true;
        } else {
            throw new IllegalArgumentException("returns the data type must be bitmap or uri!");
        }
        return this;
    }

    /**
     * 获得activity的环境
     * @param activity
     * @return
     */
    public ImageProviderBuilder with(Activity activity){
        this.activity = activity;
        return this;
    }

    /**
     * 获得fragment的环境
     * @param fragment
     * @return
     */
    public ImageProviderBuilder with(Fragment fragment){
        this.fragment = fragment;
        return this;
    }

    //-----------------------------分割线--------------------------------------
//    public EasyImageProvider_chang build(){
//        return new EasyImageProvider_chang(this);
//    }

}
