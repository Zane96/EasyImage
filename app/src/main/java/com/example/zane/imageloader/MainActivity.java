package com.example.zane.imageloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zane.easyimageprovider.OnGetImageListener;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.builder.factory.EasyImageFactory;
import com.example.zane.easyimageprovider.builder.factory.ProviderFactory;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private EasyImage easyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.imageview);

        OnGetImageListener<Bitmap> listener = new OnGetImageListener<Bitmap>() {
            @Override
            public void getDataBack(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        };
        ImageProviderBuilder builder = new ImageProviderBuilder()
                .userAlbum()
                .with(this)
                .setGetImageListener("bitmap", listener);
        EasyImageFactory factory = new ProviderFactory(builder);
        easyImage = factory.init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyImage.execute();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.onActivityResult(requestCode, resultCode, data);
    }
}
