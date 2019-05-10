package com.example.administrator.travel.presenters.impls;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.travel.OnGetPeopleLocationFinishedListener;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.DirectionFinder;
import com.example.administrator.travel.models.MapInteractor;
import com.example.administrator.travel.models.OnFindDirectionFinishListener;
import com.example.administrator.travel.models.OnGetScheduleFinishedListener;
import com.example.administrator.travel.models.PlaceDetailTask;
import com.example.administrator.travel.models.TourDetailInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.ScheduleInteractor;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.PlaceDetail;
import com.example.administrator.travel.models.entities.PlacePhoto;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.ScheduleInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.MapPresenter;
import com.example.administrator.travel.views.MapView;
import com.example.administrator.travel.views.activities.MapsActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

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

public class MapPresenterImpl implements MapPresenter, OnFindDirectionFinishListener,
        Listener.OnGetSchedulesFinishedListener, Listener.OnGetPeopleLocationFinishedListener,
        Listener.OnGetTourImageFinishedListener, Listener.OnGetPlaceDetailFinishedListener {
    private MapView view;
    private TourDetailInteractor tourDetailInteractor;
    private ScheduleInteractor scheduleInteractor;
    private TourInteractor tourInteractor;
    private ParticipantInteractor participantInteractor;

    private LatLng destination;
    private String action, scheduleId;
    private List<Schedule> scheduleList;
    private List<Route> routeList;
    private List<Participant> participantList;
    private PlaceDetail placeDetail;
    private Place autoCompleteResult;
    private int currentMarkerTag;

    private String tourStartId;
    private boolean isMyTour;
    private int pos = 0;
    private String tourId;

    public MapPresenterImpl(MapView view) {
        this.view = view;
        tourDetailInteractor = new TourDetailInteractor();
        scheduleInteractor = new ScheduleInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        tourInteractor = new TourInteractorImpl();
        currentMarkerTag = -1;
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        action = bundle.getString("action");
        tourId = bundle.getString("tourId");
        switch (action) {
            case "nearby":
                String des = bundle.getString("destination");
                String[] arr = des.split(",");
                destination = new LatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                view.showDialog();
                break;
            case "schedule":
                String dayId = bundle.getString("dayId");
                scheduleId = bundle.getString("scheduleId");
                isMyTour = bundle.getBoolean("mytour");
                scheduleInteractor.getSchedules(tourId, dayId, this);
                break;
            case "activity":
                tourStartId = bundle.getString("tourStartId");
                participantInteractor.setStreamPeopleLocationChange(tourStartId, this);
                Log.e("people location: ", "_______________________________________________");
                break;
            case "choose":
                break;
            default:
                break;
        }

        try {

        } catch (Exception ex) {
        }


    }

    //    public void onViewLocationChanged(Location location){
//        myLocation=location;
//        if(action.equals("nearby"))
//        try {
//            new DirectionFinder(this, myLocation.getLatitude()+","+myLocation.getLongitude(),
//                    destination.latitude+","+destination.longitude).execute();
//            view.moveCamera(new LatLng(location.getLatitude(),location.getLongitude()));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        if(action.equals("activity"))
//            view.moveCamera(new LatLng(location.getLatitude(),location.getLongitude()));
//    }
    @Override
    public void onAutoCompleteSelected(Place place) {
        autoCompleteResult = autoCompleteResult;
        view.moveCamera(place.getLatLng());
    }

    @Override
    public void onMapClicked(LatLng location) {

    }

    @Override
    public void onMarkerClicked(Marker marker) {
        if (marker.getTag() != null) {
            int markerTag = Integer.parseInt(marker.getTag().toString());
            //abort case: click 1 marker 2 time
            if (markerTag != currentMarkerTag) {
                currentMarkerTag=markerTag;
                Schedule schedule = scheduleList.get(Integer.parseInt(marker.getTag().toString()));
                PlaceDetailTask placeDetailTask = new PlaceDetailTask(this);
                String url = placeDetailTask.getUrl(schedule.placeId, new String[]{"photo"},
                        view.getContext().getResources().getString(R.string.google_api_key));
                placeDetailTask.execute(url);
                view.clearNavigationHeaderPhoto();
            }
        }
        view.openDrawerLayout();
    }

    @Override
    public void onMapRefreshed() {
        int green = view.getContext().getResources().getColor(R.color.colorGreen);
        if (routeList != null)
            for (Route route : routeList) {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).color(green).width(10);
                for (int i = 0; i < route.points.size(); i++) {
                    polylineOptions.add(route.points.get(i));
                }
                view.addPolyline(polylineOptions);
            }
        if (autoCompleteResult != null)
            view.addMarker(new MarkerOptions().position(autoCompleteResult.getLatLng())
                    .title(autoCompleteResult.getAddress().toString()));
        if (destination != null)
            view.addMarker(new MarkerOptions()
                    .position(destination));
        if (scheduleList != null) {
            int i = 0;
            for (Schedule schedule : scheduleList) {
                view.addMarker(new MarkerOptions()
                        .position(schedule.latLng.getLatLng())
                        .title(schedule.hour)
                        .snippet(schedule.content.length()>20?schedule.content.substring(0,20):schedule.content+"..."),i++);
            }
        }
        if (participantList != null)
            for (Participant participant : participantList) {
                view.addMarker(new MarkerOptions()
                        .position(participant.location.getLatLng())
                        .title(participant.userId));
            }
    }

    @Override
    public void onImageViewNavHeaderClicked(Bitmap photo) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        View mView = ((Activity) view.getContext()).getLayoutInflater().inflate(R.layout.dialog_zoom, null);
        PhotoView photoView = mView.findViewById(R.id.imageView);
        photoView.setImageBitmap(photo);
        mBuilder.setView(mView);
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {
        view.closeDialog();
    }

    @Override
    public void onGetSchedulesSuccess(List<Schedule> scheduleList) {

        Date date = new Date();
        int minTime = 1440; // 24 gio
        int currentHour = date.getHours();
        int currentMinute = date.getMinutes();
        int pos = -1;
        int clickPos = 0;
        for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleList.get(i).id.equals(scheduleId)) {
                clickPos = i;
                this.pos = i;
            }
            String arr[] = scheduleList.get(i).hour.split(":");
            if (!arr[0].equals("") && !arr[1].equals("")) {
                int hour = Integer.parseInt(arr[0]);
                int minute = Integer.parseInt(arr[1]);

                if ((Math.abs((currentHour - hour) * 60) + Math.abs(currentMinute - minute)) < minTime) {
                    minTime = Math.abs((currentHour - hour) * 60) + Math.abs(currentMinute - minute);
                    pos = i;
                }
            }
        }
        this.scheduleList = scheduleList;
        view.mapRefesh();
        view.moveCamera(scheduleList.get(clickPos).latLng.getLatLng());
    }

    @Override
    public void onGetSchedulesFail(Exception ex) {

    }

    @Override
    public void onGetPeopleLocationSuccess(List<Participant> participantList) {
        this.participantList = participantList;
    }

    @Override
    public void onGetPeopleLocationFailure(Exception ex) {

    }

    @Override
    public void onGetTourImageSuccess(int pos, Bitmap image) {
        view.setNavigationHeaderPhoto(pos, image);
    }

    @Override
    public void onGetTourImageFail(Exception ex) {

    }

    @Override
    public void onGetPlaceDetailSuccess(PlaceDetail placeDetail) {
       this.placeDetail=placeDetail;
        if (placeDetail.photos.size() > 0) {
            StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400")
                    .append("&photoreference=")
                    .append(placeDetail.photos.get(0).photoRef)
                    .append("&key=")
                    .append(view.getContext().getResources().getString(R.string.google_api_key));
            view.setNavigationHeaderPhoto(urlBuilder.toString());
            StringBuilder seeOtherBuilder = new StringBuilder(String.valueOf(placeDetail.photos.size()-1))
                    .append(" ")
                    .append(view.getContext().getResources().getString(R.string.see_other_photos));
            view.setNavigationHeaderTitle(seeOtherBuilder.toString());
        }
    }

    @Override
    public void onGetPlaceDetailFail(Exception ex) {

    }
}
