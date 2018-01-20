package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by User on 20/01/2018.
 */

public class StaticMutablesHolder {

    public static MutableLiveData<Boolean> progressStatus=new MutableLiveData<>();
    public static MutableLiveData<Exception> exceptionMutableLiveData=new MutableLiveData<>();

    public static void bindProgressBar(LifecycleOwner lifecycleOwner, final ProgressBar progressBar){
        progressStatus.observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static void bindException(final LifecycleOwner lifecycleOwner){
        exceptionMutableLiveData.observe(lifecycleOwner, new Observer<Exception>() {
            @Override
            public void onChanged(@Nullable Exception e) {
                Toast.makeText((Context) lifecycleOwner, exceptionMutableLiveData.getValue().getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }


}
