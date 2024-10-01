package com.example.themoviedb01.movies_api;

import com.example.themoviedb01.json_mapper.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesAPI {
    // Routers!! express.js
    @GET("movie/popular?api_key=c14748134d9cf871c4002d7a8a6a4e1e") // Recoge la información de la API (es el final de la url, el inicio está en RetrofitClient)
    Call<MovieResponse> getPopularMovies();                        // Traduce la info a Java, creando objetos Movie y los mete en la lista MovieResponse
                                                                   // Esto es posible porque estamos usando un Call, un plugin de Retrofit que automatiza el proceso con estas dos líneas



}
