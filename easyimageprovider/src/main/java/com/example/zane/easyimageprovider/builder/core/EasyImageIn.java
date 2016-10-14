package com.example.zane.easyimageprovider.builder.core;

import android.content.Intent;

/**
 * Created by Zane on 16/5/19.
 * Email: zanebot96@gmail.com
 */

public interface EasyImageIn {
    void execute();
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
