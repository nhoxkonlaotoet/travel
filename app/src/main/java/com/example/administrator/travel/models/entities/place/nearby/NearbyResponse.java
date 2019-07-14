
package com.example.administrator.travel.models.entities.place.nearby;

import java.util.List;

import com.example.administrator.travel.models.entities.Nearby;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

public class NearbyResponse {

    @SerializedName("html_attributions")
    @Expose
    public RealmList<Object> htmlAttributions = null;
    @SerializedName("next_page_token")
    @Expose
    public String nextPageToken;
    @SerializedName("results")
    @Expose
    public List<Nearby> nearbys= null;
    @SerializedName("status")
    @Expose
    public String status;

}
