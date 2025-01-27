package com.example.loginandroid_29_09_2023.lstMov.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.loginandroid_29_09_2023.R;
import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.Productos;
import com.example.loginandroid_29_09_2023.image_builder.SAMBA_ANDROID;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private List<Productos> productos;

    public PeliculaAdapter(Context context, List<Productos> productos) {
        this.productos = productos;
    }

    public interface OnItemClickListener {
        void onItemClick(Pelicula pelicula);
    }

    private  OnItemClickListener listener;

    // Método para establecer el listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_card,
                            parent,
                        false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Productos productos = this.productos.get(position);
        holder.tvNombre.setText(productos.getNombre());
        holder.tvPrecio.setText(String.format("Precio: $%.2f", productos.getPrecio()));

        SAMBA_ANDROID.Builder builder = new SAMBA_ANDROID.Builder(productos.getImagen_url(), holder.ivProductoImagen);
        builder.build().load();
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void setProductos(List<Productos> productos){
        this.productos = productos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvPrecio;
        ImageView ivProductoImagen;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivProductoImagen = itemView.findViewById(R.id.ivProductoImagen);
            }
        }
    }

