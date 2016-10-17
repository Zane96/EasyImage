package com.example.zane.easyimageprovider.builder;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.zane.easyimageprovider.provider.listener.ImageCrop;
import com.example.zane.easyimageprovider.provider.listener.ImageProvider;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

/**
 * Created by Zane on 16/5/11.
 * imageprovider的记录类
 */
public class EasyImageProvideRecord{

    private final ImageProviderBuilder imageProviderBuilder;
    private boolean isUriBack;


    public Activity activity;
    public Fragment fragment;
    public boolean isBitmapBack;
    public int outputX;
    public int outputY;
    public ImageProvider imageProvider;
    public ImageCrop imageCrop;
    public Context context;
    public OnGetImageListener onGetImageListener;

    public EasyImageProvideRecord(ImageProviderBuilder builder){
        imageProviderBuilder = builder;
        initParams();
    }

    private void initParams(){

        boolean isFromCrop;
        boolean isFromAlbum;
        boolean isFromCamera;

        isFromCamera = imageProviderBuilder.isCamera;
        isFromAlbum = imageProviderBuilder.isAlbum;

        if (imageProviderBuilder.isCrop){
            isFromCrop = imageProviderBuilder.isCrop;
            outputX = imageProviderBuilder.outputX;
            outputY = imageProviderBuilder.outputY;
            imageCrop = imageProviderBuilder.imageCrop;
            if (!(outputX > 0) || !(outputY > 0)){
                throw new IllegalArgumentException("both of outputx and outputy cannot be negative!");
            }
        }

        if (!(isFromAlbum || isFromCamera)){
            throw new IllegalArgumentException("none of provide way be false!");
        }

        activity = imageProviderBuilder.activity;
        fragment = imageProviderBuilder.fragment;

        if (activity == null && fragment == null){
            throw new IllegalArgumentException("neither of provider's context be null!");
        } else if(activity != null && fragment != null){
            throw new IllegalArgumentException("neither of provider's context not be null!");
        }

        boolean isActivity = ((activity != null) && (fragment == null));
        context = isActivity ? activity : fragment.getActivity();

        if (imageProviderBuilder.isBitmapBack){
            isBitmapBack = true;
        } else {
            isUriBack = true;
        }

        imageProvider = imageProviderBuilder.imageProvider;
        onGetImageListener = imageProviderBuilder.listener;
    }

}
