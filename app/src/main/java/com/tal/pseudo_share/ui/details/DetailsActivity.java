package com.tal.pseudo_share.ui.details;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.ui.main.BaseActivity;

public class DetailsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Pseudo Details");
        setSupportActionBar(toolbar);

        //initial fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, new DetailsFragmentOne());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onModelException(Exception exception) {
            ProgressBar pBar = findViewById(R.id.progressBar);
            pBar.setVisibility(View.INVISIBLE);
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
        if(getSupportFragmentManager().findFragmentById(R.id.contentContainer)==null)
            finish();
    }

}
