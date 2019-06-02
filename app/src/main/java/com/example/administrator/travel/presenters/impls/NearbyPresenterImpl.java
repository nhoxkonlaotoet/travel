package com.example.administrator.travel.presenters.impls;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.travel.LocationService;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.PicassoInteractor;
import com.example.administrator.travel.models.bases.PlaceInteractor;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.example.administrator.travel.models.impls.PicassoInteractorImpl;
import com.example.administrator.travel.models.impls.PlaceInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.NearbyPresenter;
import com.example.administrator.travel.views.bases.NearbyView;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Administrator on 23/12/2018.
 */

public class NearbyPresenterImpl implements NearbyPresenter,
        Listener.OnGetNearbyFinishedListener, Listener.OnGetPlaceTypeFinishedListener {
    NearbyView view;
    PlaceInteractor placeInteractor;
    PicassoInteractor picassoInteractor;
    String nearbyType;
    MyLatLng location;
    String nextPageToken;
    int page = 0;
    LocationService locationService;
    boolean loadFinished;

    public NearbyPresenterImpl(NearbyView view) {
        this.view = view;
        placeInteractor = new PlaceInteractorImpl();
        picassoInteractor = new PicassoInteractorImpl();
        Intent mIntent = new Intent(this.view.getContext(), LocationService.class);
        view.getContext().bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("onServiceDisconnected: ", "____");
            locationService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("onServiceConnected: ", "++++");
            LocationService.LocalBinder mLocalBinder = (LocationService.LocalBinder) service;
            locationService = mLocalBinder.getServerInstance();
            location = locationService.getCurrentLocation();
        }
    };

    @Override
    public void onViewCreated() {
        placeInteractor.getPlaceType(this);
    }

    @Override
    public void onRecyclerViewNearbyScrollBottom() {
        if (page != 0 && page < 3 && loadFinished && nextPageToken != null && !nextPageToken.equals("")) {
            loadFinished = false;
            placeInteractor.getNearby(nearbyType, new LatLng(location.latitude, location.longitude), nextPageToken,
                    view.getContext().getResources().getString(R.string.google_maps_key), this);
            page++;
        }

    }

    @Override
    public void onNearbyTypeItemClicked(String nearbyTypeValue) {
        if(nearbyTypeValue.equals(nearbyType))
            return;
        nearbyType = nearbyTypeValue;
        if (view.getContext()!=null && location != null) {
            loadFinished = false;
            nextPageToken = "";
            page = 1;
            placeInteractor.getNearby(nearbyTypeValue, new LatLng(location.latitude, location.longitude),
                    view.getContext().getResources().getString(R.string.google_maps_key), this);
        } else
            view.notify("Xin vui lòng cho phép truy cập vị trí của bạn");
    }

    @Override
    public void onNearbyItemClicked(Nearby nearby) {
        StringBuilder origin = new StringBuilder()
                .append(location.latitude)
                .append(",")
                .append(location.longitude);
        StringBuilder destination = new StringBuilder()
                .append(nearby.geometry.location.lat.toString())
                .append(",")
                .append(nearby.geometry.location.lng.toString());
        String openFrom = view.getContext().getResources().getString(R.string.open_from_nearby);
        view.gotoMapActivity(origin.toString(), destination.toString(), openFrom);
    }

    @Override
    public void onGetNearbySuccess(List<Nearby> nearbyList, String nextPageToken) {
        loadFinished = true;
        this.nextPageToken = nextPageToken;
        if (page == 1)
            view.showNearbys(nearbyList, location);
        else {
            view.appendNearbys(nearbyList);
            view.notify("▼ Xem thêm");
        }
    }

    @Override
    public void onGetNearbyFail(Exception ex) {
        view.notify(ex.getMessage());
    }

    @Override
    public void onGetPlaceTypeSuccess(List<NearbyType> nearbyTypeList) {
        view.showNearbyTypes(nearbyTypeList);
        if (nearbyTypeList.size() != 0)
            nearbyType = nearbyTypeList.get(0).value;
        {
            if (location != null && view.getContext()!=null) {
                loadFinished = false;
                nextPageToken = "";
                page = 1;
                placeInteractor.getNearby(nearbyType, new LatLng(location.latitude, location.longitude),
                        view.getContext().getResources().getString(R.string.google_maps_key), this);
            } else
                view.notify("Xin vui lòng cho phép truy cập vị trí của bạn");
        }
    }

    @Override
    public void onGetPlaceTypeFail(Exception ex) {
        view.notify(ex.getMessage());
    }



}
