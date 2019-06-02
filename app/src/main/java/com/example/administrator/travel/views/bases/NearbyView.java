package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;

import java.util.List;

/**
 * Created by Administrator on 23/12/2018.
 */

public interface NearbyView {

    void showNearbyTypes(List<NearbyType> nearbyTypeList);

    void showNearbys(List<Nearby> nearbyList, MyLatLng mylocation);

    void appendNearbys(List<Nearby> nearbyList);

    void notify(String message);


    void gotoMapActivity(String origin, String destination, String openFrom);

    Context getContext();
}
