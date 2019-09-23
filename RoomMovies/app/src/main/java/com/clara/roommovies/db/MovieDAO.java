package com.clara.roommovies.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


/*
* Data Access Object class for the movie entity.
* Maps SQL queries to functions.
*
* Insert, Update and Delete don't need SQL written - Room can figure out the SQL
* Other queries, like select, need the specifc SQL
*
* This is an interface. Room will fill in the actual implementation of the methods
*
* */

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


    @Insert
    void insert(Movie... movies);   // Insert an array of movies

    @Delete
    void delete(Movie... movies);   // Deletes an array of movies

    @Update
    void update(Movie... movies);   // Updates an array of movies




}



// add delete and update annotations for one row/obj