package com.example.administrator.travel.views;

/**
 * Created by Administrator on 18/11/2018.
 */

public interface BookTourView {
    public static final String TYPE_ADULT       = "Adult";
    public static final String TYPE_CHILDREN    = "Children";
    public static final String TYPE_BABY        = "Baby";

    void changeNumberOfPeople(String type, Integer number);
     void sendBookingTour();
     void notifyBookingSuccess();
     void notifyBookingFailure(Exception ex);
     void close();
     void receivedCurrentTime(Long time);
     void receivedCurrentTime(Exception ex);

}

