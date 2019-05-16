package com.example.administrator.travel.models.impls;

import android.util.Log;

import com.example.administrator.travel.models.bases.MapInteractor;
import com.example.administrator.travel.models.entities.map.direction.DirectionResponse;
import com.example.administrator.travel.models.entities.map.direction.Route;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.models.retrofit.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 5/15/2019.
 */

public class MapInteractorImpl implements MapInteractor {
    @Override
    public void getDirection(String origin, String destination, String apiKey, final Listener.OnGetDirectionFinishedListener listener) {
        ApiUtils.getSOService().getDirection(origin, destination, true, apiKey).enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                if(response.isSuccessful()) {
                    listener.onGetDirectionSuccess(response.body().routes);
                    Log.e( "direction request: ", response.raw().request().toString() );
                }
            }

            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {
                listener.onGetDirectionFail(new Exception(t.getMessage()));
            }
        });
    }
}
