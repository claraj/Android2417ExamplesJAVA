package com.clara.roommovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListFragment.MovieListFragmentListener, AddMovieFragment.OnMovieAddedListener{

    private static final String TAG = "MAIN_ACTIVITY";

    private static final String TAG_MOVIE_LIST = "MovieListFragment";
    private static final String TAG_ADD_MOVIE = "AddMovieFragment";

    private MovieViewModel mvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieListFragment movieListFragment = MovieListFragment.newInstance();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(android.R.id.content, movieListFragment, TAG_MOVIE_LIST);
        ft.commit();

        // Add example movie to the DB
       // Movie m = new Movie("Hidden Figures");

        mvm = new MovieViewModel(getApplication());

       // repository.insert(m);  // insert is asynchronous - in the background

        LiveData<List<Movie>> movieList = mvm.getAllMovies();

        movieList.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "movies: " + movies);
            }
        });
    }

    @Override
    public void onMovieAdded(Movie movie) {
        mvm.insert(movie);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MovieListFragment movieListFragment = (MovieListFragment) fm.findFragmentByTag(TAG_MOVIE_LIST);
        ft.replace(android.R.id.content, movieListFragment);
        ft.commit();
    }

    @Override
    public void requestMakeNewMovie() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AddMovieFragment addMovieFragment = AddMovieFragment.newInstance();
        ft.replace(android.R.id.content, addMovieFragment, TAG_ADD_MOVIE);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void requestUpdateMovie(Movie movie) {
        mvm.update(movie);
    }

    @Override
    public void requestDeleteMovie(Movie movie) {
        mvm.delete(movie);
    }
}


