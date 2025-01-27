package com.example.loginandroid_29_09_2023.lstMov;

import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.lstMov.presenter.LstMoviesPresenter;

import java.util.List;

public interface ContractListMovies {

    //////// HAY QUE HACERLO ENTERO
    interface View {
        void onSuccess(List<Productos> lstProductos);
        void onFailure(String err);
    }

    interface Presenter {
        void lstProductos(String nombre, String categoria);
    }

    interface Model {
        void productosAPI(OnLstProductosListener listener, String productName, String categoryId);

        interface OnLstProductosListener {
            void onFinished(List<Productos> lstProductos);
            void onFailure(String err);
        }
    }

}
