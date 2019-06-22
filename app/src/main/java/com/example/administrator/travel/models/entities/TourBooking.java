package com.example.administrator.travel.models.entities;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by Administrator on 15/11/2018.
 */

public class TourBooking {
    public String bookingPersonId;
    public Long bookingTime;
    public String tourStartDateId;
    public Integer adult;
    public Integer children;
    public Integer baby;
    public Integer money;
    public TourBooking(){}
    public TourBooking(String bookingPersonId, String tourStartDateId, Integer adult, Integer children, Integer baby, Integer money)
    {
        this.bookingPersonId=bookingPersonId;
        this.tourStartDateId=tourStartDateId;
        this.bookingTime=0L;
        this.adult=adult;
        this.children=children;
        this.baby=baby;
        this.money=money;
    }

}
