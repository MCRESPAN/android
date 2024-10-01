package com.example.themoviedb01.json_mapper;

import java.util.List;

public class MovieResponse {

    // URL del endpoint de la API: https://api.themoviedb.org/3/movie/popular?api_key=c14748134d9cf871c4002d7a8a6a4e1e

    // Esto es la lista con los objetos Movie
    private List<Movie> results;


    // Getters y Setters
    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
