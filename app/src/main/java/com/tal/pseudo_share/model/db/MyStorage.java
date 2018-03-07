package com.tal.pseudo_share.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.SharedPreferences;

import com.tal.pseudo_share.data.DateConverter;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.imageStorage.ImageStorageManager;
import com.tal.pseudo_share.utilities.MyApplication;

import static android.content.Context.MODE_PRIVATE;


@Database(entities = {Pseudo.class}, version = 6)
@TypeConverters({DateConverter.class})
abstract class RoomDB extends RoomDatabase {
    public abstract PseudoDao pseudoDao();
}

public class MyStorage {
    public static RoomDB database = Room.databaseBuilder(MyApplication.getMyContext(),
            RoomDB.class,
            "pseudo_share_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();


    public static void updateLastUpdate(long lastUpdated){
        SharedPreferences.Editor editor = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();
        editor.putLong("lastUpdateDate", lastUpdated);
        editor.commit();
    }

    public static long getLastUpdate(){
        return MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).getLong("lastUpdateDate", 0);
    }

}