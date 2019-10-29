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

    private MovieService movieService;
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

        movieService = retrofit.create(MovieService.class);
        allMovies = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getAllMovies() {

        movieService.getAllMovies().enqueue(new Callback<List<Movie>>() {
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

        movieService.get(id).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movie.setValue(response.body());
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
        movieService.insert(movie).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Inserted " + movie);
                    getAllMovies();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error inserting movie " + movie , t);
            }
        });
    }

    public void update(final Movie movie) {
        movieService.update(movie, movie.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "Updated movie " + movie);
                getAllMovies();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error updating movie " + movie , t);
            }
        });
    }

    public void delete(final Movie movie) {
        movieService.delete(movie.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "Deleted movie " + movie);
                getAllMovies();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error deleting movie " + movie , t);
            }
        });
    }



}
