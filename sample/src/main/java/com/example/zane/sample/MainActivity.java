package com.example.zane.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.ProviderFactory;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

public class MainActivity extends AppCompatActivity {

    private Button buttonCamera;
    private Button buttonAlbum;
    private ImageView imageViewProvide;
    private EasyImage easyImageProvider;
    private Button mButton;
    private Button mButton2;

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
                easyImageProvider.execute();
            }
        });

        buttonAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAlbum();
                easyImageProvider.execute();
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

    private void initAlbum(){
        ImageProviderBuilder buidler = new ImageProviderBuilder()
                .with(this)
                .useAlbum()
                .useCrop(500, 500)
                .setGetImageListener("bitmap", new OnGetImageListener() {
                    @Override
                    public void getDataBack(Object o) {
                        imageViewProvide.setImageBitmap((Bitmap) o);
                    }
                });

        EasyImageFactory factory = new ProviderFactory(buidler);
        easyImageProvider = factory.init();

    }

    private void initCamera() {
        OnGetImageListener<Bitmap> listener = new OnGetImageListener<Bitmap>() {
            @Override
            public void getDataBack(Bitmap bitmap) {
                Log.i("MainActivity2", bitmap + " bitmap");
                imageViewProvide.setImageBitmap(bitmap);
            }
        };
        ImageProviderBuilder builder = new ImageProviderBuilder()
                                               .with(this)
                                               .useCamera()
                                               //.setGetImageListener("uri", (uri) -> Toast.makeText(this, String.valueOf((Uri) uri), Toast.LENGTH_SHORT).show())
                                               .useCrop(500, 500)
                                               //.setGetImageListener("bitmap", (bitmap) -> imageViewProvide.setImageBitmap((Bitmap)bitmap));
                                                .setGetImageListener("bitmap", listener);

        EasyImageFactory factory = new ProviderFactory(builder);
        easyImageProvider = factory.init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImageProvider.onActivityResult(requestCode, resultCode, data);
    }
}
