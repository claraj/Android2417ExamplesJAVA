package com.clara.wordsapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WordService {

    @GET("{word}")
    Call<Word> getDefinition(@Path("word") String word, @Header("Authorization") String token);
}


