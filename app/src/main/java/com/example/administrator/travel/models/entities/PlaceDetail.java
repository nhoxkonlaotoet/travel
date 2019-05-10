package com.example.administrator.travel.models.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/9/2019.
 */

public class PlaceDetail {
    public String placeId;
    public String name;
    public double rating;
    public int userRatingsTotal;
    public String formattedAddress;
    public List<PlacePhoto> photos = new ArrayList<>();
    public LatLng location;
    public PlaceDetail() {
    }

}
