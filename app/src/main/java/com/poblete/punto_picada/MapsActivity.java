package com.poblete.punto_picada;
/*
    Version by Draigh on 25/11/2020.
 */
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poblete.punto_picada.entidades.Marcador;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;
    private float lat, lon;
    private String nom;
    private ArrayList<Marker> tmpRealTimeMarker = new ArrayList<>();
    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //
        userGetIntent();
        //
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        positionUser();
        rtMarkers();

    }

    public void rtMarkers(){
        databaseReference.child("Marcadores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (Marker marker:realTimeMarkers){
                    marker.remove();
                }

                for(DataSnapshot markers : snapshot.getChildren()){
                    Marcador marcador = markers.getValue(Marcador.class);
                    String name = marcador.getName();
                    String descripcion = marcador.getDescripcion();
                    double latitud = marcador.getLatitud();
                    double longitud = marcador.getLongitud();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitud,longitud)).title(name).snippet(descripcion)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    tmpRealTimeMarker.add(mMap.addMarker(markerOptions));
                }
                realTimeMarkers.clear();
                realTimeMarkers.addAll(tmpRealTimeMarker);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void positionUser(){
        LatLng userLugar = new LatLng(this.lat,this.lon);
        // user position
        mMap.addMarker(new MarkerOptions().position(userLugar).title(nom));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLugar,15));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    public void userGetIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lat = bundle.getFloat("Latitud");
        lon = bundle.getFloat("Longitud");
        nom = bundle.getString("Nombre");
    }
}