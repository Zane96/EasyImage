package com.example.zane.easyimageprovider.builder;

import android.app.Activity;
import android.support.v4.app.Fragment;

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

    ImageProvider imageProvider;
    OnGetImageListener listener;

    Activity activity;
    Fragment fragment;

    public ImageProviderBuilder(){

    }

    //------------------------------调用链-------------------------------------

    /**
     * crop
     * @param isCrop
     * @return
     */
    public ImageProviderBuilder useCrop(boolean isCrop){
        this.isCrop = isCrop;
        return this;
    }

    /**
     * camera
     * @param isCamera
     * @return
     */
    public ImageProviderBuilder useCamera(boolean isCamera){
        this.isCamera = isCamera;
        imageProvider = new ImageCameraProvider();
        return this;
    }

    /**
     * album
     * @param isAlbum
     * @return
     */
    public ImageProviderBuilder userAlbum(boolean isAlbum){
        this.isAlbum = isAlbum;
        imageProvider = new ImageAlbumProvider();
        return this;
    }

    /**
     * 获得bitmap返回
     * @param listener
     * @return
     */
    public ImageProviderBuilder getBitmapBack(OnGetBitmapListener listener){
        this.listener = listener;
        return this;
    }

    /**
     *获得uri返回
     * @param listener
     * @return
     */
    public ImageProviderBuilder getUriBack(OnGetUriListener listener){
        this.listener = listener;
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
    public EasyImageProvider builder(){
        return new EasyImageProvider(this);
    }

}
