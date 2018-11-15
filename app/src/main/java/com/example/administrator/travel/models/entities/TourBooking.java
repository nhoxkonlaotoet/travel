package com.example.administrator.travel.models.entities;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by Administrator on 15/11/2018.
 */

public class TourBooking {
    public String id;
    public String userId;
    public Integer adult;
    public Integer children;
    public Integer baby;
    public Map<String, String> bookingTime;
    public Integer money;
    public TourBooking(){}
    public TourBooking(String userId, Integer adult, Integer children, Integer baby, Map<String, String> bookingTime, Integer money)
    {
        this.userId=userId;
        this.adult=adult;
        this.children=children;
        this.baby=baby;
        this.bookingTime=bookingTime;
        this.money=money;
    }

}
