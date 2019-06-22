package com.example.administrator.travel.models.retrofit;

import com.example.administrator.travel.models.entities.BankCard;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourBookingDetail;
import com.example.administrator.travel.models.entities.TourBookingRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Admin on 6/9/2019.
 */

public interface BankService {
    @POST("validCard/")
    Call<Boolean> checkCard(@Body BankCard card);

    @POST("/test/")
    Call<ResponseBody> test(@Body TourBookingRequest tourBookingRequest);
}
