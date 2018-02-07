package com.tal.pseudo_share.ui.creation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
    public void setContentView() {
        setContentView(R.layout.activity_create_pseudo);
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
    public int getOnBackFragmentId() {
return R.id.contentContainer;
    }

    @Override
    public HashMap<Integer, Fragment> getInitialFragments() {
        HashMap<Integer, Fragment> map=new HashMap<>();
        map.put(R.id.contentContainer, new CreateFragmentOne());
        return map;
    }

}
