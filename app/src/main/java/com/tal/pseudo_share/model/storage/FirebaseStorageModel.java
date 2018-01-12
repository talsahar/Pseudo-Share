package com.tal.pseudo_share.model.storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by User on 21/12/2017.
 */

public class FirebaseStorageModel {


    public static void storeImage(Bitmap bitmap, String name, final OnUploadCompleteListener listener) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
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
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.onUploadComplete(downloadUrl);
            }
        });
    }

    public static void loadImage(String path,final OnDownloadCompleteListener listener){
    FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child(path);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                listener.onDownloadComplete(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG",exception.getMessage());
                listener.onDownloadFailed(exception);
            }
        });

    }
    public interface OnUploadCompleteListener{
        void onUploadComplete(Uri result);
        void onUploadFailed(Exception exception);

    }
    public interface OnDownloadCompleteListener{
        void onDownloadComplete(Bitmap result);
        void onDownloadFailed(Exception exception);
    }

}
