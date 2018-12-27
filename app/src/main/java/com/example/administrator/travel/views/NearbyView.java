package com.example.administrator.travel.views;

import android.location.Location;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;

import java.util.List;

/**
 * Created by Administrator on 23/12/2018.
 */

public interface NearbyView {
    void setTextRadius(String text);
    void showLayoutOption();
    void hideLayoutOption();
    void showPlacetypes(List<NearbyType> lstPlacetype);
    void showNearbys(List<Nearby> lstNearby,Location mylocation);
    void notifyGetNearbyFailure(Exception ex);
}
