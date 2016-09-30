package com.example.zane.easyimageprovider.utils;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by Zane on 16/9/24.
 * Email: zanebot96@gmail.com
 */

public class ImageViewHelper {

    private static int DEFAULT_WIDTH = 200;
    private static int DEFAULT_HEIGHT = 200;

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
    public static int getImageViewWidth(ImageView imageView) {
        if (imageView != null) {
            final ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int width = 0;
            if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = imageView.getWidth(); // Get actual image width
                Log.i("ImageViewHelper", width + " width");
            }
            if (width <= 0 && params != null) {
                width = params.width; // Get layout width parameter
            }
            if (width <= 0) {
                width = getImageViewFieldValue(imageView, "mMaxWidth");
            }
            return width;
        }
        return DEFAULT_WIDTH;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Height is defined by target {@link ImageView view} parameters,
     * configuration parameters or device display dimensions.<br />
     * Size computing algorithm:<br />
     * 1) Get the actual drawn <b>getHeight()</b> of the View. If view haven't
     * drawn yet then go to step #2.<br />
     * 2) Get <b>layout_height</b>. If it hasn't exact value then go to step #3.
     * <br />
     * 3) Get <b>maxHeight</b>.
     */
    public static int getImageViewHeight(ImageView imageView) {
        if (imageView != null) {
            final ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int height = 0;
            if (params != null
                        && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                height = imageView.getHeight(); // Get actual image height
            }
            if (height <= 0 && params != null) {
                // Get layout height parameter
                height = params.height;
            }
            if (height <= 0) {
                height = getImageViewFieldValue(imageView, "mMaxHeight");
            }
            return height;
        }
        return DEFAULT_HEIGHT;
    }

    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
        return value;
    }
}
