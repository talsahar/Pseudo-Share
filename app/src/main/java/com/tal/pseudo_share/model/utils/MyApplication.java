package com.tal.pseudo_share.model.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

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

    public static void updateLastUpdate(long lastUpdated){
        SharedPreferences.Editor editor = context.getSharedPreferences("TAG", MODE_PRIVATE).edit();
        editor.putLong("lastUpdateDate", lastUpdated);
        editor.commit();
    }

    public static long getLastUpdate(){
        return context.getSharedPreferences("TAG", MODE_PRIVATE).getLong("lastUpdateDate", 0);
    }

    public static Context getMyContext(){
        return context;
    }
}
