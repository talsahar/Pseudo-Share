package com.tal.pseudo_share.model.authentication;

import com.google.firebase.auth.FirebaseUser;
import com.tal.pseudo_share.controller.LoginActivity;

/**
 * Created by User on 15/12/2017.
 */

public interface AuthenticationAPI {
    void login(String email, String password, LoginActivity.OnSuccess onLoginSuccess, LoginActivity.OnFail onLoginFailed);
    void signup(final String email, String password, String nickname, LoginActivity.OnSuccess onLoginSuccess, LoginActivity.OnFail onLoginFailed);
    FirebaseUser getCurrentUser();
    void logout();

}
