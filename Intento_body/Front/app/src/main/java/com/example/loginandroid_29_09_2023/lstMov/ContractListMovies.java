package com.example.loginandroid_29_09_2023.lstMov;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.beans.User;

import java.util.ArrayList;
import java.util.List;

public interface ContractListMovies {
    interface View{
        void onSuccess(List<Productos> lstProductos);
        void onFailure(String err);
    }

    interface Presenter{
        void lstProductos();
    }

    interface Model{
        void productosAPI(OnListProductosListener listener);
        interface OnListProductosListener{
            void onFinished(List<Productos> lstProductos);
            void onFailure(String err);
        }
    }

}
