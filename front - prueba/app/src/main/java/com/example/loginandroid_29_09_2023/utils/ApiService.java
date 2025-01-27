package com.example.loginandroid_29_09_2023.utils;

import com.example.loginandroid_29_09_2023.beans.Categorias;
import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.lstMov.data.DataMovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    public static final String URL ="http://10.0.2.2:3311";  // cambio a localhost


      @Headers({
              "Accept: application/json",
              "Content-Type: application/json"
      })

      /////////////////////////////////////// DE AQUI PARA ABAJO NUEVO
      // TRAER TODOS LOS PRODUCTOS
      @GET("productos")
      Call<List<Productos>> getProductos();

      // TRAER TODAS LAS CATEGORIAS
      @GET("categorias")
      Call<List<Categorias>> getCategorias();

      // TRAER LOS PRODUCTOS DE UNA CATEGORIA
      @GET("productos/categoria/{id}")
      Call<List<Productos>> getProductosPorCategoria(@Path("id") int id);

      // PRUEBA
      @GET("producto/{nombre}/categoria/{id}")
      Call<List<Productos>> getProductoEspecifico(@Path("nombre") String nombre, @Path("id") String id);



}
