package com.tal.pseudo_share.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.tal.pseudo_share.db.dao.PseudoDao;
import com.tal.pseudo_share.db.entity.Pseudo;

/**
 * Created by User on 29/12/2017.
 */
@Database(entities = {Pseudo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PseudoDao pseudoDao();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "Pseudo_Share_db").build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
