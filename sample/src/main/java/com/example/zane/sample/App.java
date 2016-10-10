package com.example.zane.sample;

import android.app.Application;

import com.example.zane.easyimageprovider.download.EasyImageLoadConfiguration;
import com.example.zane.easyimageprovider.download.policy.FIFOPolicy;
import com.example.zane.easyimageprovider.download.policy.FILOPolicy;

/**
 * Created by Zane on 16/9/29.
 * Email: zanebot96@gmail.com
 */

public class App extends Application{

    int threadCount = Runtime.getRuntime().availableProcessors() + 1;

    @Override
    public void onCreate() {
        super.onCreate();
        EasyImageLoadConfiguration.getInstance()
                .setLoadPolicy(new FIFOPolicy())
                .setThreadCount(10)
                .init(this);
    }
}
