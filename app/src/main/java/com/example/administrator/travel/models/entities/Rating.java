package com.example.administrator.travel.models.entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 25/12/2018.
 */
@IgnoreExtraProperties
public class Rating {
    public String id;
    public float rating;
    public String ratingPeopleId;
    public Long ratingTime;
    public int numberOfImages;
    public String content;
    public HashMap<String,Boolean> likes;
    public Rating() {
    }

    public Rating(float rating, String ratingPeopleId, int numberOfImages, String content) {
        this.rating = rating;
        this.ratingPeopleId = ratingPeopleId;
        this.numberOfImages = numberOfImages;
        this.content = content;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ratingPeopleId", ratingPeopleId);
        result.put("rating", rating);
        result.put("numberOfImages", numberOfImages);
        result.put("content", content);
        result.put("ratingTime", ServerValue.TIMESTAMP);
        result.put("likes",likes);
        return result;
    }
}
