package com.example.administrator.travel.presenters.bases;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Admin on 5/8/2019.
 */

public interface MapPresenter {
    void onViewCreated(Bundle bundle);

    void onAutoCompleteSelected(Place place);

    void onMapClicked(LatLng location);

    void onMarkerClicked(Marker marker);

    void onMapRefreshed();

    void onImageViewNavHeaderClicked(Bitmap photo);
}
