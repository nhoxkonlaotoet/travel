package com.example.administrator.travel.models.entities;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
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
    public Float rating;
    public Integer numberofRating;
    public Boolean state;
    public Integer adultPrice;
    public Integer childrenPrice;
    public Integer babyPrice;
    public String owner;
    public Tour(){}
    public Tour(String name, String description, Integer days, Integer nights, String vihicle,
                Float rating, Integer numberofRating, Boolean state, //1: hoat dong, 0: khoa
                Integer adultPrice, Integer childrenPrice, Integer babyPrice, String owner)
    {
        this.name=name;
        this.description=description;
        this.days=days;
        this.nights=nights;
        this.vihicle=vihicle;
        this.rating = rating;
        this.numberofRating=numberofRating;
        this.state=state;
        this.adultPrice=adultPrice;
        this.childrenPrice=childrenPrice;
        this.babyPrice=babyPrice;
        this.owner=owner;
    }
    @Override
    public String toString()
    {
        return name+", "+days+", "+nights+", "+vihicle+", "+rating+", "+state+", "+rating+", "+numberofRating+", "+adultPrice+", "+
                childrenPrice+", "+babyPrice+", "+owner;
    }
}
