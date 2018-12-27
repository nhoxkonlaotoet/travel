package com.example.administrator.travel.presenters;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.example.administrator.travel.models.OnGetTourImagesFinishedListener;
import com.example.administrator.travel.models.TourInteractor;
import com.example.administrator.travel.views.NewFeedView;
import com.example.administrator.travel.views.TourView;
import com.example.administrator.travel.views.activities.TourActivity;
import com.google.android.gms.common.ConnectionResult;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourPresenter implements OnGetTourImagesFinishedListener{
    TourView view;
    TourInteractor interactor;
    Boolean isMyTour=false;
    public TourPresenter(TourView view)
    {
        this.view=view;
        interactor=new TourInteractor();
    }

    public void onViewLoad(String tourId, Boolean isMyTour){
        if(!isMyTour)
            interactor.getImages(tourId,this);
        view.addTab(isMyTour);
        view.connectGoogleApiClient();

    }
    public void onViewAttachFragment(Location myLocation){
        if(myLocation!=null)
            view.transmitLocationToFragment(myLocation);
    }
    public void onViewStop(){
        view.stopGoogleApiClient();
    }
    public void onGoogleApiClientConnected()
    {
        view.startLocationServices();
    }
    public void onGoogleApiClientConnectFailed(ConnectionResult connectionResult)
    {
        view.notifyConnectFailed(connectionResult);
    }
    public void onLocationChanged(Location location){
        view.transmitLocationToFragment(location);
    }

    @Override
    public void onSuccess(Bitmap[] bitmaps, Integer numberofImages) {
        for(int i=0;i<numberofImages;i++)
        {
            Log.e("presenter tour",bitmaps[i].getByteCount()+"" );
        }
        view.showImages(bitmaps,numberofImages);

    }

    @Override
    public void onFailure() {

    }
}
