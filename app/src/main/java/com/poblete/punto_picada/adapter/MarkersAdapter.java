package com.poblete.punto_picada.adapter;
/*
    Version by Draigh on 25/11/2020.
 */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poblete.punto_picada.R;
import com.poblete.punto_picada.entidades.Marcador;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MarkersAdapter extends RecyclerView.Adapter<MarkersAdapter.viewHolderDatos> {
    ArrayList<Marcador> marcadores;
    Marcador marcador;

    public MarkersAdapter(ArrayList<Marcador> marcadores){
        this.marcadores = marcadores;
    }

    @NonNull
    @Override
    public MarkersAdapter.viewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_markers,parent,false);
        viewHolderDatos vhd = new viewHolderDatos(view);
        return vhd;
    }

    @Override
    public void onBindViewHolder(@NonNull MarkersAdapter.viewHolderDatos holder, int position) {
        holder.cargarMarkers(marcadores.get(position));
    }

    @Override
    public int getItemCount() {
        return marcadores.size();
    }

    public class viewHolderDatos extends RecyclerView.ViewHolder {

        TextView tituloNegTextView, descTextView;
        ImageView imageView2;
        public viewHolderDatos(@NonNull View itemView) {
            super(itemView);
            //
            imageView2 = itemView.findViewById(R.id.imageView2);
            tituloNegTextView = itemView.findViewById(R.id.tituloNegTextView);
            descTextView = itemView.findViewById(R.id.descTextView);
        }

        public void cargarMarkers(Marcador marcador){
            tituloNegTextView.setText(marcador.getName());
            descTextView.setText(marcador.getDescripcion());
            if(marcador.getName().equals("Fundo El Peumo")){
                Picasso.get().load(R.drawable.peumo).into(imageView2);
            }else if (marcador.getName().equals("Tío Pollo")){
                Picasso.get().load(R.drawable.pollo).into(imageView2);
            }else if (marcador.getName().equals("Mapoli")) {
                Picasso.get().load(R.drawable.mapoli).into(imageView2);
            }else if (marcador.getName().equals("Maranatha")) {
                Picasso.get().load(R.drawable.mara).into(imageView2);
            }else if (marcador.getName().equals("Manhattan")) {
                Picasso.get().load(R.drawable.manhattan).into(imageView2);
            }else if (marcador.getName().equals("Agro House")) {
                Picasso.get().load(R.drawable.agrohouse).into(imageView2);
            }else if (marcador.getName().equals("CyberEntretenciones Pepe")) {
                Picasso.get().load(R.drawable.entrecyber).into(imageView2);
            }else if (marcador.getName().equals("Super Alimentos")) {
                Picasso.get().load(R.drawable.frutossecos).into(imageView2);
            }else if (marcador.getName().equals("Frutería Jreh")) {
                Picasso.get().load(R.drawable.jreh).into(imageView2);
            }else if (marcador.getName().equals("Pastry Home Jakimioto")) {
                Picasso.get().load(R.drawable.pastry).into(imageView2);
            }
        }
    }
}
