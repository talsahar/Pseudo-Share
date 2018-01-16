package com.tal.pseudo_share.model.db.localDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tal.pseudo_share.data.Pseudo;

import java.util.List;

/**
 * Created by User on 06/01/2018.
 */
@Dao
public interface PseudoDao{
    @Query("SELECT * FROM Pseudo")
    List<Pseudo> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Pseudo... pseudos);

    @Delete
    void delete(Pseudo pseudo);



}
