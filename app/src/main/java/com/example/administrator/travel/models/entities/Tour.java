package com.example.administrator.travel.models.entities;
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
    public Boolean state;
    public Integer adultPrice;
    public Integer childPrice;
    public Integer babyPrice;
    public Tour(){}
    public Tour(String name, String description, Integer days, Integer nights, String vihicle, Boolean state, //1: hoat dong, 0: khoa
                Integer adultPrice, Integer childPrice, Integer babyPrice, String owner)
    {
        this.name=name;
        this.description=description;
        this.days=days;
        this.nights=nights;
        this.vihicle=vihicle;
        this.state=state;
        this.adultPrice=adultPrice;
        this.childPrice=childPrice;
        this.babyPrice=babyPrice;
    }
}
