package com.clara.roommovies;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clara.roommovies.db.Movie;

public class AddMovieFragment extends Fragment {

    private OnMovieAddedListener newMovieListener;

    private MovieViewModel movieModel;

    public interface OnMovieAddedListener {
        void onMovieAdded(Movie movie);
    }

    public AddMovieFragment() {
        // Required empty public constructor
    }

    public static AddMovieFragment newInstance() {
        AddMovieFragment fragment = new AddMovieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_movie, container, false);

        final TextView movieName = view.findViewById(R.id.new_movie_name_input);
        final RatingBar movieRating = view.findViewById(R.id.new_movie_rating);
        Button add = view.findViewById(R.id.add_movie_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = movieName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(AddMovieFragment.this.getContext(), "Enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                float rating = movieRating.getRating();   //how many stars selected
                Movie movie = new Movie(name, rating);
                movieModel.insert(movie);
                newMovieListener.onMovieAdded(movie);  // notifies Activity so fragments can be swapped

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieAddedListener) {
            newMovieListener = (OnMovieAddedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieRatingChanged");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        movieModel = ViewModelProviders.of(this).get(MovieViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        newMovieListener = null;
    }
}
