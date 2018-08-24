package com.altimetrik.bikebuddyy;


import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by girish on 6/23/2018.
 */

public class NetworkRequest {

   public static String getResponse() throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient()
               .newBuilder()
               .connectTimeout(120, TimeUnit.SECONDS)
               .writeTimeout(120, TimeUnit.SECONDS)
               .readTimeout(120, TimeUnit.SECONDS)
               .build();

       Request request = new Request.Builder()
               .url("https://api.citybik.es/v2/networks/citi-bike-nyc")
               .build();

       Response response = okHttpClient.newCall(request).execute();

       String responseBody = null;
       if (response.body() != null) {
           responseBody = Objects.requireNonNull(response.body()).string();
       }

       return responseBody;
    }

}
