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
* Insert, Update and Delete don't need SQL - Room can figure out the SQL needed.
* Other queries, like select, need the specific SQL
*
* This is an interface. Room will fill in the actual implementation of the methods
*
* */

@Dao
public interface MovieDAO {

    @Query( "SELECT * from Movie ORDER BY UPPER(name) ASC")
    LiveData<List<Movie>> getAllMovies();

    @Insert
    void insert(Movie movie);

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