package com.clara.roommovies;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MovieListFragment extends Fragment implements MovieListAdapter.ListEventListener {

    List<Movie> movies;

    private static final String TAG = "MOVIELISTFRAGMENT";

    MovieListFragmentListener listener;

    @Override
    public void onMovieRatingChanged(int position, float newRating) {

        Movie movie = movies.get(position);

        MovieDatabase db = MovieDatabase.getDatabase(this.getContext());
        db.movieDAO().update(movie);

    }

    interface MovieListFragmentListener {
        void requestMakeNewMovie();
        void requestUpdateMovie(Movie movie);

    }

    private MovieListAdapter movieListAdapter;

    public MovieListFragment() {
        // Required empty public constructor
    }


    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // observe data
        final MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        final Observer<List<Movie>> movieListObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "Movies changed" +movies);
                MovieListFragment.this.movies = movies;
                MovieListFragment.this.movieListAdapter.setMovies(movies);
                MovieListFragment.this.movieListAdapter.notifyDataSetChanged();
            }
        };

        movieViewModel.getAllMovies().observe(this, movieListObserver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.movie_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        movieListAdapter = new MovieListAdapter(this.getContext(), this);
        movieListAdapter.setMovies(movies);
        recyclerView.setAdapter(movieListAdapter);


        final FloatingActionButton addMovieFAB = view.findViewById(R.id.add_movie_fab);
       // addMovieFAB.show();
        addMovieFAB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.requestMakeNewMovie();
               // addMovieFAB.hide();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MovieListFragmentListener) {
            listener = (MovieListFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.getClass().getName() + " should implement MovieListFragmentListener");
        }

    }


}
