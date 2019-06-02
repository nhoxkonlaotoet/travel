package com.example.administrator.travel.models.entities;
import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */
@IgnoreExtraProperties
public class Tour {
    public String id;
    public String name;
    public String description;
    public Integer days;
    public Integer nights;
    public String vihicle;
    public Boolean state;
    public Integer adultPrice;
    public Integer childrenPrice;
    public Integer babyPrice;
    public String owner;
    public String origin;
    public HashMap<String,Boolean> destination;
    public HashMap<String,Double> ratings;
    public Integer numberofImages;
    public Tour(){}
    public Tour(String name, String description, Integer days, Integer nights, String vihicle, Integer numberofRating, Boolean state, //1: hoat dong, 0: khoa
                Integer adultPrice, Integer childrenPrice, Integer babyPrice,String origin, HashMap<String,Boolean> destination, String owner)
    {
        this.name=name;
        this.description=description;
        this.days=days;
        this.nights=nights;
        this.vihicle=vihicle;
        this.state=state;
        this.adultPrice=adultPrice;
        this.childrenPrice=childrenPrice;
        this.babyPrice=babyPrice;
        this.origin=origin;
        this.destination=destination;
        this.owner=owner;
    }
    @Override
    public String toString()
    {
        return name+", "+days+", "+nights+", "+vihicle+", "+state+", "+adultPrice+", "+
                childrenPrice+", "+babyPrice+", "+owner ;
    }
}
