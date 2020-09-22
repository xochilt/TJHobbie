package com.xrg.application.tjpoints;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public LatLng coordenadas;
    public String nombre;
    public double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        nombre =  getIntent().getStringExtra("nombre");
        latitud =  Double.parseDouble(getIntent().getStringExtra("latitud"));
        longitud =   Double.parseDouble(getIntent().getStringExtra("longitud"));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Zoom.
        googleMap.getUiSettings().setZoomGesturesEnabled(true); // false to disable

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        coordenadas = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(coordenadas).title(nombre));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordenadas));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 13));

    }
}
