package com.tal.pseudo_share.model.authentication;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tal.pseudo_share.controller.LoginActivity;

/**
 * Created by User on 15/12/2017.
 */

public class AuthenticationModel implements AuthenticationAPI{
   FirebaseAuth auth;

    public AuthenticationModel(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public void login(String email, String password, final LoginActivity.OnSuccess onLoginSuccess, final LoginActivity.OnFail onLoginFailed) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteCreate(onLoginSuccess,onLoginFailed));
    }

    @Override
    public void signup(final String email, String password, String nickname, final LoginActivity.OnSuccess onLoginSuccess, final LoginActivity.OnFail onLoginFailed) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(onCompleteCreate(onLoginSuccess,onLoginFailed));
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    @Override
    public void logout() {
        auth.signOut();
    }


    private OnCompleteListener<AuthResult> onCompleteCreate(final LoginActivity.OnSuccess onSuccess, final LoginActivity.OnFail onFail){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    onSuccess.accept(auth.getCurrentUser());

                 else
                    onFail.accept(task.getException());

            }
        };
    }
}
