package com.example.themoviedb01.retrofit;

import com.example.themoviedb01.movies_api.MoviesAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static MoviesAPI instance;

    // Singleton
    public static MoviesAPI getInstance() {
        if (instance == null) {
            // Builder
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON, ponerlo siempre así
                    .build();

            instance = retrofit.create(MoviesAPI.class);
        }

        return instance;
    }
}
