
package com.example.administrator.travel.models.entities.map.direction;

import java.util.List;

import com.example.administrator.travel.models.entities.map.general.Location;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leg {

    @SerializedName("distance")
    @Expose
    public Distance distance;
    @SerializedName("duration")
    @Expose
    public Duration duration;
    @SerializedName("end_address")
    @Expose
    public String endAddress;
    @SerializedName("end_location")
    @Expose
    public Location endLocation;
    @SerializedName("start_address")
    @Expose
    public String startAddress;
    @SerializedName("start_location")
    @Expose
    public Location startLocation;
    @SerializedName("steps")
    @Expose
    public List<Step> steps = null;
    @SerializedName("traffic_speed_entry")
    @Expose
    public List<Object> trafficSpeedEntry = null;
    @SerializedName("via_waypoint")
    @Expose
    public List<Object> viaWaypoint = null;

}
