package com.tal.pseudo_share.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.ui.main.MainActivity;
import com.tal.pseudo_share.utilities.StoragePermission;
import com.tal.pseudo_share.viewmodel.AuthenticationViewModel;
import com.tal.pseudo_share.viewmodel.StaticMutablesHolder;

import java.util.HashMap;
import java.util.concurrent.Future;


public class LoginActivity extends BaseActivity {
    AuthenticationViewModel viewModel;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StoragePermission.verifyStoragePermissions(this);

        final EditText userField = findViewById(R.id.userField);
        final EditText passField = findViewById(R.id.passField);
        final EditText nickField = findViewById(R.id.nickname);
        Button signupButton = findViewById(R.id.signupButton);
        Button loginButton = findViewById(R.id.loginButton);

        viewModel = ViewModelProviders.of(this).get(AuthenticationViewModel.class);

        final LiveData<FirebaseUser> userLiveData = viewModel.getUserLiveData();
        userLiveData.observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "Welcome to Pseudo-Share", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userField.getText().toString();
                String pass = passField.getText().toString();
                String nick = nickField.getText().toString();
                viewModel.signup(user, pass, nick);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userField.getText().toString();
                String pass = passField.getText().toString();
                viewModel.signin(user, pass);
            }
        });
    }


    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public ProgressBar loadProgressBar() {
        return  findViewById(R.id.progressBar);
    }

    @Override
    public int getOnBackFragmentId() {
        return 0;
    }

    @Override
    public HashMap<Integer, Fragment> getInitialFragments() {
        return null;
    }
}