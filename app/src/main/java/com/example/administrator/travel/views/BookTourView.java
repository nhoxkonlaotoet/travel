package com.example.administrator.travel.views;

/**
 * Created by Administrator on 18/11/2018.
 */

public interface BookTourView {
    public static final String TYPE_ADULT       = "Adult";
    public static final String TYPE_CHILDREN    = "Children";
    public static final String TYPE_BABY        = "Baby";

    public void changeNumberOfPeople(String type, Integer number);
    public void sendBookingTour();
    public void notifyBookingSuccess();
    public void notifyBookingFailure();
    public void close();
}

