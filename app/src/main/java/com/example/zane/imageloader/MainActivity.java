package com.example.zane.imageloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.factory.LoadFactory;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.ProviderFactory;

public class MainActivity extends AppCompatActivity {

    private Button buttonProvide;
    private ImageView imageViewProvide;
    private EasyImage easyImageProvider;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonProvide = (Button)findViewById(R.id.button);
        imageViewProvide = (ImageView)findViewById(R.id.imageview);
        mButton = (Button) findViewById(R.id.button2);

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
                .setGetImageListener("bitmap", (bitmap) -> imageViewProvide.setImageBitmap((Bitmap)bitmap));
        EasyImageFactory factory = new ProviderFactory(builder);
        easyImageProvider = factory.init();
        buttonProvide.setOnClickListener((v) -> easyImageProvider.execute());

        mButton.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, ListActivity.class)));

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
