package com.example.themoviedb01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.themoviedb01.json_mapper.Movie;
import com.example.themoviedb01.json_mapper.MovieResponse;
import com.example.themoviedb01.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Botones
    private Button btnPeliculasPopulares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // PRIMER BOTÓN
        // Inicializar el botón Peliculas Populares
        btnPeliculasPopulares = findViewById(R.id.btnPeliculasPopulares);

        // Configurar el listener de clic
        btnPeliculasPopulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí llamamos a la funcionalidad que ya tienes desarrollada
                fetchPeliculasPopulares();
            }
        });

        // Método para hacer la solicitud de películas (asumiendo que ya lo tienes implementado)
        private void fetchPeliculasPopulares() {
            Call<MovieResponse> call = RetrofitClient.getInstance().getPopularMovies(); // Consigue la instancia del endpoint
            call.enqueue(new Callback<MovieResponse>() {
                // En caso de exito
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {  // Verifica que la respuesta sea exitosa
                        List<Movie> movies = response.body().getResults(); // Obtiene los datos
                        // Procesa y muestra las películas aquí
                        for (Movie myMovie : movies) {
                            Toast.makeText(MainActivity.this,
                                    "Movie: " + myMovie.getTitle(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                // En caso de error
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    // Maneja el error aquí

                }
            });
        }
        // FIN PRIMER BOTÓN
    }
}