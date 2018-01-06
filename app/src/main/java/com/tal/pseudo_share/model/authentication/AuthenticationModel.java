package com.tal.pseudo_share.model.authentication;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.tal.pseudo_share.ui.LoginActivity;

/**
 * Created by User on 05/01/2018.
 */

public class AuthenticationModel {

AuthenticationDelegate delegate;

    public AuthenticationModel(AuthenticationDelegate delegate) {
        this.delegate=delegate;
    }

    public void login(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            delegate.onLoginSuccess(task.getResult().getUser());

                        else
                            delegate.onLoginFailure(task.getException());
                    }
                });
    }

    public void signup(final String email, String password, final String nickname) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    updateNickname(nickname);
                    delegate.onSignupSuccess(FirebaseAuth.getInstance().getCurrentUser());
                }
                else
                    delegate.onSignupFailure(task.getException());
            }
        });
    }

    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public void updateNickname(String nickname){
        UserProfileChangeRequest update=new UserProfileChangeRequest.Builder().setDisplayName(nickname).build();
        getCurrentUser().updateProfile(update);
    }

    public interface AuthenticationDelegate{
        void onLoginSuccess(FirebaseUser user);
        void onLoginFailure(Exception exception);
        void onSignupSuccess(FirebaseUser user);
        void onSignupFailure(Exception exception);

    }

}
