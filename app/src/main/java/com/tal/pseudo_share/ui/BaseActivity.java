package com.tal.pseudo_share.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;

/**
 * Created by User on 20/01/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}