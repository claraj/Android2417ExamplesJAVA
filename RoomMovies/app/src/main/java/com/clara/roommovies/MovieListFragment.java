package com.clara.roommovies;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clara.roommovies.db.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MovieListFragment extends Fragment implements MovieListAdapter.ListEventListener {

    private MovieViewModel mMovieModel;
    private List<Movie> mMovies;

    private static final String TAG = "MOVIE_LIST_FRAGMENT";

    private MovieListFragmentListener mListener;

    private MovieListAdapter movieListAdapter;

    interface MovieListFragmentListener {
        void requestMakeNewMovie();
    }

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

        mMovieModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);

        final Observer<List<Movie>> movieListObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "Movies changed: " + movies);
                MovieListFragment.this.mMovies = movies;
                MovieListFragment.this.movieListAdapter.setMovies(movies);
                MovieListFragment.this.movieListAdapter.notifyDataSetChanged();
            }
        };

        mMovieModel.getAllMovies().observe(this, movieListObserver);
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
        movieListAdapter.setMovies(mMovies);
        recyclerView.setAdapter(movieListAdapter);

        final FloatingActionButton addMovieFAB = view.findViewById(R.id.add_movie_fab);
        addMovieFAB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mListener.requestMakeNewMovie();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MovieListFragmentListener) {
            mListener = (MovieListFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.getClass().getName() + " should implement MovieListFragmentListener");
        }
    }


    @Override
    public void onMovieRatingChanged(int position, float newRating) {
        Movie movie = mMovies.get(position);
        movie.setRating(newRating);
        mMovieModel.update(movie);
    }

    @Override
    public void onDeleteMovie(int position) {
        Movie movie = mMovies.get(position);
        mMovieModel.delete(movie);
    }

}
