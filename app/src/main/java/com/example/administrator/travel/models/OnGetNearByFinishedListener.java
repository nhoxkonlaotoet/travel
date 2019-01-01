package com.example.administrator.travel.models;

import android.location.Location;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;
import com.google.android.gms.location.places.PlaceTypes;

import java.util.List;

/**
 * Created by Administrator on 23/12/2018.
 */

public interface OnGetNearByFinishedListener {
    void onGetNearbySuccess(List<Nearby> lstNearby, String nextPageToken);
    void onGetNearbyFailure(Exception ex);
    void onGetPlaceTypeSuccess(List<NearbyType> lstPlaceType);
    void onGetPlaceTypeFailure(Exception ex);
}
