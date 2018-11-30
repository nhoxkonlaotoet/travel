package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Administrator on 28/11/2018.
 */
@IgnoreExtraProperties
public class Schedule {
    public String id;
    public String day;
    public String hour;
    public String title;
    public String content;
    public MyLatLng latLng;
    public Schedule(){ }

    public Schedule(String day, String hour, String title, String content, MyLatLng latLng) {
        this.day = day;
        this.hour = hour;
        this.title = title;
        this.content = content;
        this.latLng = latLng;
    }

    @Override
    public String toString()
    {
        return day + " "+ title + " "+ hour + " "+content+ " "+latLng.latitude+ " "+latLng.longitude ;
    }
}
