package com.example.administrator.travel.presenters;

import android.location.Location;
import android.util.Log;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.NearbyInteractor;
import com.example.administrator.travel.models.OnGetNearByFinishedListener;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.views.NearbyView;
import com.example.administrator.travel.views.fragments.NearbyFragment;
import com.google.android.gms.location.places.PlaceTypes;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Administrator on 23/12/2018.
 */

public class NearbyPresenter implements OnGetNearByFinishedListener {
    NearbyView view;
    NearbyInteractor interactor;
    Integer radius;
    String type;
    Boolean showOption=false;
    Location location;
    public NearbyPresenter(NearbyView view)
    {
        this.view=view;
        interactor=new NearbyInteractor(this);
    }
    public void onViewLoad()
    {
        interactor.getPlaceType(this);

    }
    public void onSelectItemSpinnerPlaceType(String type, String apiKey)
    {
        this.type=type;
        if(location==null)
            Log.e( "location is null:  ","true" );
        else
            interactor.getNearby(new LatLng(location.getLatitude(),location.getLongitude()),type, radius, apiKey);
    }
    public void onSeekbarRadiusChanged(int i)
    {
        radius = i*50; // (%seekbar)*5000
        if(radius >= 1000) {
            radius=((Math.round(((float)radius)/100))*100);

            view.setTextRadius(((float)radius)/1000+"km");
        }
        else
            view.setTextRadius(radius+"m");
    }
    public void onViewReceivedLocation(Location location){
        this.location=location;
    }
    public void onItemNearbyClicked()
    {

    }
    public void onBtnOptionClicked()
    {
        if(showOption)
        {
            showOption=false;
            view.hideLayoutOption();
        }
        else
        {
            showOption=true;
            view.showLayoutOption();
        }
    }
    public void onBtnSearchClicked()
    {
        interactor.getNearby(new LatLng(location.getLatitude(),location.getLongitude()),type, radius,((NearbyFragment)view).getString(R.string.google_maps_key));
        view.hideLayoutOption();
    }

    @Override
    public void onGetNearbySuccess(List<Nearby> lstNearby) {
        view.showNearbys(lstNearby,location);
        interactor=new NearbyInteractor(this);
    }

    @Override
    public void onGetNearbyFailure(Exception ex) {
        view.notifyGetNearbyFailure(ex);
    }

    @Override
    public void onGetPlaceTypeSuccess(List<NearbyType> lstPlaceType) {
        view.showPlacetypes(lstPlaceType);
    }

    @Override
    public void onGetPlaceTypeFailure(Exception ex) {

    }
}
