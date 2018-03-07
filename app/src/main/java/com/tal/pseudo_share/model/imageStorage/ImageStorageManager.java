package com.tal.pseudo_share.model.imageStorage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tal.pseudo_share.utilities.Callback;

/**
 * Created by User on 11/01/2018.
 */

public class ImageStorageManager {


    public static void storeImage(final Bitmap bitmap, final String name, final Callback<Uri> onComplete, final Callback<Exception> onFailed) {

                ImageFirebaseStorage.storeImage(bitmap, name, new ImageFirebaseStorage.OnUploadCompleteListener() {
                    @Override
                    public void onUploadComplete(Uri result) {
                        ImageLocalStorage.storeImage(bitmap, name);
                        onComplete.call(result);
                    }

                    @Override
                    public void onUploadFailed(Exception exception) {
                        onFailed.call(exception);
                    }
                });

    }

    public static void loadImage(final String path, final Callback<Bitmap> onComplete, final Callback<Exception> onFailed) {

            Bitmap bitmap=null;
            if((bitmap=ImageLocalStorage.loadImage(path))!=null)
                onComplete.call(ImageLocalStorage.loadImage(path));
            else
                ImageFirebaseStorage.loadImage(path, new ImageFirebaseStorage.OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(Bitmap result) {
                        ImageLocalStorage.storeImage(result, path);
                        onComplete.call(result);
                    }

                    @Override
                    public void onDownloadFailed(Exception exception) {
                      onFailed.call(exception);
                    }
                });

    }

    public static void deleteImage(final String imageUrl, boolean fromServer) {
            if (fromServer == true)
                ImageFirebaseStorage.deleteImage(imageUrl, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d("TAG","Image "+imageUrl+" have been deleted from firebase.");
                    }
                });
            ImageLocalStorage.deleteImage(imageUrl);
        }
    }
