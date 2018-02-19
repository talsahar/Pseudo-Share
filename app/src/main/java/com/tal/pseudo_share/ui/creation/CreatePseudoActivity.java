package com.tal.pseudo_share.ui.creation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.StaticMutablesHolder;
import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.viewmodel.CreatePseudoViewModel;

import java.util.HashMap;


public class CreatePseudoActivity extends BaseActivity {
    CreatePseudoViewModel createPseudoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pseudo);
        createPseudoViewModel = ViewModelProviders.of(this).get(CreatePseudoViewModel.class);
        //notified when the new pseudo has been created and stored on storage.
        createPseudoViewModel.getPseudoLiveData().observe(this, new Observer<Pseudo>() {
            @Override
            public void onChanged(@Nullable Pseudo pseudo) {
            }
        });
    }

    @Override
    public Toolbar getToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        if(getIntent().getStringExtra("id")!=null)
            toolbar.setTitle("Edit Pseudo");
        toolbar.setTitle("Create Pseudo");
        return toolbar;
    }



    @Override
    public int getParentId() {
        return R.id.parent;
    }

    @Override
    public ProgressBar loadProgressBar() {
        return findViewById(R.id.progressBar);
    }



    @Override
    public HashMap<Integer, Fragment> getInitialFragments() {
        HashMap<Integer, Fragment> map=new HashMap<>();
        map.put(R.id.contentContainer, new CreateFragmentOne());
        return map;
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
        int containerId=R.id.contentContainer;
        if(getSupportFragmentManager().findFragmentById(containerId)==null)
            finish();
    }

}
