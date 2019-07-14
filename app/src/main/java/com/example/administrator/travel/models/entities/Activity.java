package com.example.administrator.travel.models.entities;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 31/12/2018.
 */
@IgnoreExtraProperties
public class Activity {
    public String id;
    public String userId;
    public Long postTime;
    public String content;
    public MyLatLng myLocation;
    public String placeId;
    public String placeType;
    public String tourStartId;
    public HashMap<String, Boolean> likes;
    public String type;
    public Activity() {
    }

    public Activity(String userId, String tourStartId, String placeId, String placeType, String content,String type, MyLatLng myLocation) {
        this.userId = userId;
        this.tourStartId=tourStartId;
        this.content = content;
        this.myLocation = myLocation;
        this.placeId = placeId;
        this.placeType=placeType;
        this.type=type;
        likes = new HashMap<>();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("tourStartId", tourStartId);
        result.put("placeId", placeId);
        result.put("placeType", placeType);
        result.put("type", type);
        result.put("content", content);
        result.put("myLocation", myLocation);
        result.put("likes",likes);
        result.put("postTime", ServerValue.TIMESTAMP);
        return result;
    }
}
