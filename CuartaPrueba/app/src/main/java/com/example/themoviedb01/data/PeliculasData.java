package com.example.themoviedb01.data;

public class PeliculasData {

    // CLASE ENFOCADA A GUARDAR TODOS LOS DATOS IMPORTANTES DE LA APLICACIÓN EN CACHÉ
    private static String nombre;
    private static String id;


    // Getters and setters
    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        PeliculasData.nombre = nombre;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        PeliculasData.id = id;
    }
}
