package com.tal.pseudo_share.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.tal.pseudo_share.view.ProgressBarHandler;
import com.tal.pseudo_share.R;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;


public class LoginActivity extends AbstractActivity {
    EditText userField;
    EditText passField;
    EditText nickField;
    ProgressBarHandler progressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        autoLogin();
        userField = findViewById(R.id.userField);
        passField = findViewById(R.id.passField);
        nickField = findViewById(R.id.nickname);
        RingProgressBar ringProgressBar = findViewById(R.id.progress_bar);
        progressHandler = new ProgressBarHandler(this, ringProgressBar);


        Button signUp = findViewById(R.id.signupButton);
        Button loginButton = findViewById(R.id.loginButton);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressHandler.show();
                String user, pass, nick;
                if ((user = fieldAsString(userField)).isEmpty())
                    Toast.makeText(LoginActivity.this, "Error: email field is empty.", Toast.LENGTH_SHORT).show();
                else if ((pass = fieldAsString(passField)).isEmpty())
                    Toast.makeText(LoginActivity.this, "Error: password field is empty.", Toast.LENGTH_SHORT).show();
                else if ((nick = fieldAsString(nickField)).isEmpty())
                    Toast.makeText(LoginActivity.this, "Error: nickname field is empty.", Toast.LENGTH_SHORT).show();
                else
                    model.getAuthenticationModel().signup(fieldAsString(userField), fieldAsString(passField), fieldAsString(nickField)
                            , new OnSuccess() {
                                @Override
                                public void accept(FirebaseUser user) {
                                    signupSuccess(user, user.getEmail() + " you have successfully signed up.");
                                }
                            }, new OnFail() {
                                @Override
                                public void accept(Exception exception) {
                                    signupFailed(exception);
                                }
                            });
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressHandler.show();
                String user, pass, nick;
                if ((user = fieldAsString(userField)).isEmpty())
                    Toast.makeText(LoginActivity.this, "Error: email field is empty.", Toast.LENGTH_SHORT).show();
                else if ((pass = fieldAsString(passField)).isEmpty())
                    Toast.makeText(LoginActivity.this, "Error: password field is empty.", Toast.LENGTH_SHORT).show();
                else
                    model.getAuthenticationModel().login(fieldAsString(userField), fieldAsString(passField),
                            new OnSuccess() {
                                @Override
                                public void accept(FirebaseUser user) {
                                    loginSuccess(user, user.getDisplayName() + " you have successfully signed in.");
                                }
                            }, new OnFail() {
                                @Override
                                public void accept(Exception exception) {
                                    loginFailed(exception);
                                }
                            });
            }
        });
    }

    private void autoLogin() {
        FirebaseUser user = model.getAuthenticationModel().getCurrentUser();
        if (user != null)
            loginSuccess(user, "Welcome back " + user.getDisplayName());
    }

    public void loginSuccess(FirebaseUser user, String toastMessage) {
        Toast.makeText(LoginActivity.this, toastMessage,
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void loginFailed(Exception exception) {
        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void signupSuccess(FirebaseUser user, String toastMessage) {
        UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(fieldAsString(nickField)).build();
        user.updateProfile(update);

        Toast.makeText(LoginActivity.this, toastMessage,
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void signupFailed(Exception exception) {
        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }


    private String fieldAsString(EditText field) {
        return field.getText().toString();
    }


    public interface OnSuccess {
        void accept(FirebaseUser user);
    }

    public interface OnFail {
        void accept(Exception exception);
    }


}