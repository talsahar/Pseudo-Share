package com.tal.pseudo_share.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.tal.pseudo_share.model.authentication.AuthenticationModel;
import com.tal.pseudo_share.R;



public class LoginActivity extends AppCompatActivity implements AuthenticationModel.AuthenticationDelegate {
    EditText userField;
    EditText passField;
    EditText nickField;
    ProgressBar progressBar;
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
        progressBar = findViewById(R.id.progressBar);
        Button signupButton = findViewById(R.id.signupButton);
        Button loginButton = findViewById(R.id.loginButton);

        auth=new AuthenticationModel(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String user, pass, nick;
                if ((user = fieldAsString(userField)).isEmpty())
                    toast("Error: email field is empty.");
                else if ((pass = fieldAsString(passField)).isEmpty())
                    toast("Error: password field is empty.");
                else if ((nick = fieldAsString(nickField)).isEmpty())
                    toast("Error: nickname field is empty.");
                else
                {
                    auth.signup(fieldAsString(userField), fieldAsString(passField), fieldAsString(nickField));
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String user, pass, nick;
                if ((user = fieldAsString(userField)).isEmpty())
                    toast("Error: email field is empty.");
                else if ((pass = fieldAsString(passField)).isEmpty())
                    toast("Error: password field is empty.");
                else
                {
                    auth.login(fieldAsString(userField),fieldAsString(passField));
                return;
                }
                progressBar.setVisibility(View.INVISIBLE);
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
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoginFailure(Exception exception) {
        toast(exception.getMessage());
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSignupSuccess(FirebaseUser user) {
        toast("you have been successfully signed up.");
        loadMainActivity();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSignupFailure(Exception exception) {
        toast(exception.getMessage());progressBar.setVisibility(View.INVISIBLE);
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