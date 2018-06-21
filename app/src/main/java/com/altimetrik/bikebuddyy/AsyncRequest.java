package com.altimetrik.bikebuddyy;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.goebl.david.Webb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SFLDPGUSER-24 on 6/21/2018.
 */

public class AsyncRequest {

    private static final String TAG = "asyncRequests";

    //Webb is Lightweight Java HTTP-Client for calling JSON REST-Service
    final Webb webb = Webb.create();

    //Server URL to make the request to and from
    String serverURL = "https://api.citybik.es/v2/networks";

    public AsyncRequest()
    {
        webb.setBaseUri(serverURL);
    }

    @SuppressLint("StaticFieldLeak")
    public JSONObject getCitiBikeResponse()
    {
        new AsyncTask< Void, Void, JSONObject >()
        {
            @Override
            protected JSONObject doInBackground(Void...params)
            {
                JSONObject response = new JSONObject();
                try
                {
                    response = webb
                            .post("/citi-bike-nyc")
                            .asJsonObject()
                            .getBody();
                    Log.d(TAG, "response = " + response.toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return response;
            }
            @Override
            protected void onPostExecute(JSONObject result)
            {
                try {
                    passData(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
        return null;
    }

    private void passData(JSONObject result) throws JSONException {
        JSONObject curr;
        ArrayList<CitiBike> citiBikeArrayList = new ArrayList<>();
        JSONObject network = result.getJSONObject("network");
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
            Log.d(TAG,"citibike empty slot sub = " + i + " " + citiBike.getEmptySlots());
            Log.d(TAG,"citibike free bikes sub = " + i + " " + citiBike.getFreeBikes());
            Log.d(TAG,"citibike lat = " + i + " " + citiBike.getLatitude());
            Log.d(TAG,"citibike lon = " + i + " " + citiBike.getLongitude());
            Log.d(TAG,"citibike name = " + i + " " + citiBike.getName());
        }
    }

}
