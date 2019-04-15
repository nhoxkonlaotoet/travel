package com.example.administrator.travel.models.entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 18/12/2018.
 */
@IgnoreExtraProperties
public class Participant {
    public String userId;
    public String tourStartId;
    public Boolean shareLocation;
    public MyLatLng latLng;
    public Long joinedTime;
    public Long lastTimeShareLocation;
    public Participant() {
    }

//    public Participant(String userId, String tourStartId, Boolean shareLocation, MyLatLng latLng, Long joinedTime,Long lastTimeShareLocation) {
//        this.userId = userId;
//        this.tourStartId=tourStartId;
//        this.shareLocation = shareLocation;
//        this.latLng = latLng;
//        this.joinedTime = joinedTime;
//        this.lastTimeShareLocation=lastTimeShareLocation;
//    }
    public Participant(String userId, String tourStartId, Boolean shareLocation, MyLatLng latLng) {
        this.userId = userId;
        this.tourStartId=tourStartId;
        this.shareLocation = shareLocation;
        this.latLng = latLng;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("tourStartId", tourStartId);
        result.put("shareLocation", shareLocation);
        result.put("latLng", latLng);
        result.put("joinedTime", ServerValue.TIMESTAMP);
        result.put("lastTimeShareLocation", ServerValue.TIMESTAMP);
        return result;
    }
}
