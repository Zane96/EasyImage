package com.example.zane.easyimageprovider.builder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.zane.easyimageprovider.ImageCache;
import com.example.zane.easyimageprovider.ImageProvider;
import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.provider.ProviderRequestCode;

/**
 * Created by Zane on 16/5/5.
 * 主体类
 */
public class EasyImageProvider_chang {

    //provider
    private boolean isFromCrop;
    private boolean isFromAlbum;
    private boolean isFromCamera;
    private boolean isBitmapBack;
    private boolean isUriBack;
    int outputX;
    int outputY;
    //load
    private boolean isLruCache;
    private boolean isDiskCache;
    private boolean isDoubleCache;

    private boolean isProvider = false;
    private boolean isLoad = false;

    private ImageCache imageCache;
    private ImageProvider imageProvider;

    private ImageProviderBuilder imageProviderBuilder;
    private ImageLoadBuidler imageLoadBuidler;

    //开发者回调接口
    private OnGetImageListener onGetImageListener;

    private static final int REQUEST_CAMERA = ProviderRequestCode.REQUEST_CAMERA;
    private static final int REQUEST_ALBUM = ProviderRequestCode.REQUEST_ALBUM;
    private static final int REQUEST_CROP = ProviderRequestCode.REQUEST_CORP;

    private Context context;
    private Activity activity;
    private Fragment fragment;

    public EasyImageProvider_chang(ImageProviderBuilder imageProviderBuilder){
        this.imageProviderBuilder = imageProviderBuilder;
        initProvider(imageProviderBuilder);
        isProvider = true;
    }

    public EasyImageProvider_chang(ImageLoadBuidler imageLoadBuidler){
        this.imageLoadBuidler = imageLoadBuidler;
        isLoad = true;
    }

    /**q
     * provider init
     * @param imageProviderBuilder
     */
    private void initProvider(ImageProviderBuilder imageProviderBuilder){

        isFromCamera = imageProviderBuilder.isCamera;
        isFromAlbum = imageProviderBuilder.isAlbum;
        imageProvider = imageProviderBuilder.imageProvider;

        if (imageProvider == null){
            throw new IllegalArgumentException("ImageProvider interface cannot be null!");
        }

        if (imageProviderBuilder.isCrop){
            isFromCrop = imageProviderBuilder.isCrop;
            outputX = imageProviderBuilder.outputX;
            outputY = imageProviderBuilder.outputY;
        }

        if (!(isFromAlbum || isFromCrop || isFromCamera)){
            throw new IllegalArgumentException("none of provide way be false!");
        }

        if (!(outputX > 0) || !(outputY > 0)){
            throw new IllegalArgumentException("both of outputx and outputy cannot be negative!");
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

        onGetImageListener = imageProviderBuilder.listener;
        if (imageProviderBuilder.isBitmapBack){
            isBitmapBack = true;
        } else {
            isUriBack = true;
        }

    }

    public void execute(){
        if (isProvider){
            if (activity != null){
                activity.startActivityForResult(imageProvider.getIntent(), imageProvider.getRequestCode());
            } else {
                activity = fragment.getActivity();
                fragment.startActivityForResult(imageProvider.getIntent(), imageProvider.getRequestCode());
            }
        }
    }

    //------------------------------------provider------------------------------------------
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == activity.RESULT_OK){
            if (onGetImageListener != null){
                switch (requestCode){
                    case REQUEST_CAMERA:

                        break;
                    case REQUEST_ALBUM:

                        break;
                    case REQUEST_CROP:

                        break;
                }
            }
        }
    }

}
