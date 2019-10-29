package com.clara.apimovies.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.clara.apimovies.model.Movie;
import com.clara.apimovies.service.AuthorizationHeaderInterceptor;
import com.clara.apimovies.service.MovieService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieRepository {

    private static final String TAG = "MOVIE_REPOSITORY";

    private MovieService mMovieService;
    private String baseURL = "https://movies-2417.herokuapp.com/api/";
    final MutableLiveData<List<Movie>> allMovies;

    public MovieRepository() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationHeaderInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMovieService = retrofit.create(MovieService.class);
        allMovies = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getAllMovies() {

        mMovieService.getAllMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "getAllMovies response body: " + response.body());
                    allMovies.setValue(response.body());
                } else {
                    Log.e(TAG, "Error getting all movies, message from server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG, "Error fetching all movies", t);
            }
        });
        return allMovies;
    }

    public MutableLiveData<Movie> getMovie(final int id) {

        final MutableLiveData<Movie> movie = new MutableLiveData<>();

        mMovieService.get(id).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "fetched movie " + response.body());
                    movie.setValue(response.body());
                } else {
                    Log.e(TAG, "Error getting movie id " + id + " because " + response.message());
                    movie.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "Error getting movie id " + id , t);
            }
        });

        return movie;
    }

    public void insert(final Movie movie) {
        mMovieService.insert(movie).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "inserted " + movie);
                    getAllMovies();
                } else {
                    Log.e(TAG, "Error inserting movie, message from server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error inserting movie " + movie , t);
            }
        });
    }

    public void update(final Movie movie) {
        mMovieService.update(movie, movie.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "updated movie " + movie);
                    getAllMovies();
                }
                else {
                    Log.e(TAG, "Error updating movie, message from server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error updating movie " + movie , t);
            }
        });
    }

    public void delete(final Movie movie) {
        mMovieService.delete(movie.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "deleted movie " + movie);
                    getAllMovies();
                } else {
                    Log.e(TAG, "Error deleting movie, message from server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error deleting movie " + movie , t);
            }
        });
    }



}
