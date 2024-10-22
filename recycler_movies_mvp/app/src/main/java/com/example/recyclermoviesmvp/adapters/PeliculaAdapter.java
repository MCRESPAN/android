package com.example.recyclermoviesmvp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclermoviesmvp.R;
import com.example.recyclermoviesmvp.beans.Pelicula;
import java.util.List;


public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private List<Pelicula> peliculas;
    private Context context;

    public PeliculaAdapter(Context context, List<Pelicula> peliculas) {
        this.context = context;
        this.peliculas = peliculas; // datos
    }

    @NonNull
    @Override
    public PeliculaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // El layoutInflater permite modificar el xml desde java
        // Crea el item_row y el ViewHolder
        // Solo se le llama la primera vez, el resto de veces se le llamará al onBindViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,
                parent,
                false);
        return new PeliculaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Asigna contenido (ya venga de un API, un arrayList...)
        Pelicula pelicula = peliculas.get(position);
        holder.tvTitulo.setText(pelicula.getTitulo()); // Asigna el titulo (por ahora no modificamos la imagen)
        holder.bind(pelicula); // Actualiza la pelicula que se ha de pintar
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }


    // VIEW HOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Tiene la misma estructura que el item_row
        public TextView tvTitulo;
        public ImageView ivPeliculaImagen;
        private Pelicula currentPelicula;  // Objeto a nivel de contenido del que se va a alimentar

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            ivPeliculaImagen = itemView.findViewById(R.id.ivPeliculaImagen);
        }

        public void bind(Pelicula pelicula) {
            // Guarda el objeto con el que va a pintar el contenido
            currentPelicula = pelicula;
        }
    }




}
