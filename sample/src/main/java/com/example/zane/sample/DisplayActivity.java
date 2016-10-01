package com.example.zane.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zane.easyimageprovider.builder.ImageLoadBuidler;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.LoadFactory;

/**
 * Created by Zane on 16/10/1.
 * Email: zanebot96@gmail.com
 */

public class DisplayActivity extends AppCompatActivity{

    private EasyImage easyImage;
    private EasyImageFactory factory;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView[] images;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_layout);

        images = new ImageView[7];

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        image6 = (ImageView) findViewById(R.id.image6);
        image7 = (ImageView) findViewById(R.id.image7);
        images[0] = image1;
        images[1] = image2;
        images[2] = image3;
        images[3] = image4;
        images[4] = image5;
        images[5] = image6;
        images[6] = image7;

        for (int i = 0; i < 7; i++){
            ImageLoadBuidler buidler = new ImageLoadBuidler()
                    .with(this)
                    .useNoCache()
                    .setHolderPlace(R.drawable.ic_launcher)
                    .setError(R.drawable.ic_launcher)
                    .load(Data.URLS[i])
                    .into(images[i]);
            factory = new LoadFactory(buidler);
            easyImage = factory.init();
            easyImage.execute();
        }
    }
}
