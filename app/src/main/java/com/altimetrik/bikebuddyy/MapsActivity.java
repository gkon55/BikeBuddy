package com.altimetrik.bikebuddyy;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.text.TextUtils;
import android.util.Log;
=======
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
>>>>>>> ba56cb47a46b6371e8aa9767d2e95604ae5b1187

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
=======
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

>>>>>>> ba56cb47a46b6371e8aa9767d2e95604ae5b1187
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private LatLng nycLatLang;
    private int range;
    private SeekBar seekBar;
    private TextView seekBarTextView;
    private ProgressBar progressBar;
    private    ArrayList<CitiBike> citiBikeArrayList = new ArrayList<>();
    private ArrayList<CitiBike> nearestCitiBikeArrayList = new ArrayList();
    private HashMap<Marker,String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
<<<<<<< HEAD
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
=======

        initialize();

>>>>>>> ba56cb47a46b6371e8aa9767d2e95604ae5b1187
    }

    private void initialize(){
        nycLatLang = new LatLng(40.7589, -73.9851);
        range = 1;
        progressBar = findViewById(R.id.progressBar);
        seekBarTextView = findViewById(R.id.seekBar_textView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(1);
        seekBar.incrementProgressBy(1);
        seekBarTextView.setText("Locations within " + range + " mile");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                range = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarTextView.setText("Locations within " + range + " mile");
                updateMap(distanceCalculator(citiBikeArrayList, range));
            }
        });

        new CitiBikeAsyncTask(this).execute();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nycLatLang));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nycLatLang, 16));
    }

    private  ArrayList parseCitiBikeResponse (String response) throws JSONException {
        JSONObject responseJsonObject =  new JSONObject(response);
        JSONObject networkJSONObj = responseJsonObject.getJSONObject("network");
        JSONArray stationsJSONArray = networkJSONObj.getJSONArray("stations");

        for(int i = 0; i < stationsJSONArray.length();i++)
        {
            CitiBike citiBike = new CitiBike();
            citiBike.setEmptySlots(stationsJSONArray.getJSONObject(i).getInt("empty_slots"));
            citiBike.setFreeBikes(stationsJSONArray.getJSONObject(i).getInt("free_bikes"));
            citiBike.setLatitude(stationsJSONArray.getJSONObject(i).getDouble("latitude"));
            citiBike.setLongitude(stationsJSONArray.getJSONObject(i).getDouble("longitude"));
            citiBike.setName(stationsJSONArray.getJSONObject(i).getString("name"));
            citiBikeArrayList.add(citiBike);
        }
        return citiBikeArrayList;
    }
    public class CitiBikeAsyncTask extends AsyncTask<Void, Void, String> {

        private WeakReference<Activity> activityWeakReference;
        private final ThreadLocal<ProgressBar> progressBar = new ThreadLocal<>();
        private String URL = "https://api.citybik.es/v2/networks/citi-bike-nyc";;


        private CitiBikeAsyncTask(Activity activity){
            this.activityWeakReference = new WeakReference<>(activity);
            this.progressBar.set((ProgressBar) activityWeakReference.get().findViewById(R.id.progressBar));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.get().setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String response = "";
            try {
                response =  NetworkRequest.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                //citiBikeArrayList = new ArrayList<>();
                citiBikeArrayList.addAll(parseCitiBikeResponse(response));
                updateMap(distanceCalculator(citiBikeArrayList, range));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.get().setVisibility(View.INVISIBLE);
        }
    }

    public ArrayList distanceCalculator(ArrayList<CitiBike> citiBikeArrayList, int range){

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(nycLatLang.latitude);
        startPoint.setLongitude(nycLatLang.longitude);
        nearestCitiBikeArrayList.clear();
        if(citiBikeArrayList.size()>0){
            for(CitiBike citiBike: citiBikeArrayList){
                Location endPoint=new Location("locationA");
                endPoint.setLatitude(citiBike.getLatitude());
                endPoint.setLongitude(citiBike.getLongitude());
                int distance = (int) (startPoint.distanceTo(endPoint) * 0.00062137);
                if(distance < range){
                    nearestCitiBikeArrayList.add(citiBike);
                }
            }
        }
        return nearestCitiBikeArrayList;
    }

    public void updateMap(ArrayList<CitiBike> nearestCitiBikeArrayList) {

        mMap.clear();
        if(nearestCitiBikeArrayList.size()>0){
            for(CitiBike citiBike: nearestCitiBikeArrayList){
                LatLng latLngObj = new LatLng(citiBike.getLatitude(), citiBike.getLongitude());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(citiBike.getName());
                stringBuilder.append(System.getProperty("line.separator"));
                stringBuilder.append("Free Bikes: ");
                stringBuilder.append(citiBike.getFreeBikes());
                stringBuilder.append(System.getProperty("line.separator"));
                stringBuilder.append("Empty Slots: ");
                stringBuilder.append(citiBike.getEmptySlots());

                MarkerOptions markerOptions = new MarkerOptions().position(latLngObj);
                if(citiBike.getEmptySlots()<2 || citiBike.getFreeBikes() < 2){
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.low_capacity_marker));
                }else{
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.medium_capacity_marker));
                }
                Marker marker = mMap.addMarker(markerOptions);
                hashMap.put(marker,stringBuilder.toString());

            }
            //Redraw current position maker
            MarkerOptions markerOptions = new MarkerOptions().position(nycLatLang).title("Current Location");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.current_color_pin));
            //mMap.addMarker(markerOptions);
            Marker marker = mMap.addMarker(markerOptions);
            hashMap.put(marker,"Current Location");
            mMap.moveCamera(CameraUpdateFactory.newLatLng(nycLatLang));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nycLatLang, 16));
            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(hashMap, this));

        }



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

<<<<<<< HEAD
        return citiBikeArrayList;
    }
}
=======
}
>>>>>>> ba56cb47a46b6371e8aa9767d2e95604ae5b1187
