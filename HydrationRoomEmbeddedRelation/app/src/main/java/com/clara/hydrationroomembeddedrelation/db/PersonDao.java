package com.clara.hydrationroomembeddedrelation.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person... persons);

    @Update
    void update(Person... persons);

    @Query("SELECT * FROM Person")
    LiveData<List<Person>> getAllPeople();

    @Query("SELECT * FROM PERSON WHERE name like :name")
    LiveData<Person> getPersonByName(String name);

}
