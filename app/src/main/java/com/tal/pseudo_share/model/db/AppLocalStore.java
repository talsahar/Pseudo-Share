package com.tal.pseudo_share.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.tal.pseudo_share.model.db.localDB.PseudoDao;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.utils.MyApplication;




@Database(entities = {Pseudo.class}, version = 2)
abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract PseudoDao pseudoDao();
}

public class AppLocalStore{
    static public AppLocalStoreDb db = Room.databaseBuilder(MyApplication.getMyContext(),
            AppLocalStoreDb.class,
            "pseudo_share_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
}