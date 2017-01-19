package com.gemapps.remembrall.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.ByteArrayOutputStream;

/**
 * Created by edu on 1/18/17.
 */

public class ImageUtil {

    public static byte[] convertBitmapToByte(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap convertByteToBitmap(byte[] imageLikeBytes){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeByteArray(imageLikeBytes, 0, imageLikeBytes.length, options);
    }

    public static void changeBlackLinesToWhite(Bitmap bitmap){
        int[] pixels = new int[bitmap.getHeight() * bitmap.getWidth()];

        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < pixels.length; i++) {
            if(pixels[i] == Color.BLACK) pixels[i] = Color.WHITE;
        }

        bitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
    }
}
