package com.tal.pseudo_share.model.storage;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by User on 11/01/2018.
 */

public class ImageStorageModel {

    public static void storeImage(final Bitmap bitmap, final String name, final FirebaseStorageModel.OnUploadCompleteListener listener) {
        FirebaseStorageModel.storeImage(bitmap, name, new FirebaseStorageModel.OnUploadCompleteListener() {
            @Override
            public void onUploadComplete(Uri result) {
                LocalStorage.saveImageToFile(bitmap, result.toString().split("token=")[1]);
                listener.onUploadComplete(result);
            }

            @Override
            public void onUploadFailed(Exception exception) {exception.printStackTrace();
            listener.onUploadFailed(exception);
            }
        });
    }

    public static void loadImage(final String path, final FirebaseStorageModel.OnDownloadCompleteListener listener) {
        Bitmap imageBitmap;
//        if ((imageBitmap = LocalStorage.loadImageFromFile(path.split("token=")[1])) != null)
  //          listener.onDownloadComplete(imageBitmap);
    //    else {
            FirebaseStorageModel.loadImage(path, new FirebaseStorageModel.OnDownloadCompleteListener() {
                @Override
                public void onDownloadComplete(Bitmap result) {
                    LocalStorage.saveImageToFile(result, path.split("token=")[1]);
                    listener.onDownloadComplete(result);
                }

                @Override
                public void onDownloadFailed(Exception exception) {
                    listener.onDownloadFailed(exception);
                }
            });
      //  }
    }


}


