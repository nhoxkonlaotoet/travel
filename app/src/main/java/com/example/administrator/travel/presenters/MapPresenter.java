package com.example.administrator.travel.presenters;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.models.DirectionFinder;
import com.example.administrator.travel.models.OnFindDirectionFinishListener;
import com.example.administrator.travel.models.OnGetScheduleFinishedListener;
import com.example.administrator.travel.models.TourDetailInteractor;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.views.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 29/12/2018.
 */

public class MapPresenter  implements OnFindDirectionFinishListener,OnGetScheduleFinishedListener{
    MapView view;
    TourDetailInteractor tourDetailInteractor;
    Location myLocation;
    List<Nearby> lstNearby;
    LatLng origin, destination;

    public MapPresenter(MapView view){
        this.view=view;
        tourDetailInteractor=new TourDetailInteractor();
    }

    public void onViewLoad(Bundle bundle){
        view.connectGoogleApiClient();
        String action = bundle.getString("action");
        switch (action){
            case "nearby":
                String des = bundle.getString("destination");
                String[] arr = des.split(",");
                destination =new LatLng(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]));
                view.showDialog();
                view.addDestination(destination);
                break;
            case "schedule":
                String tourId= bundle.getString("tourId");
                String dayId = bundle.getString("dayId");

                tourDetailInteractor.getSchedule(tourId,dayId,this);
                break;
            case "activity":

                break;
            case "choose":

                break;
            default: break;
        }

        try {

        }catch (Exception ex){}


    }
    public void onViewLocationChanged(Location location){
        myLocation=location;
        try {
            new DirectionFinder(this, myLocation.getLatitude()+","+myLocation.getLongitude(),
                    destination.latitude+","+destination.longitude).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void onViewConnectedGoogleApiClient(){
        view.startLocationServices();
    }
    public void onViewStop(){
        view.stopGoogleApiClient();
    }

    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {
        Log.e("onDirectSuccess: ",route.size()+"" );
        view.closeDialog();
        view.addDirection(route);
    }

    @Override
    public void onGetScheduleSuccess(List<Schedule> lstSchedule) {
        
    }

    @Override
    public void onGetScheduleFailure(Exception ex) {

    }
}
