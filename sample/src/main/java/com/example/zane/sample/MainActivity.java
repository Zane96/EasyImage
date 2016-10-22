package com.example.zane.sample;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.core.EasyImageIn;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.ProviderFactory;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

public class MainActivity extends AppCompatActivity {

    private Button buttonCamera;
    private Button buttonAlbum;
    private ImageView imageViewProvide;
    private Button mButton;
    private Button mButton2;

    private EasyImage easyImage;

    private static final int PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.VERSION_CODE >= 23){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
            }
        }

        buttonCamera = (Button)findViewById(R.id.button_camera);
        buttonAlbum = (Button)findViewById(R.id.button_album);
        imageViewProvide = (ImageView)findViewById(R.id.imageview);
        mButton = (Button) findViewById(R.id.button2);
        mButton2 = (Button) findViewById(R.id.button3);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCamera();
                easyImage.execute();
            }
        });

        buttonAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAlbum();
                easyImage.execute();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DisplayActivity.class));
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "拒绝授权", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 相册中获取图片
     */
    private void initAlbum(){
        easyImage = EasyImage.creat(new ImageProviderBuilder().with(this).useAlbum().useCrop(500,500)
            .setGetImageListener("bitmap", new OnGetImageListener() {
                @Override
                public void getDataBack(Object o) {
                   imageViewProvide.setImageBitmap((Bitmap) o);
             }
        }));
    }

    /**
     * 相机中获取图片
     */
    private void initCamera() {
        easyImage = EasyImage.creat(new ImageProviderBuilder().with(this).useCamera().useCrop(500,500)
            .setGetImageListener("bitmap", new OnGetImageListener() {
                @Override
                public void getDataBack(Object o) {
                    imageViewProvide.setImageBitmap((Bitmap) o);
                }
            }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.onActivityResult(requestCode, resultCode, data);
    }
}
