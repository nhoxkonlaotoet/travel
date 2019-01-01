package com.example.administrator.travel.presenters;

import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.views.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 29/12/2018.
 */

public class MapPresenter {
    MapView view;

    List<Nearby> lstNearby;
    public MapPresenter(MapView view){
        this.view=view;

    }
    public void onViewLoad(String action, Bundle bundle){
        view.connectGoogleApiClient();
        try {

                    view.showNearbys(lstNearby);

        }catch (Exception ex){}
    }
    public void onViewConnectedGoogleApiClient(){
        view.startLocationServices();
    }
    public void onViewStop(){
        view.stopGoogleApiClient();
    }
}
