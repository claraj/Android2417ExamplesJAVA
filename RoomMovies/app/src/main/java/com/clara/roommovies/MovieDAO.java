package com.clara.roommovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void insert(Movie movie);

    @Query( "SELECT * from Movie ORDER BY name ASC")
    LiveData<List<Movie>> getAllMovies();

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

}



// add delete and update annotations for one row/obj