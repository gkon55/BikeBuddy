package com.altimetrik.bikebuddyy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.goebl.david.Webb;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SFLDPGUSER-24 on 6/21/2018.
 */

public class AsyncRequest {

    private static final String TAG = "asyncRequests";
    private Context context;
    //Webb is Lightweight Java HTTP-Client for calling JSON REST-Service
    final Webb webb = Webb.create();

    //Server URL to make the request to and from
    String serverURL = "https://api.citybik.es/v2/networks";

    public AsyncRequest(Context context)
    {
        this.context = context;
      //  webb.setBaseUri(serverURL);
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
                saveToSharedPreference(result.toString());
            }
        }.execute();
        return null;
    }

    private void saveToSharedPreference(String response){
        SharedPreferences pref = this.context.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("response", response);
    }

    public String getSharedPreference(){
        SharedPreferences pref = this.context.getSharedPreferences("MyPref", MODE_PRIVATE);
        String response = pref.getString("response", null);
        return response;
    }

}
