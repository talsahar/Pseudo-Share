package com.tal.pseudo_share.model.imageStorage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.tal.pseudo_share.utilities.StoragePermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by User on 07/01/2018.
 */

public class ImageLocalStorage {

    static File dir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES);

    public static Bitmap loadImage(String imageFileName){

        Bitmap bitmap = null;
        try {
            File imageFile = new File(dir,imageFileName);
            if(!imageFile.exists() || imageFile.isDirectory())
                return null;

            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Logger.d("tag","got image from cache: " + imageFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void storeImage(Bitmap imageBitmap, String imageFileName){
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();
            OutputStream out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteImage(String fileName) {
            File imageFile = new File(dir,fileName);
            imageFile.delete();
    }




}
