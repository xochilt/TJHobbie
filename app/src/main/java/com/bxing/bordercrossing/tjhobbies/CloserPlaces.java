package com.xrg.application.tjpoints;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;

public class CloserPlaces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closer_places);

        getCloserPlaces();

    }

    public void getCloserPlaces(){


        Location locationA = new Location("punto A");

        locationA.setLatitude(00.00000);
        locationA.setLongitude(00.00000);

        Location locationB = new Location("punto B");

        locationB.setLatitude(00.0000);
        locationB.setLongitude(00.00000);

        float distance = locationA.distanceTo(locationB);
    }



}
