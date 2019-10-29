package com.clara.apimovies.model;

import androidx.annotation.NonNull;

public class Movie {

    private int id;
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
