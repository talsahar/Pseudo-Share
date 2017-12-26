package com.tal.pseudo_share.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.tal.pseudo_share.model.authentication.AuthenticationAPI;
import com.tal.pseudo_share.model.authentication.AuthenticationModel;
import com.tal.pseudo_share.model.storage.FirebaseFilesAPI;
import com.tal.pseudo_share.model.storage.FirebaseStorageModel;


/**
 * Created by User on 15/12/2017.
 */

public class MyModel {
    private static final MyModel ourInstance = new MyModel();
    private AuthenticationAPI authenticationModel;
    private FirebaseFilesAPI storageModel;

    public static MyModel getInstance() {
        return ourInstance;
    }

    private MyModel() {
        authenticationModel=new AuthenticationModel(FirebaseAuth.getInstance());
        storageModel=new FirebaseStorageModel(FirebaseStorage.getInstance());
    }

    public FirebaseFilesAPI getStorageModel() {return storageModel;}
    public AuthenticationAPI getAuthenticationModel(){
        return authenticationModel;
    }

}
