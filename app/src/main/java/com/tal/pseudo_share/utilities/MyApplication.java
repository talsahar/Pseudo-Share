package com.tal.pseudo_share.utilities;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.firebase.database.ServerValue;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by User on 06/01/2018.
 */

public class MyApplication extends Application {
    private static Context context;
    //on app start
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());



        // Todo: storage permission.
        //StoragePermission.verifyStoragePermissions();
    }
    public static Context getMyContext(){
        return context;
    }
}
