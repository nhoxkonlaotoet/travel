package com.example.administrator.travel.models.entities;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by Administrator on 15/11/2018.
 */

public class TourBooking {
    public String userId;
    public Long bookingTime;
    public String tourStartDateId;
    public Integer adult;
    public Integer children;
    public Integer baby;
    public Integer money;
    public Boolean accepted;
    public TourBooking(){}
    public TourBooking(String userId, String tourStartDateId,Long bookingTime, Integer adult, Integer children, Integer baby, Integer money)
    {
        this.userId=userId;
        this.tourStartDateId=tourStartDateId;
        this.bookingTime=bookingTime;
        this.adult=adult;
        this.children=children;
        this.baby=baby;
        this.money=money;
        accepted=false;
    }

}
