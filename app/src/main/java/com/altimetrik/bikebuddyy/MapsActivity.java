package com.altimetrik.bikebuddyy;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng nycLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        nycLatLong = new LatLng(40.7, 74.0);

        mMap.addMarker(new MarkerOptions().position(nycLatLong).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nycLatLong));
    }

    public double distanceCalculator(LatLng destinationLatLng){

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(nycLatLong.latitude);
        startPoint.setLongitude(nycLatLong.longitude);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(destinationLatLng.latitude);
        endPoint.setLongitude(destinationLatLng.longitude);

        double distance = startPoint.distanceTo(endPoint);

        return distance;
    }

}
