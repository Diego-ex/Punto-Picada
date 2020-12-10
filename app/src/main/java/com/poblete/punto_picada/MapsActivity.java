package com.poblete.punto_picada;
/*
    Version by Draigh on 25/11/2020.
 */

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.poblete.punto_picada.entidades.Marcador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;
    Boolean positionAct = true;
    //JSONObject jso;
    Double latitudUser, longitudUser;
    /*private float lat, lon;
    private String nom;*/
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
        //userGetIntent();
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
        permissionMaps();
        //positionUser();
        rtMarkers();
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (positionAct){
                    latitudUser = location.getLatitude();
                    longitudUser = location.getLongitude();
                    positionAct = false;

                    LatLng userPosition = new LatLng(latitudUser,longitudUser);
                    mMap.addMarker(new MarkerOptions().position(userPosition).title("Aquí estás! por ahora.."));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitudUser, longitudUser))
                            .zoom(17)
                            .bearing(90)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    // lo de acá no funciona sin la key de pago :c The provided API key is invalid.
                    /*
                    String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+latitudUser+","+longitudUser+"&destination=-36.835869,-73.002875&key=AIzaSyD2Ll3W84Gc4_PTZcp31xpmqSoFHtmz2Fk";
                    
                    RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                jso = new JSONObject(response);
                                trazarRuta(jso);
                                Log.i("jsonRuta: ", ""+response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    queue.add(stringRequest);*/
                }
            }
        });
    }

    //No funciona sin la key de pago :c
    /*
    {
   "error_message" : "The provided API key is invalid.",
   "routes" : [],
   "status" : "REQUEST_DENIED"
    }
    private void trazarRuta(JSONObject jso) {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length(); i++){

                jLegs = ((JSONObject) (jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length(); j++){

                    jSteps = ((JSONObject) (jLegs.get(i))).getJSONArray("steps");

                    for (int k=0; k<jSteps.length(); k++){


                        String polyline = ""+((JSONObject) ((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end", ""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(5));

                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int [] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }else{
                }
                return;
        }
    }

    public void permissionMaps(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                //
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                //
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }

            return;
        }
        mMap.setMyLocationEnabled(true);
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

    /*public void positionUser(){
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
    }*/
}