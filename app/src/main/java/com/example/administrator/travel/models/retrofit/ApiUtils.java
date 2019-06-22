package com.example.administrator.travel.models.retrofit;

/**
 * Created by Admin on 5/10/2019.
 */

public class ApiUtils {
   // public static final String TRAVEL_BASE_URL = "https://travel-76809.firebaseapp.com/api/";
    public static final String MAPS_BASE_URL = "https://maps.googleapis.com/";
   // public static final String BANK_BASE_URL = "https://us-central1-bank-14cd4.cloudfunctions.net/";



//    public static TravelService getTravelService(){
//        return RetrofitClient.getClient(TRAVEL_BASE_URL).create(TravelService.class);
//    }
    public static MapsService getMapsService() {
        return RetrofitClient.getClient(MAPS_BASE_URL).create(MapsService.class);
    }
//    public static BankService getBankService(){
//        return RetrofitClient.getClient(BANK_BASE_URL).create(BankService.class);
//    }
}