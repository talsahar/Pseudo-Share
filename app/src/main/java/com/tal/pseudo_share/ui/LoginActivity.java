package com.tal.pseudo_share.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.tal.pseudo_share.model.authentication.AuthenticationModel;
import com.tal.pseudo_share.view.ProgressBarHandler;
import com.tal.pseudo_share.R;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;


public class LoginActivity extends AppCompatActivity implements AuthenticationModel.AuthenticationDelegate {
    EditText userField;
    EditText passField;
    EditText nickField;
    ProgressBarHandler progressHandler;
AuthenticationModel auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(AuthenticationModel.getCurrentUser() != null)
            loadMainActivity();

        setContentView(R.layout.activity_login);
        userField = findViewById(R.id.userField);
        passField = findViewById(R.id.passField);
        nickField = findViewById(R.id.nickname);
        RingProgressBar ringProgressBar = findViewById(R.id.progress_bar);
        progressHandler = new ProgressBarHandler(this, ringProgressBar);
        Button signupButton = findViewById(R.id.signupButton);
        Button loginButton = findViewById(R.id.loginButton);

        auth=new AuthenticationModel(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressHandler.show();
                String user, pass, nick;
                if ((user = fieldAsString(userField)).isEmpty())
                    toast("Error: email field is empty.");
                else if ((pass = fieldAsString(passField)).isEmpty())
                    toast("Error: password field is empty.");
                else if ((nick = fieldAsString(nickField)).isEmpty())
                    toast("Error: nickname field is empty.");
                else
                   auth.signup(fieldAsString(userField), fieldAsString(passField), fieldAsString(nickField));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressHandler.show();
                String user, pass, nick;
                if ((user = fieldAsString(userField)).isEmpty())
                    toast("Error: email field is empty.");
                else if ((pass = fieldAsString(passField)).isEmpty())
                    toast("Error: password field is empty.");
                else
                    auth.login(fieldAsString(userField),fieldAsString(passField));
            }
        });
    }

    private String fieldAsString(EditText field) {
        return field.getText().toString();
    }

    @Override
    public void onLoginSuccess(FirebaseUser user) {
        toast("you have been successfully signed in.");
        loadMainActivity();
    }

    @Override
    public void onLoginFailure(Exception exception) {
        toast(exception.getMessage());
    }

    @Override
    public void onSignupSuccess(FirebaseUser user) {
        toast("you have been successfully signed up.");
        loadMainActivity();
    }

    @Override
    public void onSignupFailure(Exception exception) {
        toast(exception.getMessage());
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

}