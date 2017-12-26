package com.tal.pseudo_share.model.storage;

import android.widget.ImageView;

/**
 * Created by User on 21/12/2017.
 */

public interface FirebaseFilesAPI {

    //UPLOAD IMAGE
    void uploadFromMemory(ImageView imageView);
    void uploadViaStream(ImageView imageView);
    void downloadFile(final ImageView imageView);

}

