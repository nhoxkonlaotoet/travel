
package com.example.administrator.travel.models.entities.place.nearby;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.map.general.Geometry;
import com.example.administrator.travel.models.entities.map.general.Photo;
import com.example.administrator.travel.models.entities.map.general.PlusCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.annotations.Ignore;

public class Nearby{

    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("photos")
    @Expose
    public RealmList<Photo> photos = null;
    @SerializedName("place_id")
    @Expose
    public String placeId;
    @SerializedName("plus_code")
    @Expose
    public PlusCode plusCode;
    @SerializedName("rating")
    @Expose
    public Double rating;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("scope")
    @Expose
    public String scope;
    @SerializedName("types")
    @Expose
    public RealmList<String> types = null;
    @SerializedName("user_ratings_total")
    @Expose
    public Integer userRatingsTotal;
    @SerializedName("vicinity")
    @Expose
    public String vicinity;
    @SerializedName("opening_hours")
    @Expose
    public OpeningHours openingHours;
    @SerializedName("price_level")
    @Expose
    public Integer priceLevel;

    @Ignore
    public Bitmap photo;
    @Override
    public String toString() {
        return openingHours==null?"null":openingHours.openNow + " ." + priceLevel+".";
    }
}
