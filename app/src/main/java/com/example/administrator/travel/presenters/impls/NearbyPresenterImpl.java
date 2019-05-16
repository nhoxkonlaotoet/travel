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
        Listener.OnGetNearbyFinishedListener, Listener.OnGetPlaceTypeFinishedListener, Listener.OnPicassoLoadFinishedListener {
    NearbyView view;
    PlaceInteractor placeInteractor;
    PicassoInteractor picassoInteractor;
    String type;
    MyLatLng location;
    String nextPageToken;
    int page = 0;
    LocationService locationService;
    List<NearbyType> lstPlaceType = new ArrayList<>();
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
    public void onListViewNearbyScrollBottom() {
        if (page < 3 && loadFinished && nextPageToken != null && !nextPageToken.equals("")) {
            loadFinished = false;
            placeInteractor.getNearby(type, new LatLng(location.latitude, location.longitude), nextPageToken,
                    view.getContext().getResources().getString(R.string.google_api_key), this);
            page++;
        }

    }

    @Override
    public void onSelectItemSpinnerPlaceType(int index) {
        if (location != null) {
            picassoInteractor.cleanGarbages();
            type = lstPlaceType.get(index).value;
            loadFinished = false;
            nextPageToken = "";
            page = 1;
            placeInteractor.getNearby(type, new LatLng(location.latitude, location.longitude),
                    view.getContext().getResources().getString(R.string.google_api_key), this);
        }
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
        view.gotoMapActivity(origin.toString(),destination.toString(),openFrom);
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
        for (int i = 0; i < nearbyList.size(); i++) {
            String url;
            try {
                url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference="
                        + nearbyList.get(i).photos.get(0).photoReference
                        + "&key=" + view.getContext().getResources().getString(R.string.google_api_key);
            } catch (Exception e) {
                url = nearbyList.get(i).icon;
            }
            picassoInteractor.load(view.getContext(),(page-1)*20 + i,url,this);
        }
    }

    @Override
    public void onGetNearbyFail(Exception ex) {
        view.notify(ex.getMessage());
    }

    @Override
    public void onGetPlaceTypeSuccess(List<NearbyType> lstPlaceType) {
        this.lstPlaceType = lstPlaceType;
        view.showPlacetypes(lstPlaceType);
    }

    @Override
    public void onGetPlaceTypeFail(Exception ex) {

    }


    @Override
    public void onPicassoLoadSuccess(int pos, Bitmap photo) {
        view.updateListViewImages(pos,photo);
    }

    @Override
    public void onPicassoLoadFail(Exception ex) {

    }
}
