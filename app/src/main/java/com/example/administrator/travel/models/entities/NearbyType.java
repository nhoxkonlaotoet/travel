package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Administrator on 23/12/2018.
 */
@IgnoreExtraProperties
public class NearbyType {
    public String id;
    public String value;
    public String display;

    public NearbyType() {}

    public NearbyType(String value, String display) {
        this.value = value;
        this.display = display;
    }
}
