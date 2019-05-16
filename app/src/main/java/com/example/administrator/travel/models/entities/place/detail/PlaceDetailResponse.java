
package com.example.administrator.travel.models.entities.place.detail;

import java.util.List;

import com.example.administrator.travel.models.entities.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceDetailResponse {

    @SerializedName("html_attributions")
    @Expose
    public List<Object> htmlAttributions = null;
    @SerializedName("result")
    @Expose
    public PlaceDetail placeDetail;
    @SerializedName("status")
    @Expose
    public String status;


}
