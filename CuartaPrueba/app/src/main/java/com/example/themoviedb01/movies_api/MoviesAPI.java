package com.example.themoviedb01.movies_api;

import com.example.themoviedb01.json_mapper.Movie;
import com.example.themoviedb01.json_mapper.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {
    // Routers!! express.js
    // Metodo para obtener las películas populares
    @GET("movie/popular?api_key=c14748134d9cf871c4002d7a8a6a4e1e") // Recoge la información de la API (es el final de la url, el inicio está en RetrofitClient)
    Call<MovieResponse> getPopularMovies();                        // Traduce la info a Java, creando objetos Movie y los mete en la lista MovieResponse
                                                                   // Esto es posible porque estamos usando un Call, un plugin de Retrofit que automatiza el proceso con estas dos líneas

    // Metodo para obtener las peliculas que hay según su nombre
    @GET("search/movie?api_key=c14748134d9cf871c4002d7a8a6a4e1e&language=es-ES")
    Call<MovieResponse> getPeliculaNombre(@Query("query") String nombre);  // El @Query nos permite pasar los datos de la consulta de manera dinámica (en este caso, le estamos diciendo que
                                                                           // añada un parámetro llamado query con el valor de la variable nombre)

    // Metodo para obtener los detalles de la película según su id
    @GET("movie/{movie_id}?api_key=c14748134d9cf871c4002d7a8a6a4e1e&language=es-ES")
    Call<Movie> getPeliculaId(@Path("movie_id") String id);           // El @Path nos permite pasar los datos de la consulta de manera dinámica mediante el {}


}
