package com.tal.pseudo_share.model.imageStorage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tal.pseudo_share.utilities.Callback;

/**
 * Created by User on 11/01/2018.
 */

public class ImageStorageManager {


    public void storeImage(final Bitmap bitmap, final String name, final Callback<Uri> onComplete, final Callback<Exception> onFailed) {
        if(bitmap==null)
            onComplete.call(null);
        else ImageFirebaseStorage.storeImage(bitmap, name, new ImageFirebaseStorage.OnUploadCompleteListener() {
            @Override
            public void onUploadComplete(Uri result) {
                ImageLocalStorage.storeImage(bitmap,name);
                onComplete.call(result);
            }

            @Override
            public void onUploadFailed(Exception exception) {
                exception.printStackTrace();onFailed.call(exception);
            }
        });
    }

    public void loadImage(final String path, final Callback<Bitmap> onComplete,final Callback<Exception> onFailed) {
       if(path==null||path.isEmpty()){
           onComplete.call(null);
           return;
       }

        final String fname=uriToFileName(path);
        if(ImageLocalStorage.isExists(fname))
            onComplete.call(ImageLocalStorage.loadImage(fname));
        else ImageFirebaseStorage.loadImage(path, new ImageFirebaseStorage.OnDownloadCompleteListener() {
                @Override
                public void onDownloadComplete(Bitmap result) {
                    ImageLocalStorage.storeImage(result,fname);
                    onComplete.call(result);
                }

                @Override
                public void onDownloadFailed(Exception exception) {
                    exception.printStackTrace();final Callback<Exception> onFailed;
                }
            });
    }

    public void deleteImage(String path,boolean fromServer) {
        if(path==null||path.isEmpty())
            return;
        final String fname=uriToFileName(path);
        if(fromServer==true)
        ImageFirebaseStorage.deleteImage(fname, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                ImageLocalStorage.deleteImage(fname);
            }
        });
        else ImageLocalStorage.deleteImage(fname);
    }
    public String uriToFileName(String uri){
        String s=uri.toString().split("images%2F")[1].split("alt=")[0];
        return s.substring(0, s.length() - 1);
    }
}


