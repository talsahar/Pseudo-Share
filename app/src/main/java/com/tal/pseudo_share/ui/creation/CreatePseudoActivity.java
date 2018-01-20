package com.tal.pseudo_share.ui.creation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.ui.creation.CreateFragmentOne;
import com.tal.pseudo_share.viewmodel.CreatePseudoViewModel;
import com.tal.pseudo_share.viewmodel.StaticMutablesHolder;


public class CreatePseudoActivity extends AppCompatActivity {
    CreatePseudoViewModel createPseudoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pseudo);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Pseudo");
        setSupportActionBar(toolbar);
        createPseudoViewModel = ViewModelProviders.of(this).get(CreatePseudoViewModel.class);
        //called when the new pseudo has been created and stored on storage.
        createPseudoViewModel.getPseudoLiveData().observe(this, new Observer<Pseudo>() {
            @Override
            public void onChanged(@Nullable Pseudo pseudo) {
                finish();
            }
        });

        StaticMutablesHolder.bindProgressBar(this, (ProgressBar) findViewById(R.id.progressBar));

        Fragment newFragment = new CreateFragmentOne();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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


}
