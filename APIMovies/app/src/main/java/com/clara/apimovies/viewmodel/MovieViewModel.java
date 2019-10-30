package com.clara.apimovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.clara.apimovies.model.Movie;
import com.clara.apimovies.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;

    private MutableLiveData<List<Movie>> allMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mMovieRepository = new MovieRepository();
        allMovies = mMovieRepository.getAllMovies();
    }

    public MutableLiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    // Not used in this app, but getting an individual item by ID is a common task.
    public MutableLiveData<Movie> getMovie(int id) {
        return mMovieRepository.getMovie(id);
    }

    public MutableLiveData<String> insert(Movie movie) {
        return mMovieRepository.insert(movie);
    }

    public void update(Movie movie) {
        mMovieRepository.update(movie);
    }

    public void delete(Movie movie) {
        mMovieRepository.delete(movie);
    }



}
