package com.tal.pseudo_share.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.controller.LoginActivity;
import com.tal.pseudo_share.model.MyModel;
import com.tal.pseudo_share.view.ProgressBarHandler;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

/**
 * Created by User on 23/12/2017.
 */

public abstract class AbstractActivity extends AppCompatActivity {

    protected MyModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = MyModel.getInstance();
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
      //  getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                model.getAuthenticationModel().logout();
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
