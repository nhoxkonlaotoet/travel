package com.example.administrator.travel.models.entities;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public HashMap<String,Boolean> participants;
    public List<Bitmap> lstPicture =new ArrayList<>();
    public Activity() {
    }

    public Activity(String userId, Boolean focus, Long postTime, String content, MyLatLng location, int numberOfPicture) {
        this.userId = userId;
        this.focus = focus;
        this.postTime = postTime;
        this.content = content;
        this.location = location;
        this.numberOfPicture = numberOfPicture;
    }
}
