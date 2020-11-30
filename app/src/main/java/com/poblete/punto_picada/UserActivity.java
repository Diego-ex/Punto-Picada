package com.poblete.punto_picada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    TextView titleTextView, emailTextView;
    MaterialButton btnLogOut, btnGoMaps, agregarLocalesButton;
    FusedLocationProviderClient fused;
    EditText latitudEditText, longitudEditText, nomEditText, descEditText;

    FirebaseDatabase database;
    DatabaseReference rDatabase;
    private double latitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        titleTextView = findViewById(R.id.titleTextView);
        emailTextView = findViewById(R.id.emailTextView);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnGoMaps = findViewById(R.id.btnGoMaps);
        latitudEditText = findViewById(R.id.latitudEditText);
        longitudEditText = findViewById(R.id.longitudEditText);
        nomEditText = findViewById(R.id.nomEditText);
        descEditText = findViewById(R.id.descEditText);
        //boton para agregar
        agregarLocalesButton = findViewById(R.id.agregarLocalesButton);

        userEmailPerfil();
        //Inicializando el objeto fusedLocation
        fused = LocationServices.getFusedLocationProviderClient(UserActivity.this);

        //latitudEditText.setEnabled(false);
        //longitudEditText.setEnabled(false);

        coordenadasUser();
        connectFirebaseDataBase();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnGoMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miUbi();
            }
        });

        agregarLocalesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarMarcador();
            }
        });

    }




















    public void connectFirebaseDataBase(){
        database = FirebaseDatabase.getInstance();
        rDatabase = database.getReference();
        //msjToast("Conectado a firebase");
    }

    public void userEmailPerfil(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            emailTextView.setText(user.getEmail());
        }
    }

    public void miUbi(){
        float lat, lon;
        String nom = "Yo";//para agregar otro marcador cambia el nombre dg

        lat = Float.parseFloat(latitudEditText.getText().toString());
        lon = Float.parseFloat(longitudEditText.getText().toString());

        Intent gMap = new Intent(UserActivity.this, MapsActivity.class);
        gMap.putExtra("Latitud", lat);
        gMap.putExtra("Longitud", lon);
        gMap.putExtra("Nombre", nom);
        startActivity(gMap);
    }

    public void coordenadasUser(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(UserActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        fused.getLastLocation().addOnSuccessListener(UserActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    //ubicacion dispositivo usuario
                    latitudEditText.setText(String.valueOf(location.getLatitude()));
                    longitudEditText.setText(String.valueOf(location.getLongitude()));

                    //agregar marcadores en google maps

                }
            }
        });
    }

    public void agregarMarcador(){

    }

    public void msjToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }


}