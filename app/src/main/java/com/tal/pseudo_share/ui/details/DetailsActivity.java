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
import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.ui.creation.CreateFragmentOne;
import com.tal.pseudo_share.viewmodel.DetailsViewModel;
import com.tal.pseudo_share.viewmodel.StaticMutablesHolder;

import java.util.HashMap;

public class DetailsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_details);

    }

    @Override
    public Toolbar getToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Pseudo Details");
        return toolbar;
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
    public  HashMap<Integer, Fragment> getInitialFragments() {
        HashMap<Integer,Fragment> map=new HashMap<>();
        map.put(R.id.contentContainer, new DetailsFragmentOne());
        return map;
    }
}
