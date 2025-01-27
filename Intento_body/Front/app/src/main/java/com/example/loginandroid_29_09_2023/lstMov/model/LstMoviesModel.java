package com.example.loginandroid_29_09_2023.lstMov.model;

import android.util.Log;

import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.presenter.LstMoviesPresenter;
import com.example.loginandroid_29_09_2023.lstMov.data.DataMovies;
import com.example.loginandroid_29_09_2023.utils.ApiService;
import com.example.loginandroid_29_09_2023.utils.RetrofitCliente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LstMoviesModel implements ContractListMovies.Model {
    private LstMoviesPresenter presenter;

    public LstMoviesModel(LstMoviesPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void productosAPI(OnListProductosListener respuestaMovies) {
        ApiService apiService = RetrofitCliente.getClient(ApiService.URL).
                create(ApiService.class);

        Call<List<Productos>> call = apiService.getProductos();
        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                respuestaMovies.onFinished(new ArrayList<>(response.body()));
            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                respuestaMovies.onFailure(t.getMessage());
            }
        });
    }

}

