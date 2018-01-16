package com.tal.pseudo_share.ui.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.ui.creation.CreateFragmentOne;
import com.tal.pseudo_share.viewmodel.DetailsViewModel;

public class DetailsActivity extends AppCompatActivity {

    DetailsViewModel detailsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pseudo Details");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("id");
        detailsViewModel= ViewModelProviders.of(this).get(DetailsViewModel.class);
        detailsViewModel.getProgressBarStatusMutableData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                ProgressBar progressBar=findViewById(R.id.progressBar);
                if(aBoolean)
                progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.INVISIBLE);
            }
        });
        Fragment newFragment = new DetailsFragmentOne();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        if (fragment.getClass() == DetailsFragmentOne.class)
            finish();
        else super.onBackPressed();
    }

}
