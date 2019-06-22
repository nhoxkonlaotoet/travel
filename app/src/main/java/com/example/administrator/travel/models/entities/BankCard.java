package com.example.administrator.travel.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 6/9/2019.
 */

public class BankCard {
    @SerializedName("card_number")
    @Expose
    public String cardNumber;
    @SerializedName("cvc")
    @Expose
    public Integer cvc;
    @SerializedName("expiration_month")
    @Expose
    public Integer expirationMonth;
    @SerializedName("expiration_year")
    @Expose
    public Integer expirationYear;
    @SerializedName("holder_name")
    @Expose
    public String holderName;

    public BankCard(String cardNumber, String holderName, Integer expirationMonth, Integer expirationYear, Integer cvc) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.holderName = holderName;
    }

    public JSONObject toJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("card_number", cardNumber);
            jsonObject.put("cvc", cvc);
            jsonObject.put("expiration_month", expirationMonth);
            jsonObject.put("expiration_year", expirationYear);
            jsonObject.put("holder_name", holderName);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}