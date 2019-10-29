package com.clara.apimovies.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clara.apimovies.model.Movie;
import com.clara.apimovies.R;


public class MainActivity extends AppCompatActivity implements MovieListFragment.MovieListFragmentListener, AddMovieFragment.OnMovieAddedListener{

    private static final String TAG = "MAIN_ACTIVITY";

    private static final String TAG_MOVIE_LIST = "MovieListFragment";
    private static final String TAG_ADD_MOVIE = "AddMovieFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieListFragment movieListFragment = MovieListFragment.newInstance();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(android.R.id.content, movieListFragment, TAG_MOVIE_LIST);
        ft.commit();

    }

    @Override
    public void onMovieAdded(Movie movie) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MovieListFragment movieListFragment = (MovieListFragment) fm.findFragmentByTag(TAG_MOVIE_LIST);
        if (movieListFragment != null) {
            ft.replace(android.R.id.content, movieListFragment);
            ft.commit();
        } else {
            Log.w(TAG, "Movie list fragment not found");
        }
    }

    @Override
    public void requestMakeNewMovie() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AddMovieFragment addMovieFragment = AddMovieFragment.newInstance();
        ft.replace(android.R.id.content, addMovieFragment, TAG_ADD_MOVIE);
        ft.addToBackStack(null);
        ft.commit();
    }

}


