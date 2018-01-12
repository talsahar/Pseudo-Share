package com.tal.pseudo_share.model.storage;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by User on 11/01/2018.
 */

public class ImageStorageModel {

    public static void storeImage(Bitmap bitmap, String name, FirebaseStorageModel.OnUploadCompleteListener listener){
            LocalStorage.saveImageToFile(bitmap,name);
            FirebaseStorageModel.storeImage(bitmap, name, listener);
        }

        public static void loadImage(final String path, final FirebaseStorageModel.OnDownloadCompleteListener listener){
        Bitmap imageBitmap;
        if(LocalStorage.isExists(getFileNameFromPath(path)))
        if ((imageBitmap=LocalStorage.loadImageFromFile(path))==null){
            FirebaseStorageModel.loadImage(path, new FirebaseStorageModel.OnDownloadCompleteListener() {
                @Override
                public void onDownloadComplete(Bitmap result) {
                    LocalStorage.saveImageToFile(result,getFileNameFromPath(path));
                    listener.onDownloadComplete(result);
                }

                @Override
                public void onDownloadFailed(Exception exception) {
                    listener.onDownloadFailed(exception);
                }
            });
        }
        }
        public static String getFileNameFromPath(String path){
            String[] s=path.split("token=");
            return s[1]+".jpg";
        }

    }


