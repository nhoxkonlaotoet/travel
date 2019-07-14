
package com.example.administrator.travel.models.entities;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.map.general.Geometry;
import com.example.administrator.travel.models.entities.map.general.Photo;
import com.example.administrator.travel.models.entities.map.general.PlusCode;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.annotations.Ignore;

public class Nearby {

    public Double latitude;
    public Double longitude;
    public String icon;
    public String id;
    public String name;
    public RealmList<String> photos = new RealmList();
    public String placeId;
    public Double rating;
    public String reference;
    public RealmList<String> types = new RealmList();
    public Integer userRatingsTotal;
    public String vicinity;
    public Boolean openNow;
    public Integer priceLevel;

}
