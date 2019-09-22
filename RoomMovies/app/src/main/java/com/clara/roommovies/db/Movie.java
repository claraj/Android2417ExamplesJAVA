package com.clara.roommovies.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/* Model class. Describe the columns in the table, by creating fields (instance variables) in
a Java class.  Every public variable, or every variable with a public getter, will be mapped
to a column in the database.

By default, the table name will be the same as the class name,
the column names will be the same as the field names.

*/

@Entity
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String name;

    private float rating;

    public Movie(@NonNull String name, float rating) {
        this.name = name;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}



