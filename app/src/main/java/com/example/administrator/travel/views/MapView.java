package com.example.administrator.travel.views;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by Administrator on 29/12/2018.
 */

public interface MapView {
    void moveCamera(LatLng location);

    void mapRefesh();
    void addMarker(MarkerOptions markerOptions);
    void addMarker(MarkerOptions markerOptions, Object tag);
    void addPolyline(PolylineOptions polylineOptions);
    void showDialog();
    void closeDialog();
    void openDrawerLayout();
    void closeDrawerLayout();
    void setNavigationHeaderPhoto(int pos, Bitmap photo);
    void setNavigationHeaderPhoto(String imageUrl);
    void clearNavigationHeaderPhoto();
    void setNavigationHeaderTitle(String title);
    void clearNavigationHeaderTitile();

    void closeActivityForResult(LatLng location);
    Context getContext();

}
