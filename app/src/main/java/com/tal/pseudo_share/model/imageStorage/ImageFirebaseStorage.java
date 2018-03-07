package com.tal.pseudo_share.model.imageStorage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by User on 21/12/2017.
 */

public class ImageFirebaseStorage {

    public static void storeImage(Bitmap bitmap, String name, final OnUploadCompleteListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onUploadFailed(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.onUploadComplete(downloadUrl);
            }
        });
    }

    public static void loadImage(final String path, final OnDownloadCompleteListener listener) {

        new AsyncTask<String, Integer, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... path0) {
                try {
                    URL url = new URL(path0[0]);
                    return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null)
                    listener.onDownloadComplete(bitmap);
                else
                    listener.onDownloadFailed(null);

            }
        }.execute(path);
    }

    public static void deleteImage(String path, final OnCompleteListener onCompleteListener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference().child("images").child(path).delete().addOnCompleteListener(onCompleteListener).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Image file already deleted from storage.");
            }
        });
    }


    public interface OnUploadCompleteListener {
        void onUploadComplete(Uri result);

        void onUploadFailed(Exception exception);

    }

    public interface OnDownloadCompleteListener {
        void onDownloadComplete(Bitmap result);

        void onDownloadFailed(Exception exception);
    }



}
