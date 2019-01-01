package com.example.administrator.travel.views;

import android.graphics.Bitmap;
import android.location.Location;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;

import java.util.List;

/**
 * Created by Administrator on 23/12/2018.
 */

public interface NearbyView {

    void showPlacetypes(List<NearbyType> lstPlacetype);
    void showNearbys(List<Nearby> lstNearby,Location mylocation);
    void appendNearbys(List<Nearby> lstNearby);
    void notifyGetNearbyFailure(Exception ex);
    void updateListViewImages(int index, Bitmap bitmap);

}
