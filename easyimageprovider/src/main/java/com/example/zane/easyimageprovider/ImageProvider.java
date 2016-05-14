package com.example.zane.easyimageprovider;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Zane on 16/5/11.
 */
public interface ImageProvider {
    Intent getIntent();
    int getRequestCode();
    void onActivityResult(Context context, boolean isBitmapBack, OnGetImageListener listener, Intent data);
}
