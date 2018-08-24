package com.altimetrik.bikebuddyy;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
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
        AsyncRequest asyncRequest = new AsyncRequest(this);
        asyncRequest.getCitiBikeResponse();
        String jsonResponse = asyncRequest.getSharedPreference();

        try {
            if(!TextUtils.isEmpty(jsonResponse)){
                doJsonSerialization(jsonResponse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    private ArrayList doJsonSerialization(String response) throws JSONException {
        JSONObject curr;
        JSONObject jsonResponseObj = new JSONObject(response);;
        ArrayList<CitiBike> citiBikeArrayList = new ArrayList<>();
        JSONObject network = jsonResponseObj.getJSONObject("network");
        Log.d(TAG,"network = " + network.toString());
        JSONArray dataset = network.getJSONArray("stations");
        for(int i = 0; i < dataset.length();i++)
        {
            CitiBike citiBike = new CitiBike();
            citiBike.setEmptySlots(dataset.getJSONObject(i).getInt("empty_slots"));
            citiBike.setFreeBikes(dataset.getJSONObject(i).getInt("free_bikes"));
            citiBike.setLatitude(dataset.getJSONObject(i).getDouble("latitude"));
            citiBike.setLongitude(dataset.getJSONObject(i).getDouble("longitude"));
            citiBike.setName(dataset.getJSONObject(i).getString("name"));
            citiBikeArrayList.add(citiBike);
            Log.d(TAG,"citibike empty slot sub = " + citiBike.getEmptySlots());
            Log.d(TAG,"citibike free bikes sub = " + citiBike.getFreeBikes());
            Log.d(TAG,"citibike lat = " + citiBike.getLatitude());
            Log.d(TAG,"citibike lon = " + citiBike.getLongitude());
            Log.d(TAG,"citibike name = " + citiBike.getName());
        }

        return citiBikeArrayList;
    }
}
