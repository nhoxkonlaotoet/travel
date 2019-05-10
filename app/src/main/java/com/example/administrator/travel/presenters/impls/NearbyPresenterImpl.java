package com.example.administrator.travel.presenters.impls;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.travel.LocationObservable;
import com.example.administrator.travel.LocationService;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.DownLoadImageTask;
import com.example.administrator.travel.models.bases.NearbyInteractor;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.impls.NearbyInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.NearbyPresenter;
import com.example.administrator.travel.views.NearbyView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Administrator on 23/12/2018.
 */

public class NearbyPresenterImpl implements NearbyPresenter,
        Listener.OnGetNearbyFinishedListener, Listener.OnGetPlaceTypeFinishedListener,
        Listener.OnDownloadImageFinishedListener {
    NearbyView view;
    NearbyInteractor nearbyInteractor;
    String type;
    MyLatLng location;
    String nextPageToken = "";
    String apiKey;
    int page = 0;
    LocationService locationService;
    List<NearbyType> lstPlaceType=new ArrayList<>();
    boolean loadFinished = false;

    public NearbyPresenterImpl(NearbyView view) {
        this.view = view;
        nearbyInteractor = new NearbyInteractorImpl();
        if (view.getContext() != null)
            apiKey = view.getContext().getResources().getString(R.string.google_api_key);
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
        nearbyInteractor.getPlaceType(this);
    }

    @Override
    public void onListViewNearbyScrollBottom() {
        if (page < 3 && loadFinished && !nextPageToken.equals("")) {
            loadFinished = false;
            nearbyInteractor.getNearby(type, new LatLng(location.latitude, location.longitude), nextPageToken, apiKey, this);
            page++;
        }

    }

    @Override
    public void onSelectItemSpinnerPlaceType(int index) {
        if (location != null) {
            type=lstPlaceType.get(index).value;
            loadFinished = false;
            nextPageToken="";
            page = 1;
            nearbyInteractor.getNearby(type, new LatLng(location.latitude, location.longitude), nextPageToken, apiKey, this);
        }
    }

    @Override
    public void onGetNearbySuccess(List<Nearby> lstNearby, String nextPageToken) {
        Log.e("onGetNearbySuccess: ", lstNearby.get(0).toString());

        loadFinished = true;
        this.nextPageToken = nextPageToken;
        if (page == 1)
            view.showNearbys(lstNearby, location);
        else {
            view.appendNearbys(lstNearby);
        }
        String url;
        for (int i = 0; i < lstNearby.size(); i++) {
            {
                if (!lstNearby.get(i).photo_reference.equals("")) {
                    url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference="
                            + lstNearby.get(i).photo_reference
                            + "&key=" + apiKey;
                } else {
                    url = lstNearby.get(i).iconURl;
                }
                // 20 items/ page
                new DownLoadImageTask(this).execute(((page-1)*20 + i) + "", url);
            }
        }

    }

    @Override
    public void onGetNearbyFail(Exception ex) {
        view.notifyGetNearbyFailure(ex);
    }

    @Override
    public void onGetPlaceTypeSuccess(List<NearbyType> lstPlaceType) {
        this.lstPlaceType=lstPlaceType;
        view.showPlacetypes(lstPlaceType);
    }

    @Override
    public void onGetPlaceTypeFail(Exception ex) {

    }


    @Override
    public void onDownloadImageFail(Exception e) {

    }

    @Override
    public void onDownloadImageSuccess(int pos, Bitmap result) {
        view.updateListViewImages(pos, result);
    }
}
