package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.utilities.ExceptionHandler;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExceptionHandler.get().observe(this, new Observer<Exception>() {
            @Override
            public void onChanged(@Nullable Exception e) {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                onModelException(e);
            }
        });

    }
    public abstract void onModelException(Exception exception);

}
