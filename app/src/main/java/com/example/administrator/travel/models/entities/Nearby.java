package com.example.administrator.travel.models.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Administrator on 23/12/2018.
 */

public class Nearby{
    public String id;
    public LatLng location;
    public String name;
    public Boolean openNow;
    public Integer priceLevel;
    public Double rating;
    public String[] types;
    public String vicinity;
    public String photo_reference;
    public Bitmap photo;
    public String iconURl;
    public Nearby(){}
    public Nearby(String id, LatLng location, String name, Boolean openNow, Integer priceLevel, Double rating, String[] types, String vicinity, String photo_reference) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.openNow = openNow;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.types = types;
        this.vicinity = vicinity;
        this.photo_reference=photo_reference;
    }


    @Override
    public String toString() {
        return id+"," + location+", "+name+", "+openNow+", "+priceLevel+", "+rating+", "+types[0]+", "+vicinity;
    }

}
