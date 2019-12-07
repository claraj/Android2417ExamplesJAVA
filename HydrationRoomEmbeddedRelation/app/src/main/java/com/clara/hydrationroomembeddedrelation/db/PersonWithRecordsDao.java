package com.clara.hydrationroomembeddedrelation.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface PersonWithRecordsDao {

//    @Insert
//    public void insert(PersonWithRecords... personWithRecords);
//

    @Transaction
    @Query("SELECT * FROM Person WHERE name like :name")
    public LiveData<PersonWithRecords> getPersonWithRecords(String name);

}
