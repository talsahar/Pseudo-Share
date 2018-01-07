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

    FirebaseFileStorageDelegate delegate;
    public FirebaseStorageModel(FirebaseFileStorageDelegate delegate){
        this.delegate=delegate;
    }

    public void storeImage(Bitmap bitmap, String name) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                delegate.onUploadFailed(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                delegate.onUploadComplete(downloadUrl);
            }
        });
    }

    public void loadImage(String path){
    FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child(path);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                delegate.onDownloadComplete(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG",exception.getMessage());
                onFailure(exception);
            }
        });

    }

    public interface FirebaseFileStorageDelegate{
        void onUploadComplete(Uri result);
        void onUploadFailed(Exception exception);
        void onDownloadComplete(Bitmap result);
        void onDownloadFailed(Exception exception);


    }

}
