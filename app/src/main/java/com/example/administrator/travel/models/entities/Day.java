package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Administrator on 28/11/2018.
 */
@IgnoreExtraProperties
public class Day {
    public String id;
    public Integer day;
    public String title;

    public Day() {
    }

    public Day(Integer day, String title) {
        this.day = day;
        this.title = title;
    }

    @Override
    public String toString()
    {
        return day + " "+ title;
    }
}
