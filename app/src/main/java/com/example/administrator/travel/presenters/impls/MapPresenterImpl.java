package com.example.administrator.travel.presenters.impls;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.travel.LocationObservable;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.MapInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.PlaceInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.ScheduleInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.map.direction.Polyline;
import com.example.administrator.travel.models.entities.map.direction.Route;
import com.example.administrator.travel.models.entities.place.detail.PlaceDetail;
import com.example.administrator.travel.models.impls.MapInteractorImpl;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.PlaceInteractorImpl;
import com.example.administrator.travel.models.impls.ScheduleInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.MapPresenter;
import com.example.administrator.travel.views.bases.MapView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 29/12/2018.
 */

public class MapPresenterImpl implements MapPresenter,
        Listener.OnGetSchedulesFinishedListener, Listener.OnGetPeopleLocationFinishedListener,
        Listener.OnGetPlaceDetailFinishedListener, Listener.OnGetDirectionFinishedListener {
    private MapView view;
    private ScheduleInteractor scheduleInteractor;
    private ParticipantInteractor participantInteractor;
    private PlaceInteractor placeInteractor;
    private MapInteractor mapInteractor;

    private LatLng origin, destination;
    private String openFrom, scheduleId;
    private List<Schedule> scheduleList;
    private List<Route> routeList;
    private List<Participant> participantList;
    private PlaceDetail placeDetail;
    private Place autoCompleteResult;
    private String currentPlaceId;

    private String tourStartId;
    private boolean isMyTour;
    private int pos = 0;
    private String tourId;
    private Location myLocation;

    public MapPresenterImpl(MapView view) {
        this.view = view;
        scheduleInteractor = new ScheduleInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        placeInteractor = new PlaceInteractorImpl();
        mapInteractor = new MapInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        openFrom = bundle.getString("openFrom");
        tourId = bundle.getString("tourId");
        switch (openFrom) {
            case "nearby":
                String[] orgSplit = bundle.getString("origin").split(",");
                origin = new LatLng(Double.parseDouble(orgSplit[0]), Double.parseDouble(orgSplit[1]));
                String[] desSplit = bundle.getString("destination").split(",");
                destination = new LatLng(Double.parseDouble(desSplit[0]), Double.parseDouble(desSplit[1]));
                mapInteractor.getDirection(bundle.getString("origin"), bundle.getString("destination"),
                        view.getContext().getResources().getString(R.string.google_maps_key), this);
                view.showDialog();
                break;
            case "contact":
                String[] companySplit = bundle.getString("destination").split(",");
                LatLng companyLocation= new LatLng(Double.parseDouble(companySplit[0]), Double.parseDouble(companySplit[1]));
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
                break;
            case "choose":
                break;
            default:
                break;
        }


    }
    @Override
    public void onLocationChanged(Location location){
       // Toast.makeText(view.getContext(), location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        myLocation=location;
    }
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
            //marker.tag = position of schedule list;
            int markerTag = Integer.parseInt(marker.getTag().toString());
            Schedule schedule = scheduleList.get(markerTag);
            //abort click 1 marker 2 time
            if (currentPlaceId == null || !currentPlaceId.equals(schedule.placeId)) {
                placeInteractor.getPlaceDetail(schedule.placeId, view.getContext().getResources().getString(R.string.google_maps_key), this);
                view.clearNavigationHeaderPhoto();
                currentPlaceId = schedule.placeId;
            }
        }
        view.openDrawerLayout();
    }

    @Override
    public void onPOIClicked(PointOfInterest poi) {
        //abort click 1 marker 2 time
        if (currentPlaceId == null || !currentPlaceId.equals(poi.placeId)) {
            placeInteractor.getPlaceDetail(poi.placeId, view.getContext().getResources().getString(R.string.google_maps_key), this);
            view.clearNavigationHeaderPhoto();
            currentPlaceId = poi.placeId;
        }
        view.openDrawerLayout();
    }

    @Override
    public void onMapRefreshed() {
        int green = view.getContext().getResources().getColor(R.color.colorKiwi);
        if (routeList != null) {
            for (Route route : routeList) {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).color(green).width(10);
                for (int i = 0; i < route.overviewPolyline.points().size(); i++) {
                    Log.e("onMapRefreshed: ", route.overviewPolyline.points().get(i).latitude
                            + ", " + route.overviewPolyline.points().get(i).longitude);
                    polylineOptions.add(route.overviewPolyline.points().get(i));
                }
                view.addPolyline(polylineOptions);
            }
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
                        .snippet(schedule.content.length() > 20 ? schedule.content.substring(0, 20) : schedule.content + "..."), i++);
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
    public void onGetPlaceDetailSuccess(PlaceDetail placeDetail) {
        this.placeDetail = placeDetail;
        if (placeDetail.photos != null && placeDetail.photos.size() > 0) {
            StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400")
                    .append("&photoreference=")
                    .append(placeDetail.photos.get(0).photoReference)
                    .append("&key=")
                    .append(view.getContext().getResources().getString(R.string.google_maps_key));
            view.setNavigationHeaderPhoto(urlBuilder.toString());
            StringBuilder seeOtherBuilder = new StringBuilder(String.valueOf(placeDetail.photos.size() - 1))
                    .append(" ")
                    .append(view.getContext().getResources().getString(R.string.see_other_photos));
            view.setNavigationHeaderTitle(seeOtherBuilder.toString());
        } else {
        }
    }

    @Override
    public void onGetPlaceDetailFail(Exception ex) {

    }

    @Override
    public void onGetDirectionSuccess(List<Route> routeList) {
       
        this.routeList = routeList;
        view.mapRefesh();
        view.closeDialog();
        view.moveCamera(destination);
    }

    @Override
    public void onGetDirectionFail(Exception ex) {
        view.closeDialog();
    }
}
