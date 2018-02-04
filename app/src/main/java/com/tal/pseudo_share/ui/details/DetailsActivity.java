package com.tal.pseudo_share.ui.details;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.ui.BaseActivity;

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
