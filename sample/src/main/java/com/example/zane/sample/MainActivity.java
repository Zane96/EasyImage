package com.example.zane.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
