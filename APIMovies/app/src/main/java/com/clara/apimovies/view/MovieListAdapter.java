package com.clara.apimovies.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.clara.apimovies.Movie;
import com.clara.apimovies.R;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private final static String TAG = "MOVIE_LIST_ADAPTER";

    interface ListEventListener {
        void onMovieRatingChanged(int position, float newRating);
        void onDeleteMovie(int position);
    }

    private ListEventListener mListener;

    private List<Movie> mMovies;  // reference to list of movies.

    MovieListAdapter(Context context, ListEventListener eventListener) {
        this.mListener = eventListener;
    }

    void setMovies(List<Movie> movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (mMovies != null) {
            Movie movie = mMovies.get(position);
            holder.bind(movie);
        } else {
            holder.bind(null);
        }
    }

    @Override
    public int getItemCount() {

        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private final TextView movieNameView;
        private final RatingBar ratingBar;
        private final ImageButton deleteButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieNameView = itemView.findViewById(R.id.movie_name);
            ratingBar = itemView.findViewById(R.id.movie_rating);
            deleteButton = itemView.findViewById(R.id.delete_movie_button);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float value, boolean fromUser) {
                    if (fromUser) {
                        mListener.onMovieRatingChanged(getAdapterPosition(), value);
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteMovie(getAdapterPosition());
                }
            });
        }

        void bind(Movie movie) {
            //Helper method to update the data shown in a ViewHolder
            Log.d(TAG, "binding movie " + movie);
            if (movie == null) { movieNameView.setText(""); ratingBar.setNumStars(0); }
            else {
                movieNameView.setText(movie.getName());
                ratingBar.setRating(movie.getRating());
            }
        }
    }
}
