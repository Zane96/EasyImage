package com.example.zane.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.ProviderFactory;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

public class MainActivity extends AppCompatActivity {

    private Button buttonProvide;
    private ImageView imageViewProvide;
    private EasyImage easyImageProvider;
    private Button mButton;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonProvide = (Button)findViewById(R.id.button);
        imageViewProvide = (ImageView)findViewById(R.id.imageview);
        mButton = (Button) findViewById(R.id.button2);
        mButton2 = (Button) findViewById(R.id.button3);

        OnGetImageListener<Bitmap> listener = new OnGetImageListener<Bitmap>() {
            @Override
            public void getDataBack(Bitmap bitmap) {
                imageViewProvide.setImageBitmap(bitmap);
            }
        };
        ImageProviderBuilder builder = new ImageProviderBuilder()
                                               .with(this)
                                               .useCamera()
                                               //.setGetImageListener("uri", (uri) -> Toast.makeText(this, String.valueOf((Uri) uri), Toast.LENGTH_SHORT).show())
                                               .useCrop(200, 200)
                                               //.setGetImageListener("bitmap", listener)
                                               .setGetImageListener("bitmap", listener);
        EasyImageFactory factory = new ProviderFactory(builder);
        easyImageProvider = factory.init();
        buttonProvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

//        //-----------------------------------Load---------------------
//        ImageLoadBuidler buidlerLoad = new ImageLoadBuidler()
//                .with(this)
//                //.load("https://avatars.githubusercontent.com/u/12124524?v=3")
//                                               .load(R.drawable.avatar)
//                .setError(R.drawable.ic_launcher)
//                .setHolderPlace(R.drawable.ic_launcher)
//                .useLruCache()
//                .into(imageViewLoad);
//        EasyImageFactory factoryLoad = new LoadFactory(buidlerLoad);
//        easyImageLoad = factoryLoad.init();
//        buttonLoad.setOnClickListener((v) -> easyImageLoad.execute());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImageProvider.onActivityResult(requestCode, resultCode, data);
    }
}
