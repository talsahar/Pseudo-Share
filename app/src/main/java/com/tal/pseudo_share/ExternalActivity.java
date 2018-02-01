package com.tal.pseudo_share;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.ui.creation.CreateFragmentOne;

import java.util.HashMap;

public class ExternalActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Toolbar getToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
            toolbar.setTitle("About");
        return toolbar;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_external);

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
        map.put(R.id.contentContainer, new AboutFragment());
        return map;
    }
}
