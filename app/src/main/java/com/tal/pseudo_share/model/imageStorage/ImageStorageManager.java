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
        if (bitmap == null)
            onComplete.call(null);
        else{
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
    }

    public static void loadImage(final String path, final Callback<Bitmap> onComplete, final Callback<Exception> onFailed) {
        if (path == null || path.isEmpty())
            onComplete.call(null);
        else {
            final String fname = uriToFileName(path);
            Bitmap bitmap=null;
            if((bitmap=ImageLocalStorage.loadImage(fname))!=null)
                onComplete.call(ImageLocalStorage.loadImage(fname));
            else
                ImageFirebaseStorage.loadImage(path, new ImageFirebaseStorage.OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(Bitmap result) {
                        ImageLocalStorage.storeImage(result, fname);
                        onComplete.call(result);
                    }

                    @Override
                    public void onDownloadFailed(Exception exception) {
                      onFailed.call(exception);
                    }
                });
        }
    }

    public static void deleteImage(String imageUrl, boolean fromServer) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            final String fname = uriToFileName(imageUrl);
            if (fromServer == true)
                ImageFirebaseStorage.deleteImage(fname, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d("TAG","Image "+fname+" have been deleted from firebase.");
                    }
                });
            ImageLocalStorage.deleteImage(fname);
        }
    }

    private static String uriToFileName(String uri) {
        String s = uri.toString().split("images%2F")[1].split("alt=")[0];
        return s.substring(0, s.length() - 1);
    }
}


