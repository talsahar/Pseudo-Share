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
        setContentView();
        KeyboardHandler.setupUI(this,findViewById(getParentId()));
        Toolbar toolbar = getToolbar();
        if (toolbar != null)
            setSupportActionBar(toolbar);

        HashMap<Integer, Fragment> fragmentMap = getInitialFragments();
        if (fragmentMap != null) {
            for (Map.Entry<Integer, Fragment> entry : fragmentMap.entrySet()) {
                loadFragment(entry.getKey(),entry.getValue());
            }
        }


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


    public void loadFragment(int containerId,Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        int containerId=getOnBackFragmentId();
        if(getSupportFragmentManager().findFragmentById(containerId)==null)
        finish();
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

    public void assignImageViewPopup(ImageView image){

        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional

        imagePopup.initiatePopup(image.getDrawable());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.viewPopup();

            }
        });

    }

    public void onDone(){
        finish();
    }


}

interface BaseActivityInterface {
    Toolbar getToolbar();//null value wont display menu, (dont forget to set title toolbar.setTitle(""));
    void setContentView();
    int getParentId();//returns parent id for keyboard autoDismiss
    ProgressBar loadProgressBar();
    int getOnBackFragmentId();//when user clicks back button it will return which fragment container stack to pop.
    HashMap<Integer, Fragment> getInitialFragments();//return an hashmap of fragments and their container's id
}