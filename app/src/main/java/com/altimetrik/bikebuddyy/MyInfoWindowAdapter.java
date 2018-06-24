package com.altimetrik.bikebuddyy;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by girish on 6/23/2018.
 */

class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    LayoutInflater inflater;
   // private String title, contentInfo;
    private HashMap<Marker, String> stopsMarkersInfo;
    public MyInfoWindowAdapter(HashMap<Marker, String> hashMap, Context context) {
         inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        myContentsView = inflater.inflate(R.layout.custom_info_contents, null);
        this.stopsMarkersInfo = hashMap;
        //this.contentInfo = contentInfo;
    }

    @Override
    public View getInfoContents(Marker marker) {
        String detailString = stopsMarkersInfo.get(marker);
        if(!TextUtils.isEmpty(detailString)){
            TextView tvTitle = ((TextView) myContentsView
                    .findViewById(R.id.title));
            tvTitle.setText(detailString);
        }
        return myContentsView;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }

}