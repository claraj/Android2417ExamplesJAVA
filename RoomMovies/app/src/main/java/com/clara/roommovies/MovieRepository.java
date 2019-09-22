package com.clara.roommovies;

/*
* "A Repository manages query threads and allows you to use multiple backends.
* In the most common example, the Repository implements the logic for deciding
* whether to fetch data from a network or use results cached in a local database."
*
* */

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {

    private MovieDAO movieDAO;
    private LiveData<List<Movie>> allMovies;

    MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        movieDAO = db.movieDAO();
        allMovies = movieDAO.getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public void insert(Movie movie) {
        new InsertAsyncTask(movieDAO).execute(movie);
    }

    public void update(Movie movie) { new UpdateAsyncTask(movieDAO).execute(movie); }

    public void delete(Movie movie) { new DeleteAsyncTask(movieDAO).execute(movie); }

    private static class InsertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO asyncTaskDAO;

        InsertAsyncTask(MovieDAO movieDAO) {
            this.asyncTaskDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {   // varargs
            asyncTaskDAO.insert(movies[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO asyncTaskDAO;

        UpdateAsyncTask(MovieDAO movieDAO) {
            this.asyncTaskDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {   // varargs
            asyncTaskDAO.update(movies[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO asyncTaskDAO;

        DeleteAsyncTask(MovieDAO movieDAO) {
            this.asyncTaskDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {   // varargs
            asyncTaskDAO.delete(movies[0]);
            return null;
        }
    }


}

