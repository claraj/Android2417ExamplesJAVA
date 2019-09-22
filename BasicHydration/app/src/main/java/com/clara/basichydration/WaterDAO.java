package com.clara.basichydration;

/* Maps SQL queries to functions  */

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface WaterDAO {

    @Insert
    void insert(WaterRecord... wr);

    @Update
    void update(WaterRecord... wr);

    @Query( "SELECT * FROM waterrecord WHERE dayOfWeek = :day")
    LiveData<WaterRecord> getRecordForDate(String day);

    @Query("SELECT * FROM WaterRecord")
    LiveData<List<WaterRecord>> getAllRecords();

}
