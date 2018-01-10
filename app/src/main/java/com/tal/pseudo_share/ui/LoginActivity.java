package com.tal.pseudo_share.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.viewmodel.AuthenticationViewModel;


public class LoginActivity extends AppCompatActivity {
    EditText userField;
    EditText passField;
    EditText nickField;
    ProgressBar progressBar;

    AuthenticationViewModel viewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(this).get(AuthenticationViewModel.class);

        viewModel.getExceptionLiveData().observe(this, new Observer<Exception>() {
            @Override
            public void onChanged(@Nullable Exception e) {
                toast(e.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                toast("Hello "+firebaseUser.getDisplayName()+".");
                progressBar.setVisibility(View.INVISIBLE);
                if(firebaseUser!=null)
                loadMainActivity();

            }
        });

        setContentView(R.layout.activity_login);
        userField = findViewById(R.id.userField);
        passField = findViewById(R.id.passField);
        nickField = findViewById(R.id.nickname);
        progressBar = findViewById(R.id.progressBar);
        Button signupButton = findViewById(R.id.signupButton);
        Button loginButton = findViewById(R.id.loginButton);

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
                    viewModel.signup(fieldAsString(userField), fieldAsString(passField), fieldAsString(nickField));
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
                    viewModel.signin(fieldAsString(userField),fieldAsString(passField));
                return;
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private String fieldAsString(EditText field) {
        return field.getText().toString();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();

    }

}