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
import android.widget.Spinner;
import android.widget.Toast;
import retrofit2.Callback;

import com.example.loginandroid_29_09_2023.MainActivity;
import com.example.loginandroid_29_09_2023.R;
import com.example.loginandroid_29_09_2023.beans.Categorias;
import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.adapters.PeliculaAdapter;
import com.example.loginandroid_29_09_2023.lstMov.presenter.LstMoviesPresenter;
import com.example.loginandroid_29_09_2023.utils.ApiService;
import com.example.loginandroid_29_09_2023.utils.RetrofitCliente;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LstMovies extends AppCompatActivity
            implements ContractListMovies.View {    ///// Añadir el implements
    private LstMoviesPresenter presenter;
    private RecyclerView recyclerView;
    private PeliculaAdapter adapter;
    ///////// NUEVO
    private Spinner spinner;
    private ApiService apiService;
    //////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_movies4);

        /////////// NUEVO
        presenter = new LstMoviesPresenter(this);
        presenter.lstProductos();

        spinner = findViewById(R.id.spinnerCategorias);
        apiService = RetrofitCliente.getClient(ApiService.URL).create(ApiService.class);

        // Cargar categorías en el Spinner
        cargarCategorias();

        // Configurar el listener del Spinner
        configurarSpinnerListener();
        ///////////
    }


    ////////////////////////// CODIGO SPINNER DEL PROFESOR
    private void cargarCategorias() {
        Call<List<Categorias>> call = apiService.getCategorias();

        call.enqueue(new Callback<List<Categorias>>() {
            @Override
            public void onResponse(Call<List<Categorias>> call, Response<List<Categorias>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Categorias> listaCategorias = response.body();

                    // Crear adaptador y asignarlo al Spinner
                    ArrayAdapter<Categorias> adapter = new ArrayAdapter<>(
                            spinner.getContext(),  //////////////////////////// MODIFICADO
                            android.R.layout.simple_spinner_item,
                            listaCategorias
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } else {
                    Toast.makeText(spinner.getContext(), "Error al cargar categorías", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categorias>> call, Throwable t) {
                Toast.makeText(spinner.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isFirstSelection = true;      /////////// NUEVO para evitar que se llame al listener al abrir la actividad
    private void configurarSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /////////////// NUEVO para evitar que se llame al listener al abrir la actividad
                if (isFirstSelection) {
                    // Ignorar la primera selección al cargar la app
                    isFirstSelection = false;
                    return;
                }
                //////////////////////////////

                // Obtener la categoría seleccionada
                Categorias categoriaSeleccionada = (Categorias) parent.getItemAtPosition(position);

                // Mostrar el nombre de la categoría seleccionada
                Toast.makeText(spinner.getContext(), "Seleccionado: " + categoriaSeleccionada.getNombre(), Toast.LENGTH_SHORT).show();

                /////////////////////////// NUEVO
                // Llamar al método para obtener los productos de la categoría seleccionada
                cargarProductosPorCategoria(categoriaSeleccionada.getId());
                ///////////////////////////////////////////7
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Opcional: manejar caso de no selección
            }
        });
    }

    ////////////////////// NUEVO
    private void cargarProductosPorCategoria(int categoriaId) {
        Call<List<Productos>> call = apiService.getProductosPorCategoria(categoriaId);

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Productos> listaProductos = response.body();

                    // Actualizar el RecyclerView con los productos obtenidos
                    adapter.setProductos(listaProductos);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(LstMovies.this, "Error al cargar productos de la categoría", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                Toast.makeText(LstMovies.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    ////////////////////////////////////////////
    //////////////////////////////////////////////////////////


    @Override
    public void onSuccess(List<Productos> lstProductos) {
        recyclerView = findViewById(R.id.recyclerViewPeliculas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PeliculaAdapter(this, lstProductos);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(String err) {
        //////////// NUEVO
        Toast.makeText(this, "Error: " + err, Toast.LENGTH_SHORT).show();
        //////////////////////////
    }
}