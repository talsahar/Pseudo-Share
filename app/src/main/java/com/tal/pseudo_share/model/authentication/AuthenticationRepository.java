package com.tal.pseudo_share.model.authentication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by talsahar73 on 10/01/2018.
 */

public class AuthenticationRepository {

    private static MutableLiveData<FirebaseUser> userMutableLiveData;
    private static MutableLiveData<Exception> exceptionMutableLiveData;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    private static OnFailureListener  onFail=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            exceptionMutableLiveData.setValue(e);
        }
    };


    public static void login(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userMutableLiveData.setValue(task.getResult().getUser());
                        }
                        else
                            exceptionMutableLiveData.setValue(task.getException());
                    }
                }).addOnFailureListener(onFail);
    }

    public static void signup(final String email, String password, final String nickname) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> taskA) {
                if (taskA.isSuccessful()) {
                    final FirebaseUser use=taskA.getResult().getUser();
                    updateNickname(use,nickname,new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> taskB) {
                          userMutableLiveData.setValue(use);
                        }
                    });
                }
                else
                    exceptionMutableLiveData.setValue(taskA.getException());
            }
        }).addOnFailureListener(onFail);
    }

    public static void updateNickname(FirebaseUser user, String nickname, OnCompleteListener<Void> onCompleteListener) {
        UserProfileChangeRequest update=new UserProfileChangeRequest.Builder().setDisplayName(nickname).build();
        user.updateProfile(update).addOnCompleteListener(onCompleteListener).addOnFailureListener(onFail);
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
        userMutableLiveData.setValue(null);
    }

    public static MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        if(userMutableLiveData==null)
        {
            userMutableLiveData=new MutableLiveData<>();
            userMutableLiveData.setValue(FirebaseAuth.getInstance().getCurrentUser());
        }
        return userMutableLiveData;
    }

    public static MutableLiveData<Exception> getExceptionMutableLiveData() {
   if(exceptionMutableLiveData==null)
       exceptionMutableLiveData=new MutableLiveData<>();
        return exceptionMutableLiveData;
    }



}
