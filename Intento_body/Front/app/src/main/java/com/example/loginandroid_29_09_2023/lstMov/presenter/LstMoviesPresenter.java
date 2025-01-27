package com.example.loginandroid_29_09_2023.lstMov.presenter;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.model.LstMoviesModel;

import java.util.ArrayList;
import java.util.List;

public class LstMoviesPresenter implements ContractListMovies.Presenter, ContractListMovies.Model.OnListProductosListener{

    private ContractListMovies.View vista;
    private LstMoviesModel lstMoviesModel;

    public LstMoviesPresenter(ContractListMovies.View vista){
        this.vista = vista;
        lstMoviesModel = new LstMoviesModel(this);
    }
    @Override
    public void lstProductos() {
        lstMoviesModel.productosAPI(this);
    }

    @Override
    public void onFinished(List<Productos> lstProductos) {
        vista.onSuccess(lstProductos);
    }

    @Override
    public void onFailure(String err) {
        vista.onFailure(err);
    }


}
