package com.example.loginandroid_29_09_2023.beans;

public class Categorias {
    private int id;
    private String nombre;

    // Constructores
    public Categorias() {
    }

    public Categorias(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {     /// PARA MOSTRAR EL NOMBRE EN EL SPINNER
        return nombre;
    }
}