package com.example.loginandroid_29_09_2023.beans;

public class CategoriaRequest {
    private int categoria_id;

    public CategoriaRequest(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public int getCategoriaId() {
        return categoria_id;
    }

    public void setCategoriaId(int categoria_id) {
        this.categoria_id = categoria_id;
    }
}
