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
    public Boolean focus;
    public Long postTime;
    public String content;
    public MyLatLng location;
    public int numberOfPicture;
    public List<Bitmap> lstPicture =new ArrayList<>();
    public Activity() {
    }

    public Activity(String userId, Boolean focus, String content, MyLatLng location, int numberOfPicture) {
        this.userId = userId;
        this.focus = focus;
        this.content = content;
        this.location = location;
        this.numberOfPicture = numberOfPicture;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("focus", focus);
        result.put("content", content);
        result.put("location", location);
        result.put("postTime", ServerValue.TIMESTAMP);
        result.put("numberOfPicture", numberOfPicture);
        return result;
    }
}
