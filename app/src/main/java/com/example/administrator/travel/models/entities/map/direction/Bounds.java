
package com.example.administrator.travel.models.entities.map.direction;

import com.example.administrator.travel.models.entities.map.general.Northeast;
import com.example.administrator.travel.models.entities.map.general.Southwest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds {

    @SerializedName("northeast")
    @Expose
    public Northeast northeast;
    @SerializedName("southwest")
    @Expose
    public Southwest southwest;

}
