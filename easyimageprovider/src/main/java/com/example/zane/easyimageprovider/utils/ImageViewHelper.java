package com.example.zane.easyimageprovider.utils;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

public final class ImageViewHelper {

    private static int DEFAULT_WIDTH = 200;
    private static int DEFAULT_HEIGHT = 200;
    private static int width = 0;
    private static int height = 0;

    /**
     * {@inheritDoc}
     * <p/>
     * Width is defined by target {@link ImageView view} parameters,
     * configuration parameters or device display dimensions.<br />
     * Size computing algorithm:<br />
     * 1) Get the actual drawn <b>getWidth()</b> of the View. If view haven't
     * drawn yet then go to step #2.<br />
     * 2) Get <b>layout_width</b>. If it hasn't exact value then go to step #3.<br />
     * 3) Get <b>maxWidth</b>.
     */

    public static int getImageViewWidth(final ImageView imageView) {
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    width = imageView.getWidth();
                    Log.i("ImageViewHelper", width + "in width");
                }
            });
            Log.i("ImageViewHelper", width + " width");
            return width;
        }
        return DEFAULT_WIDTH;
    }

    public static int getImageViewHeight(final ImageView imageView) {
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    height = imageView.getHeight();
                }
            });
            return height;
        }
        return DEFAULT_HEIGHT;
    }
//
//    private static int getImageViewFieldValue(Object object, String fieldName) {
//        int value = 0;
//        try {
//            Field field = ImageView.class.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            int fieldValue = (Integer) field.get(object);
//            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
//                value = fieldValue;
//            }
//        } catch (Exception e) {
//            Log.e("", e.getMessage());
//        }
//        return value;
//    }
}
