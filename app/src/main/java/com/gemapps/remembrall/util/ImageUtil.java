package com.gemapps.remembrall.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.gemapps.remembrall.ui.model.Client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by edu on 1/18/17.
 */

public class ImageUtil {
    private static final String TAG = "ImageUtil";
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

    public static Uri saveImage(Client client, Bitmap bitmap){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "remembrall");

        if(!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdir()){
                Log.w(TAG, "Failed to create directory");
                return null;
            }
        }
        String imageName = client.getFirstName() + client.getIdCard() + ".jpg";
        File file = new File(mediaStorageDir.getPath() + File.separator + imageName);

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }
}
