package com.tal.pseudo_share.model.authentication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by talsahar73 on 10/01/2018.
 */

public class AuthenticationRepository {

    private static MutableLiveData<FirebaseUser> userMutableLiveData=new MutableLiveData<>();
    private static MutableLiveData<Exception> exceptionMutableLiveData=new MutableLiveData<>();

    static{
        userMutableLiveData.setValue(FirebaseAuth.getInstance().getCurrentUser());
    }

    public static void login(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            userMutableLiveData.setValue(task.getResult().getUser());

                        else
                            exceptionMutableLiveData.setValue(task.getException());
                    }
                });
    }

    public static void signup(final String email, String password, final String nickname) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> taskA) {
                if (taskA.isSuccessful()) {
                    updateNickname(nickname,new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> taskB) {
                            userMutableLiveData.setValue(taskA.getResult().getUser());
                        }
                    });
                }
                else
                    exceptionMutableLiveData.setValue(taskA.getException());
            }
        });
    }

    public static void updateNickname(String nickname,OnCompleteListener<Void> onCompleteListener){
        UserProfileChangeRequest update=new UserProfileChangeRequest.Builder().setDisplayName(nickname).build();
        final FirebaseUser user=userMutableLiveData.getValue();
        user.updateProfile(update).addOnCompleteListener(onCompleteListener);
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public static MutableLiveData<Exception> getExceptionMutableLiveData() {
        return exceptionMutableLiveData;
    }



}
