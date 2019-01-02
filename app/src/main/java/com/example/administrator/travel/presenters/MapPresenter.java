package com.example.administrator.travel.presenters;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.OnGetPeopleLocationFinishedListener;
import com.example.administrator.travel.models.DirectionFinder;
import com.example.administrator.travel.models.MapInteractor;
import com.example.administrator.travel.models.OnFindDirectionFinishListener;
import com.example.administrator.travel.models.OnGetScheduleFinishedListener;
import com.example.administrator.travel.models.TourDetailInteractor;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.views.MapView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 29/12/2018.
 */

public class MapPresenter  implements OnFindDirectionFinishListener,OnGetScheduleFinishedListener,OnGetPeopleLocationFinishedListener{
    MapView view;
    TourDetailInteractor tourDetailInteractor;
    MapInteractor mapInteractor;
    Location myLocation;
    LatLng  destination;
    String action, scheduleIdNow, scheduleId;
    List<Schedule> lstSchedule;
    String tourStartId;
    boolean isMyTour;
    int pos=0;
    public MapPresenter(MapView view){
        this.view=view;
        tourDetailInteractor=new TourDetailInteractor();
        mapInteractor = new MapInteractor();
    }

    public void onViewLoad(Bundle bundle){
        view.connectGoogleApiClient();
        action = bundle.getString("action");
        switch (action){
            case "nearby":
                String des = bundle.getString("destination");
                String[] arr = des.split(",");

                destination =new LatLng(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]));
                view.showDialog();
                view.addDestination(destination);
                view.hideControlBtns();
                view.hideBtnChooseMyLocation();
                break;
            case "schedule":
                String tourId= bundle.getString("tourId");
                String dayId = bundle.getString("dayId");
                scheduleId = bundle.getString("scheduleId");
                isMyTour = bundle.getBoolean("mytour");
                if(!isMyTour)
                    view.hideBtnNow();
                tourDetailInteractor.getSchedule(tourId,dayId,this);
                view.hideBtnChooseMyLocation();
                break;
            case "activity":
                tourStartId = bundle.getString("tourStartId");
                mapInteractor.getPeopleLocation(tourStartId,this);
                view.hideControlBtns();
                view.hideBtnChooseMyLocation();
                Log.e("people location: ","_______________________________________________" );
                break;
            case "choose":
                    view.hideControlBtns();
                break;
            default: break;
        }

        try {

        }catch (Exception ex){}


    }
    public void onViewLocationChanged(Location location){
        myLocation=location;
        if(action.equals("nearby"))
        try {
            new DirectionFinder(this, myLocation.getLatitude()+","+myLocation.getLongitude(),
                    destination.latitude+","+destination.longitude).execute();
            view.moveCamera(new LatLng(location.getLatitude(),location.getLongitude()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(action.equals("activity"))
            view.moveCamera(new LatLng(location.getLatitude(),location.getLongitude()));
    }
    public void onBtnPreviousClicked(){
        if(pos==0)
            return;
        pos--;
        view.moveCamera(lstSchedule.get(pos).latLng.getLatLng());
    }
    public void onBtnNowClicked(){
        if(!isMyTour) return;
        for(int i=0;i<lstSchedule.size();i++)
            if(lstSchedule.get(i).id.equals(scheduleIdNow)) {
                pos=i;
                view.moveCamera(lstSchedule.get(i).latLng.getLatLng());
            }
    }
    public void onBtnNextClicked(){
        if(pos==lstSchedule.size()-1)
            return;
        pos++;
        view.moveCamera(lstSchedule.get(pos).latLng.getLatLng());
    }
    public  void btnChooseMyLocationClicked(){
        if(myLocation!=null)
            view.closeActivityForResult(new LatLng(myLocation.getLatitude(),myLocation.getLongitude()));
    }
    public void onAutoCompleteSelected(Place place){
        view.addPlaceResult(place);
        view.moveCamera(place.getLatLng());
    }
    public void onMapClick(LatLng location){
        view.addPlaceResult(null);
        if(action.equals("choose"))
        {
            view.addMyClickLocation(location);
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
        Log.e( "onGetScheduleSuccess: ", lstSchedule.size()+"");
        Date date = new Date();
        int minTime= 1440; // 24 gio
        int currentHour =date.getHours();
        int currentMinute = date.getMinutes();
        int pos=-1;
        int clickPos=0;
        for(int i=0;i<lstSchedule.size();i++)
        {
            if (lstSchedule.get(i).id.equals(scheduleId)) {
                clickPos = i;
                this.pos=i;
            }
            String arr[] = lstSchedule.get(i).hour.split(":");
            if(!arr[0].equals("") && !arr[1].equals("")) {
                int hour = Integer.parseInt(arr[0]);
                int minute = Integer.parseInt(arr[1]);

                if ((Math.abs((currentHour - hour) * 60) + Math.abs(currentMinute - minute)) < minTime) {
                    minTime = Math.abs((currentHour - hour) * 60) + Math.abs(currentMinute - minute);
                    pos = i;
                }
            }
        }
        scheduleIdNow = lstSchedule.get(pos).id;

        this.lstSchedule=lstSchedule;
        view.addSchedule(lstSchedule);
        view.moveCamera(lstSchedule.get(clickPos).latLng.getLatLng());
    }

    @Override
    public void onGetScheduleFailure(Exception ex) {

    }

    @Override
    public void onGetPeopleLocationSuccess(List<Participant> lstParticipant) {
        view.addPeopleLocations(lstParticipant);
    }

    @Override
    public void onGetPeopleLocationFailure(Exception ex) {

    }
}
