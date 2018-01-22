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




}
