package com.example.administrator.travel.models.impls;

import android.util.Log;

import com.example.administrator.travel.models.bases.PlaceInteractor;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.entities.place.detail.PlaceDetailResponse;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.example.administrator.travel.models.entities.place.nearby.NearbyResponse;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.models.retrofit.ApiUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 4/25/2019.
 */

public class PlaceInteractorImpl implements PlaceInteractor {
    @Override
    public void getNearby(String type, LatLng location, String apiKey, final Listener.OnGetNearbyFinishedListener listener) {
        ApiUtils.getSOService().getNearby(location.latitude+","+location.longitude,type,"distance",apiKey)
        .enqueue(new Callback<NearbyResponse>() {
            @Override
            public void onResponse(Call<NearbyResponse> call, Response<NearbyResponse> response) {
                Log.e("nearby: ", response.raw().request() +"       ____");
                if(response.isSuccessful())
                    listener.onGetNearbySuccess(response.body().nearbys,response.body().nextPageToken);
            }

            @Override
            public void onFailure(Call<NearbyResponse> call, Throwable t) {
                listener.onGetNearbyFail(new Exception(t.getMessage()));
            }
        });

    }

    @Override
    public void getNearby(String type, LatLng location, String pageToken, String apiKey, final Listener.OnGetNearbyFinishedListener listener) {
        ApiUtils.getSOService().getNearby(location.latitude+","+location.longitude,type,
                "distance",true,true,pageToken,apiKey)
                .enqueue(new Callback<NearbyResponse>() {
                    @Override
                    public void onResponse(Call<NearbyResponse> call, Response<NearbyResponse> response) {
                        if(response.isSuccessful())
                            listener.onGetNearbySuccess(response.body().nearbys,response.body().nextPageToken);
                    }

                    @Override
                    public void onFailure(Call<NearbyResponse> call, Throwable t) {
                        listener.onGetNearbyFail(new Exception(t.getMessage()));
                    }
                });

    }

    @Override
    public void getPlaceType(final Listener.OnGetPlaceTypeFinishedListener listener) {
        final List<NearbyType> lstPlacetype = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference placetypeRef = database.getReference("placetypes");
        placetypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsPlacetype : dataSnapshot.getChildren())
                {
                    NearbyType placetype = dsPlacetype.getValue(NearbyType.class);
                    placetype.id = dsPlacetype.getKey();
                    lstPlacetype.add(placetype);
                    listener.onGetPlaceTypeSuccess(lstPlacetype);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetPlaceTypeFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getPlaceDetail(String placeId, String apiKey, final Listener.OnGetPlaceDetailFinishedListener listener) {
        final long start = System.currentTimeMillis();
        ApiUtils.getSOService().getPlaceDetail(placeId,apiKey)
                .enqueue(new Callback<PlaceDetailResponse>() {
                    @Override
                    public void onResponse(Call<PlaceDetailResponse> call, Response<PlaceDetailResponse> response) {
                        Log.e( "onResponse: ", "Time :" + (System.currentTimeMillis()-start));
                        if(response.isSuccessful())
                            listener.onGetPlaceDetailSuccess(response.body().placeDetail);
                    }

                    @Override
                    public void onFailure(Call<PlaceDetailResponse> call, Throwable t) {
                        listener.onGetPlaceDetailFail(new Exception(t.getMessage()));
                    }
                });
    }
}
