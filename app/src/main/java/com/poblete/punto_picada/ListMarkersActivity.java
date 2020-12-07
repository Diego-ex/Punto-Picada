package com.poblete.punto_picada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poblete.punto_picada.adapter.MarkersAdapter;
import com.poblete.punto_picada.entidades.Marcador;

import java.util.ArrayList;

public class ListMarkersActivity extends AppCompatActivity {

    TextView picadaLista;
    RecyclerView recyclerMarcador;
    //
    FirebaseDatabase database;
    DatabaseReference rDatabase;
    //
    ArrayList<Marcador> arrayListMarcadores;
    Marcador marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_markers);
        //
        referencias();
        //
        recyclerMarcador.setLayoutManager(new LinearLayoutManager(this));
        //
        arrayListMarcadores = new ArrayList<>();
        //
        conectFirebase();
        //
        cargarMarcadores();
    }
    //
    public void cargarMarcadores(){
        rDatabase.child("Marcadores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListMarcadores.clear();
                for(DataSnapshot marker : snapshot.getChildren()){
                    marcador = marker.getValue(Marcador.class);
                    arrayListMarcadores.add(marcador);
                }
                //
                MarkersAdapter adapter = new MarkersAdapter(arrayListMarcadores);
                //
                recyclerMarcador.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //
    private void conectFirebase() {
        database = FirebaseDatabase.getInstance();
        rDatabase = database.getReference();
        msjToast("Conectado");
    }
    //
    public void referencias(){
        picadaLista = findViewById(R.id.picadaLista);
        recyclerMarcador = findViewById(R.id.recyclerMarcador);
    }

    public void msjToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}