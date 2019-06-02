package com.example.administrator.travel.presenters.bases;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PointOfInterest;

/**
 * Created by Admin on 5/8/2019.
 */

public interface MapPresenter {
    void onViewCreated(Bundle bundle);

    void onAutoCompleteSelected(Place place);

    void onLocationChanged(Location location);

    void onMapClicked(LatLng location);

    void onMarkerClicked(Marker marker);

    void onPOIClicked(PointOfInterest poi);

    void onMapRefreshed();

    void onImageViewNavHeaderClicked(Bitmap photo);
}
