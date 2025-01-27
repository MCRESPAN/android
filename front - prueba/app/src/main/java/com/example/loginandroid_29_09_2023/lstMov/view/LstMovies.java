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
import android.widget.EditText;
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
    private EditText etProductName, etCategoryId;
    private Button btnSearch;
    //////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_movies4);

        /////////// NUEVO
        etProductName = findViewById(R.id.etProductName);
        etCategoryId = findViewById(R.id.etCategoryId);
        btnSearch = findViewById(R.id.btnSearch);

        presenter = new LstMoviesPresenter(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString();
                String categoryId = etCategoryId.getText().toString();

                if (productName.isEmpty() || categoryId.isEmpty()) {
                    Toast.makeText(LstMovies.this, "Por favor, llena ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.lstProductos(productName, categoryId);
                }
            }
        });
        //////////////
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
        Toast.makeText(this, "Error: " + err, Toast.LENGTH_SHORT).show();
    }
}