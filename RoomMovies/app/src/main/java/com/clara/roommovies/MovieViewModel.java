package com.clara.roommovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clara.roommovies.db.Movie;
import com.clara.roommovies.db.MovieRepository;

import java.util.List;

/* Application-aware ViewModel. Connects the UI to the database.
All components can get a reference to the same ViewModel object via the application */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;
    private LiveData<List<Movie>> mAllMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
        mAllMovies = mMovieRepository.getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return mAllMovies;
    }

    public void insert(Movie movie) {
        mMovieRepository.insert(movie);
    }

    public void update(Movie movie) { mMovieRepository.update(movie);}

    public void delete(Movie movie) { mMovieRepository.delete(movie); }

    public void insert(Movie... movies) { mMovieRepository.insert(movies);  }

}
