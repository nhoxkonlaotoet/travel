package com.example.administrator.travel.models.entities;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Administrator on 28/11/2018.
 */
@IgnoreExtraProperties
public class MyLatLng {
    public double latitude;
    public double longitude;
    public MyLatLng()
    {

    }

    public MyLatLng(double latitude, double longitude)
    {
        this.latitude=latitude;
        this.longitude = longitude;

    }
    @Exclude
    public LatLng getLatLng()
    {
        return new LatLng(latitude,longitude);
    }
}