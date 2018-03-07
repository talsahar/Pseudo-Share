package com.tal.pseudo_share.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.ui.main.BaseActivity;
import com.tal.pseudo_share.ui.main.MainActivity;
import com.tal.pseudo_share.viewmodel.AuthenticationVM;


public class LoginActivity extends BaseActivity {

    private void loginIfAuthorized(LiveData<FirebaseUser> liveData){
        if (liveData.getValue() != null) {
            Toast.makeText(LoginActivity.this, "Welcome to Pseudo-Share", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText userField = findViewById(R.id.userField);
        final EditText passField = findViewById(R.id.passField);
        final EditText nickField = findViewById(R.id.nickname);
        Button signupButton = findViewById(R.id.signupButton);
        Button loginButton = findViewById(R.id.loginButton);

        final AuthenticationVM viewModel = ViewModelProviders.of(this).get(AuthenticationVM.class);
        final ProgressBar pBar = findViewById(R.id.progressBar);

        final LiveData<FirebaseUser> userLiveData = viewModel.getUserLiveData();

        loginIfAuthorized(userLiveData);
        userLiveData.observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                pBar.setVisibility(View.INVISIBLE);
                loginIfAuthorized(userLiveData);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBar.setVisibility(View.VISIBLE);
                String user = userField.getText().toString();
                String pass = passField.getText().toString();
                String nick = nickField.getText().toString();

                viewModel.signup(user, pass, nick);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBar.setVisibility(View.VISIBLE);
                String user = userField.getText().toString();
                String pass = passField.getText().toString();
                viewModel.signin(user, pass);
            }
        });
    }
    @Override
    public void onModelException(Exception exception){

        ProgressBar pBar = findViewById(R.id.progressBar);
        pBar.setVisibility(View.INVISIBLE);
    }

}