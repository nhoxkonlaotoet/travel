package com.example.administrator.travel.models.entities;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 6/10/2019.
 */

public class TourBookingRequest {
    public String sender_card_number;
    public String receiver;
    public Integer amount;
    public TourBooking tourBooking;
    public ArrayList<TourBookingDetail> tourBookingDetailList;

    public TourBookingRequest(String senderCardNumber, String receiver, Integer amount, TourBooking tourBooking, ArrayList<TourBookingDetail> tourBookingDetailList) {
        this.sender_card_number = senderCardNumber;
        this.receiver = receiver;
        this.amount = amount;
        this.tourBooking = tourBooking;
        this.tourBookingDetailList = tourBookingDetailList;
    }

    public JSONObject toJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            Gson gson = new Gson();
            jsonObject.put("sender_card_number", sender_card_number);
            jsonObject.put("receiver", receiver);
            jsonObject.put("amount", amount);
            jsonObject.put("tourBooking", new JSONObject(gson.toJson(tourBooking)));
            jsonObject.put("tourBookingDetailList", new JSONArray(gson.toJson(tourBookingDetailList)));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
