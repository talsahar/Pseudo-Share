package com.tal.pseudo_share.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.model.StaticMutablesHolder;
import com.tal.pseudo_share.ui.main.KeyboardHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 20/01/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseActivityInterface {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // KeyboardHandler.setupUI(this,findViewById(getParentId()));




        StaticMutablesHolder.exceptionMutableLiveData.observe(this, new Observer<Exception>() {
            @Override
            public void onChanged(@Nullable Exception e) {
                if(e!=null)
                {
                    Toast.makeText(BaseActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    StaticMutablesHolder.exceptionMutableLiveData.setValue(null);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        HashMap<Integer, Fragment> fragmentMap = getInitialFragments();
        if (fragmentMap != null) {
            for (Map.Entry<Integer, Fragment> entry : fragmentMap.entrySet()) {
                loadFragment(entry.getKey(),entry.getValue());
            }
        }
        Toolbar toolbar = getToolbar();
        if (toolbar != null)
            setSupportActionBar(toolbar);

        final ProgressBar progressBar = loadProgressBar();
        if (progressBar != null)
            StaticMutablesHolder.progressStatus.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean)
                        progressBar.setVisibility(View.VISIBLE);
                    else
                        progressBar.setVisibility(View.INVISIBLE);
                }
            });

    }

    public void loadFragment(int containerId, Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    public void registerHideKeyboardOnClick(View view){
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        });
    }




}

interface BaseActivityInterface {
    Toolbar getToolbar();//null value wont display menu, (dont forget to set title toolbar.setTitle(""));
    int getParentId();//returns parent id for keyboard autoDismiss
    ProgressBar loadProgressBar();
    HashMap<Integer, Fragment> getInitialFragments();//return an hashmap of fragments and their container's id
}