package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Administrator on 24/12/2018.
 */
@IgnoreExtraProperties
public class City {
    public String id;
    public String name;

    public City() {
    }

    public City( String name) {
        this.name = name;
    }
}
