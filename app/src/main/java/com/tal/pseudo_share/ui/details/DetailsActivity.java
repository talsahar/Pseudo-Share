package com.tal.pseudo_share.ui.details;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.ui.creation.CreatePseudoActivity;

import java.util.HashMap;

public class DetailsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }


    @Override
    public int getParentId() {
        return R.id.parent;
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
    public  HashMap<Integer, Fragment> getInitialFragments() {
        HashMap<Integer,Fragment> map=new HashMap<>();
        map.put(R.id.contentContainer, new DetailsFragmentOne());
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
