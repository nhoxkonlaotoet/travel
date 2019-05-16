
package com.example.administrator.travel.models.entities.place.detail;

import com.example.administrator.travel.models.entities.map.general.Geometry;
import com.example.administrator.travel.models.entities.map.general.Photo;
import com.example.administrator.travel.models.entities.map.general.PlusCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlaceDetail extends RealmObject {

    @SerializedName("address_components")
    @Expose
    public RealmList<AddressComponent> addressComponents = null;
    @SerializedName("adr_address")
    @Expose
    public String adrAddress;
    @SerializedName("formatted_address")
    @Expose
    public String formattedAddress;
    @SerializedName("formatted_phone_number")
    @Expose
    public String formattedPhoneNumber;
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("international_phone_number")
    @Expose
    public String internationalPhoneNumber;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("opening_hours")
    @Expose
    public OpeningHours openingHours;
    @SerializedName("photos")
    @Expose
    public RealmList<Photo> photos = null;
    @SerializedName("place_id")
    @Expose
    @PrimaryKey
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
    @SerializedName("reviews")
    @Expose
    public RealmList<Review> reviews = null;
    @SerializedName("scope")
    @Expose
    public String scope;
    @SerializedName("types")
    @Expose
    public RealmList<String> types = null;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("user_ratings_total")
    @Expose
    public Integer userRatingsTotal;
    @SerializedName("utc_offset")
    @Expose
    public Integer utcOffset;
    @SerializedName("vicinity")
    @Expose
    public String vicinity;
    @SerializedName("website")
    @Expose
    public String website;

}
