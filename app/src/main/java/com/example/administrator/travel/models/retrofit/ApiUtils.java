package com.example.administrator.travel.models.retrofit;

/**
 * Created by Admin on 5/10/2019.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://maps.googleapis.com/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}