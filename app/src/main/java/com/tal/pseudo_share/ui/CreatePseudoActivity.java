package com.tal.pseudo_share.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.view.ProgressBarHandler;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class CreatePseudoActivity extends AppCompatActivity {
    ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pseudo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Pseudo");
        setSupportActionBar(toolbar);

        Fragment newFragment = new CreateFragmentOne();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        RingProgressBar ringProgressBar = findViewById(R.id.progress_bar);
        progressBarHandler = new ProgressBarHandler(this, ringProgressBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
onBackPressed();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        final Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.contentContainer);
        if (fragment.getClass() == CreateFragmentOne.class)
            finish();
        else super.onBackPressed();
    }

    public void showProgressBar() {
        progressBarHandler.show();
    }

}
