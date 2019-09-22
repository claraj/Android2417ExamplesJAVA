package com.clara.roommovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clara.roommovies.db.Movie;
import com.clara.roommovies.db.MovieRepository;

import java.util.List;

/*application-aware viewmodel. all components can get a
ref to the same viewmodel via the application */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<List<Movie>> allMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        allMovies = repository.getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public void insert(Movie movie) {
        repository.insert(movie);
    }

    public void update(Movie movie) { repository.update(movie);}

    public void delete(Movie movie) { repository.delete(movie); }

    public void insert(Movie... movies) { repository.insert(movies);  }

}
