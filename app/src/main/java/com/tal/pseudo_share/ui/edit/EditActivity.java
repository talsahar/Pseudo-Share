package com.tal.pseudo_share.ui.edit;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.ui.creation.CreateFragmentOne;
import com.tal.pseudo_share.viewmodel.CreatePseudoViewModel;
import com.tal.pseudo_share.viewmodel.EditPseudoViewModel;
import com.tal.pseudo_share.viewmodel.StaticMutablesHolder;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pseudo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Pseudo");
        setSupportActionBar(toolbar);
        String id=getIntent().getStringExtra("id");
        EditPseudoViewModel viewModel= ViewModelProviders.of(this).get(EditPseudoViewModel.class);

        StaticMutablesHolder.bindProgressBar(this, (ProgressBar) findViewById(R.id.progressBar));


        viewModel.getPseudoWhenReady().observe(this, new Observer<Pseudo>() {
            @Override
            public void onChanged(@Nullable Pseudo pseudo) {
                finish();
            }
        });

    /*    Fragment newFragment = new EditFragmentOne();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

*/


    }
}
