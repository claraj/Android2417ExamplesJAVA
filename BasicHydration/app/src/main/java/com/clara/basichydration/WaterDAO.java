package com.clara.basichydration;

/* Maps SQL queries to functions  */

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


/** Data access object is an interface. Define the Java methods that will map to SQL queries.
 * Room will write the actual queries for basic insert, update, and delete queries for single objects / arrays of objects
 * Write the SQL for select and delete, update queries with where clauses
 */

@Dao
public interface WaterDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)   // Another record with the same day will be ignored
    void insert(WaterRecord... wr);     // Using varargs means that the insert method can be called with any number of WaterRecords

    @Update
    void update(WaterRecord... wr);

    @Query( "SELECT * FROM WaterRecord WHERE day = :day LIMIT 1")
    LiveData<WaterRecord> getRecordForDay(String day);

    @Query("SELECT * FROM WaterRecord")
    LiveData<List<WaterRecord>> getAllRecords();

}
