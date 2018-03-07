package com.tal.pseudo_share.ui.creation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.ui.main.BaseActivity;
import com.tal.pseudo_share.utilities.StoragePermission;
import com.tal.pseudo_share.viewmodel.CreatePseudoVM;


public class CreatePseudoActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pseudo);

        StoragePermission.verifyStoragePermissions(this);

        //toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        if(getIntent().getStringExtra("id")!=null)
            toolbar.setTitle("Edit Pseudo");
        else toolbar.setTitle("Create Pseudo");
        setSupportActionBar(toolbar);

        //initial fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, new CreateFragmentOne());
        transaction.addToBackStack(null);
        transaction.commit();


        //bind on create
        CreatePseudoVM createPseudoViewModel = ViewModelProviders.of(this).get(CreatePseudoVM.class);
        //notified when the new pseudo has been created and stored on storage.
        createPseudoViewModel.getCreatedLiveData().observe(this, new Observer<Pseudo>() {
            @Override
            public void onChanged(@Nullable Pseudo pseudo) {
                ProgressBar pbar=findViewById(R.id.progressBar);
                pbar.setVisibility(View.INVISIBLE);
            }
        });
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
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //it will finish activity if there is no fragment on the stack.
        if(getSupportFragmentManager().findFragmentById(R.id.contentContainer)==null)
            finish();
    }
@Override
    public void onModelException(Exception exception){

        ProgressBar pBar = findViewById(R.id.progressBar);
    pBar.setVisibility(View.INVISIBLE);
    }
}
