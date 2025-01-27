package com.example.loginandroid_29_09_2023.lstMov.presenter;

import android.content.Context;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.model.LstMoviesModel;

import java.util.ArrayList;
import java.util.List;

public class LstMoviesPresenter implements ContractListMovies.Presenter, ContractListMovies.Model.OnLstProductosListener{ ////// Añadido el implements

    private ContractListMovies.View vista;
    private LstMoviesModel lstMoviesModel;

    public LstMoviesPresenter(ContractListMovies.View vista){
        this.vista = vista;
        lstMoviesModel = new LstMoviesModel(this);
    }
    @Override
    public void lstProductos(String nombre, String id) {   /// Borrar argumento
        lstMoviesModel.productosAPI(this, nombre, id);    /// Añadir
    }


    @Override
    public void onFinished(List<Productos> lstProductos) {
        ///////////////// NUEVO
        vista.onSuccess(lstProductos);
        /////////////////
    }

    @Override
    public void onFailure(String err) {
        ///////////////// NUEVO
        vista.onFailure(err);
        /////////////////
    }
}
