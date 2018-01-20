package com.tal.pseudo_share.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.tal.pseudo_share.data.Pseudo;

import java.util.List;

/**
 * Created by User on 06/01/2018.
 */
@Dao
public interface PseudoDao{
    @Query("SELECT * FROM Pseudo")
    List<Pseudo> selectAll();

    @Query("SELECT * FROM Pseudo WHERE id IN (:userIds)")
    List<Pseudo> loadByIds(int[] userIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pseudo... pseudos);

    @Update
    public void update(Pseudo... pseudos);

    @Delete
    public void delete(Pseudo... users);
}
