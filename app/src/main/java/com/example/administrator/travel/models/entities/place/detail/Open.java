
package com.example.administrator.travel.models.entities.place.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Open extends RealmObject {

    @SerializedName("day")
    @Expose
    public Integer day;
    @SerializedName("time")
    @Expose
    public String time;

}
