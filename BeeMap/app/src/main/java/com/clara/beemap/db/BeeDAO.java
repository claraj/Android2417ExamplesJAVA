package com.clara.beemap.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BeeDAO {

    @Query("SELECT * FROM bee ORDER BY date LIMIT :results")
    LiveData<List<Bee>> getRecentBees(int results);

    @Insert
    void insert(Bee bee);

    @Delete
    void delete(Bee bee);

    @Query("DELETE FROM bee WHERE id = :id")
    void delete(int id);

    // TODO add your insert method here



}
