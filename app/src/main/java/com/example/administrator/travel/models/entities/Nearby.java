package com.example.administrator.travel.models.entities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Administrator on 23/12/2018.
 */

public class Nearby {
    public String id;
    public LatLng location;
    public String name;
    public Boolean openNow;
    public Integer priceLevel;
    public Double rating;
    public String[] types;
    public String vicinity;
    public Nearby(){}
    public Nearby(String id, LatLng location, String name, Boolean openNow, Integer priceLevel, Double rating, String[] types, String vicinity) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.openNow = openNow;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.types = types;
        this.vicinity = vicinity;
    }

    @Override
    public String toString() {
        return id+"," + location+", "+name+", "+openNow+", "+priceLevel+", "+rating+", "+types[0]+", "+vicinity;
    }
}
