package com.example.administrator.travel.models.retrofit;

import com.example.administrator.travel.models.entities.TourBookingRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Admin on 6/12/2019.
 */

public interface TravelService {
    @POST("booking")
    Call<Boolean> bookTour(@Body TourBookingRequest tourBookingRequest);
}
