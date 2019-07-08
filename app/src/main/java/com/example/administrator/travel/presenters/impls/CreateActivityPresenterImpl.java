package com.example.administrator.travel.presenters.impls;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.travel.LocationService;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.ActivitySuggestionInteractor;
import com.example.administrator.travel.models.bases.PlaceInteractor;
import com.example.administrator.travel.models.entities.ActivitySuggestion;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.example.administrator.travel.models.impls.ActivitySuggestionInteractorImpl;
import com.example.administrator.travel.models.impls.PlaceInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.CreateActivityPresenter;
import com.example.administrator.travel.views.bases.CreateActivityView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

public class CreateActivityPresenterImpl implements CreateActivityPresenter,
        Listener.OnGetActivitySuggestionsFinishedListener, Listener.OnGetNearbyFinishedListener {
    private final static int CODE_MAPS = 239;
    private final static int CODE_SEARCH_PLACE = 294;

    private CreateActivityView view;
    private ActivitySuggestionInteractor activitySuggestionInteractor;
    private PlaceInteractor placeInteractor;
    private LocationService locationService;
    private MyLatLng location;
    private boolean clickSuggest = false;
    private ActivitySuggestion selectedActivitySuggestion;
    private Nearby selectedPlaceSuggestion;

    public CreateActivityPresenterImpl(CreateActivityView view) {
        this.view = view;
        activitySuggestionInteractor = new ActivitySuggestionInteractorImpl();
        placeInteractor = new PlaceInteractorImpl();
        Intent mIntent = new Intent(this.view.getContext(), LocationService.class);
        view.getContext().bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        view.showWaitForLocationDialog();
        view.hideLayoutPlaceAddress();
        view.hideLayoutPlaceSuggest();
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
            view.closeWaitForLocationDialog();
        }
    };

    @Override
    public void onViewCreated(Bundle bundle) {
        activitySuggestionInteractor.getActivitySuggestion(this);
    }

    @Override
    public void onEditTextContentClicked() {
        view.hideLayoutPlaceAddress();
        view.hideLayoutPlaceSuggest();
        view.setTextEditTextContent("");
        selectedPlaceSuggestion=null;
    }

    @Override
    public void onTextViewPlaceAddressClicked() {
        if (selectedPlaceSuggestion != null || selectedPlaceSuggestion.placeId != null)
            view.gotoMapsActivity(selectedPlaceSuggestion.placeId);
    }

    @Override
    public void onButtonPostClicked() {

    }

    @Override
    public void onItemActivitySuggestClicked(ActivitySuggestion activitySuggestion) {
        selectedActivitySuggestion = activitySuggestion;
        clickSuggest = true;
        String[] keywordsSplit = activitySuggestion.keywords.split(",");
        LatLng currentLocation = location.getLatLng();
        for (String keyword : keywordsSplit)
            placeInteractor.getNearby(keyword, currentLocation, view.getContext().getResources().getString(R.string.google_maps_key), this);
        view.hideSoftKeyboard();
    }

    @Override
    public void onItemPlaceSuggestClicked(Nearby nearby) {
        selectedPlaceSuggestion = nearby;
        view.setTextEditTextContent(selectedActivitySuggestion.value + " " + nearby.name);
        view.showPlaceAddress(nearby.vicinity);
        view.showLayoutPlaceAddress();
        view.hideSoftKeyboard();
    }

    @Override
    public void onEditTextContentTypingStoped() {
        view.showLayoutPlaceSuggest();
    }

    @Override
    public void onViewResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onGetActivitySuggestionsSuccess(List<ActivitySuggestion> activitySuggestionList) {
        view.showActivitySuggestions(activitySuggestionList);
    }

    @Override
    public void onGetActivitySuggestionsFail(Exception ex) {

    }

    @Override
    public void onGetNearbySuccess(List<Nearby> nearbyList, String nextPageToken) {
        view.showLayoutPlaceSuggest();
        if (clickSuggest) {
            clickSuggest = false;
            view.showNearby(nearbyList);
        } else {
            view.appendNearby(nearbyList);
        }

    }

    @Override
    public void onGetNearbyFail(Exception ex) {

    }
}
