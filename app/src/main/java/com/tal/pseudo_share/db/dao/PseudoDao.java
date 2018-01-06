package com.tal.pseudo_share.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.tal.pseudo_share.db.entity.Pseudo;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by User on 29/12/2017.
 */

@Dao
public interface PseudoDao {




    @Query("SELECT * FROM Pseudo")
    LiveData<List<Pseudo>> findAllPseudos();

    @Query("SELECT * FROM Pseudo")
    List<Pseudo> findAllPseudosSync();

    @Update(onConflict = REPLACE)
    void updatePseudo(Pseudo pseudo);

    @Insert(onConflict = IGNORE)
    void insertPseudo(Pseudo pseudo);

    @Query("DELETE FROM Pseudo")
    void deleteAll();

}
