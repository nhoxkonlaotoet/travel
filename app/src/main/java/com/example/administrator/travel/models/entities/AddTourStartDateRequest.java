package com.example.administrator.travel.models.entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class AddTourStartDateRequest {
    public String id;
    public String userId;
    public Long startDate;
    public Long timeAdd;
    public Integer numberOfPeople;

    public AddTourStartDateRequest() {
    }

    public AddTourStartDateRequest(String userId, Long startDate, Integer numberOfPeople) {
        this.userId = userId;
        this.startDate = startDate;
        this.numberOfPeople = numberOfPeople;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("startDate", startDate);
        result.put("numberOfPeople", numberOfPeople);
        result.put("timeAdd", ServerValue.TIMESTAMP);
        return result;
    }
}
