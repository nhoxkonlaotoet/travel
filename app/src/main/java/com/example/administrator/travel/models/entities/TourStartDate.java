package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 30/10/2018.
 */
@IgnoreExtraProperties
public class TourStartDate implements Serializable {
    public String id;
    public Long startDate;
    public Integer maxPeople;
    public Integer peopleBooking;
    public Integer participant;
    public Integer adultPrice;
    public Integer childrenPrice;
    public Integer babyPrice;
    public Boolean available;
    public  TourStartDate(){}
    public TourStartDate(Long startDate, Integer adultPrice, Integer childrenPrice, Integer babyPrice,
                         Integer maxPeople,Integer peopleBooking, Integer participant, Boolean available)
    {
        this.startDate=startDate;
        this.adultPrice=adultPrice;
        this.childrenPrice=childrenPrice;
        this.babyPrice=babyPrice;
        this.maxPeople=maxPeople;
        this.peopleBooking=peopleBooking;
        this.participant=participant;
        this.available=available;
    }
    @Override
    public String toString()
    {
        return startDate.toString()+", "+adultPrice+", "+childrenPrice+", "+babyPrice+", "+ peopleBooking;
    }
}
