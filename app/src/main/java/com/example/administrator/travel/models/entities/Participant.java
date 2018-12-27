package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

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

    public Participant(String userId, String tourStartId, Boolean shareLocation, MyLatLng latLng, Long joinedTime,Long lastTimeShareLocation) {
        this.userId = userId;
        this.tourStartId=tourStartId;
        this.shareLocation = shareLocation;
        this.latLng = latLng;
        this.joinedTime = joinedTime;
        this.lastTimeShareLocation=lastTimeShareLocation;
    }
}
