package com.example.administrator.travel.models.retrofit;

/**
 * Created by Admin on 5/10/2019.
 */

import com.example.administrator.travel.models.entities.map.direction.DirectionResponse;
import com.example.administrator.travel.models.entities.place.detail.PlaceDetailResponse;
import com.example.administrator.travel.models.entities.place.nearby.NearbyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    @GET("maps/api/directions/json?")
    Call<DirectionResponse> getDirection(@Query("origin") String origin,
                                         @Query("destination") String destination,
                                         @Query("alternative") Boolean alternative,
                                         @Query("key") String apiKey);
    @GET("maps/api/place/details/json?")
    Call<PlaceDetailResponse> getPlaceDetail(@Query("placeid") String placeId,
                                             @Query("key") String apiKey);

    @GET("maps/api/place/nearbysearch/json?")
    Call<NearbyResponse> getNearby(@Query("location") String location,
                                   @Query("type") String type,
                                   @Query("rankby") String rankby,
                                   @Query("key") String apiKey);
    @GET("maps/api/place/nearbysearch/json?")
    Call<NearbyResponse> getNearby(@Query("location") String location,
                                   @Query("type") String type,
                                   @Query("rankby") String rankby,
                                   @Query("hasNextPage") Boolean hasNextPage,
                                   @Query("nextPage()") Boolean nextPage,
                                   @Query("pagetoken") String pagetoken,
                                   @Query("key") String apiKey);

}
