package com.tal.pseudo_share.utilities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * Created by talsa on 07/03/2018.
 */

public class ExceptionHandler {


    private static MutableLiveData<Exception> handler=new MutableLiveData<>();

    public static void set(Exception exception){
    handler.setValue(exception);
    }

    public static MutableLiveData<Exception> get(){
        return handler;
    }

}
