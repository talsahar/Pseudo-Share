package com.tal.pseudo_share.model;

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
import com.tal.pseudo_share.utilities.ExceptionHandler;

import java.util.logging.Logger;

/**
 * Created by talsahar73 on 10/01/2018.
 */

public class AuthenticationRepository {

    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private static AuthenticationRepository instance;

    public static AuthenticationRepository getInstance(){
        if(instance == null)
            instance=new AuthenticationRepository();
        return instance;
    }

    private AuthenticationRepository(){
         userMutableLiveData = new MutableLiveData<>();
         userMutableLiveData.setValue(FirebaseAuth.getInstance().getCurrentUser());
    }


    public void login(String email, String password) {
        if (email.isEmpty())
            ExceptionHandler.set(new Exception("email field is empty"));
        else if (password.isEmpty())
            ExceptionHandler.set(new Exception("password field is empty"));
        else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful())
                                userMutableLiveData.setValue(task.getResult().getUser());

                            else
                                ExceptionHandler.set(task.getException());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ExceptionHandler.set(e);

                }
            });
        }
    }

    public void signup(final String email, String password, final String nickname) {
        if (email.isEmpty())
            ExceptionHandler.set(new Exception("email field is empty"));
        else if (password.isEmpty())
            ExceptionHandler.set(new Exception("password field is empty"));
        else if (nickname.isEmpty())
            ExceptionHandler.set(new Exception("nickname field is empty"));
        else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> taskA) {
                    if (taskA.isSuccessful()) {
                        final FirebaseUser user = taskA.getResult().getUser();
                        updateNickname(user, nickname, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> taskB) {
                                userMutableLiveData.setValue(user);
                            }
                        });
                    } else
                        ExceptionHandler.set(taskA.getException());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ExceptionHandler.set(e);

                }
            });
        }
    }

    private void updateNickname(FirebaseUser user, String nickname, OnCompleteListener<Void> onCompleteListener) {
        UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(nickname).build();
        user.updateProfile(update).addOnCompleteListener(onCompleteListener);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        userMutableLiveData.setValue(null);
    }

    public LiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }


    public String getCurrUsername() {
        return userMutableLiveData.getValue()!=null?userMutableLiveData.getValue().getDisplayName():null;
    }

}
