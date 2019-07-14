
package com.example.administrator.travel.models.entities;

import com.example.administrator.travel.models.entities.map.general.Geometry;
import com.example.administrator.travel.models.entities.map.general.Photo;
import com.example.administrator.travel.models.entities.map.general.PlusCode;
import com.example.administrator.travel.models.entities.place.detail.AddressComponent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlaceDetail extends RealmObject {

    public RealmList<AddressComponent> addressComponents = null;
    public String formattedAddress;
    public String formattedPhoneNumber;
    public Double latitude;
    public Double longitude;
    public String icon;
    public String id;
    public String name;
    public Boolean openNow;
    public RealmList<String> photos = new RealmList();
    @PrimaryKey
    public String placeId;
    public Double rating;
    public String reference;
    public RealmList<String> types = new RealmList();
    public String url;
    public Integer userRatingsTotal;
    public String vicinity;
    public String website;

}
