package com.clara.roommovies;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class AddMovieFragment extends Fragment {

    private OnMovieAddedListener newMovieListener;

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
                float rating = movieRating.getRating();   //how many stars selected!
                Movie movie = new Movie(name, rating);
                newMovieListener.onMovieAdded(movie);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        return view;
    }

//    public void onButtonPressed(Movie movie) {
//        if (newMovieListener != null) {
//            newMovieListener.onMovieAdded(movie);
//        }
//    }

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
    public void onDetach() {
        super.onDetach();
        newMovieListener = null;
    }

    public interface OnMovieAddedListener {
        void onMovieAdded(Movie movie);
    }
}
