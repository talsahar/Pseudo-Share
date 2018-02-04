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

/**
 * Created by talsahar73 on 10/01/2018.
 */

public class AuthenticationRepository {

    private static MutableLiveData<FirebaseUser> userMutableLiveData = new MutableLiveData<>();


    static {
        userMutableLiveData.setValue(FirebaseAuth.getInstance().getCurrentUser());
    }

    private static OnFailureListener onFail = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            StaticMutablesHolder.exceptionMutableLiveData.setValue(e);
            StaticMutablesHolder.progressStatus.setValue(false);
        }
    };


    public static void login(String email, String password) {
        if (email.isEmpty())
            StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("email field is empty"));
        else if (password.isEmpty())
            StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("password field is empty"));
        else {
            StaticMutablesHolder.progressStatus.setValue(true);
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful())
                                userMutableLiveData.setValue(task.getResult().getUser());

                            else
                                onFail.onFailure(task.getException());
                            StaticMutablesHolder.progressStatus.setValue(false);
                        }
                    }).addOnFailureListener(onFail);
        }
    }

    public static void signup(final String email, String password, final String nickname) {
        if (email.isEmpty())
            StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("email field is empty"));
        else if (password.isEmpty())
            StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("password field is empty"));
        else if (nickname.isEmpty())
            StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("nickname field is empty"));
        else {
            StaticMutablesHolder.progressStatus.setValue(true);
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
                        onFail.onFailure(taskA.getException());
                    StaticMutablesHolder.progressStatus.setValue(false);
                }
            }).addOnFailureListener(onFail);
        }
    }

    private static void updateNickname(FirebaseUser user, String nickname, OnCompleteListener<Void> onCompleteListener) {
        UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(nickname).build();
        user.updateProfile(update).addOnCompleteListener(onCompleteListener).addOnFailureListener(onFail);
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
        userMutableLiveData.setValue(null);
    }

    public static LiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }


    public static String getCurrUsername() {
        return userMutableLiveData.getValue()!=null?userMutableLiveData.getValue().getDisplayName():null;
    }

}
