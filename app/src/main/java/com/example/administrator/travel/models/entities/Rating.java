package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

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
    public Rating() {
    }

    public Rating(float rating, String ratingPeopleId, Long ratingTime, int numberOfImages, String content) {
        this.rating = rating;
        this.ratingPeopleId = ratingPeopleId;
        this.ratingTime = ratingTime;
        this.numberOfImages = numberOfImages;
        this.content = content;
    }
}
