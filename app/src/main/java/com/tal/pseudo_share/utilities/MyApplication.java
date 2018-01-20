package com.tal.pseudo_share.utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.ServerValue;

/**
 * Created by User on 06/01/2018.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getMyContext(){
        return context;
    }
}
