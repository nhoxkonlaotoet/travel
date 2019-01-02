package com.example.administrator.travel.models.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}