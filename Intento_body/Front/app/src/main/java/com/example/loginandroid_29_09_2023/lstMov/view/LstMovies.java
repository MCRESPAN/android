package com.example.loginandroid_29_09_2023.lstMov.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.loginandroid_29_09_2023.beans.CategoriaRequest;
import com.example.loginandroid_29_09_2023.MainActivity;
import com.example.loginandroid_29_09_2023.R;
import com.example.loginandroid_29_09_2023.beans.Categorias;
import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.adapters.PeliculaAdapter;
import com.example.loginandroid_29_09_2023.lstMov.data.DataMovies;
import com.example.loginandroid_29_09_2023.lstMov.presenter.LstMoviesPresenter;
import com.example.loginandroid_29_09_2023.utils.ApiService;
import com.example.loginandroid_29_09_2023.utils.RetrofitCliente;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LstMovies extends AppCompatActivity
            implements ContractListMovies.View{
    private LstMoviesPresenter presenter;
    private RecyclerView recyclerView;
    private PeliculaAdapter adapter;
    private Spinner spinner;
    private ApiService apiService;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_movies4);

        presenter = new LstMoviesPresenter(this);
        presenter.lstProductos();
        spinner = findViewById(R.id.spinnerCategorias);
        btnSearch = findViewById(R.id.btnSearch);

        apiService = RetrofitCliente.getClient(ApiService.URL).create(ApiService.class);
        cargarCategorias();
        configurarSpinnerListener();
    }

    private void cargarCategorias() {
        Call<List<Categorias>> call = apiService.getCategorias();

        call.enqueue(new Callback<List<Categorias>>() {
            @Override
            public void onResponse(Call<List<Categorias>> call, Response<List<Categorias>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Categorias> listaCategorias = response.body();
                    ArrayAdapter<Categorias> adapter = new ArrayAdapter<>(
                            spinner.getContext(),
                            android.R.layout.simple_spinner_item,
                            listaCategorias
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } else {
                    Toast.makeText(LstMovies.this, "Error al cargar categorías", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categorias>> call, Throwable t) {
                Toast.makeText(LstMovies.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    // Configurar el evento de selección del Spinner
    private void configurarSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener la categoría seleccionada
                Categorias categoriaSeleccionada = (Categorias) parent.getItemAtPosition(position);

                // Mostrar la categoría seleccionada (nombre)
                Toast.makeText(LstMovies.this, "Seleccionado: " + categoriaSeleccionada.getNombre(), Toast.LENGTH_SHORT).show();

                // Aquí puedes realizar otras acciones, por ejemplo, cargar productos según la categoría seleccionada
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cargarProductosPorCategoria(categoriaSeleccionada.getId());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Opcional: manejar el caso cuando no se selecciona nada
            }
        });
    }

    private void cargarProductosPorCategoria(int categoriaId){

        CategoriaRequest request = new CategoriaRequest(categoriaId); // Nuevo

        Call<List<Productos>> call = apiService.getProductosPorCategoria(request);
        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                if(response.isSuccessful() && response.body()!=null){

                        List<Productos> listaProductos = response.body();
                        adapter.setProductos(listaProductos);
                        adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


    @Override
    public void onSuccess(List<Productos> lstProductos) {
        recyclerView = findViewById(R.id.recyclerViewPeliculas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PeliculaAdapter(this, lstProductos);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(String err) {

    }
}