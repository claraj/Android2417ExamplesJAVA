package com.clara.hydrationroom.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDao {

    @Insert
    void insert(Record... records);

    @Query("SELECT * FROM Record WHERE personId = :personId")
    LiveData<List<Record>> getRecordsForPerson(int personId);

}
