package com.example.administrator.travel.presenters.impls;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.travel.LocationService;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.ActivityInteractor;
import com.example.administrator.travel.models.bases.ActivitySuggestionInteractor;
import com.example.administrator.travel.models.bases.PlaceInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.ActivitySuggestion;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.impls.ActivityInteractorImpl;
import com.example.administrator.travel.models.impls.ActivitySuggestionInteractorImpl;
import com.example.administrator.travel.models.impls.PlaceInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.CreateActivityPresenter;
import com.example.administrator.travel.views.bases.CreateActivityView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

public class CreateActivityPresenterImpl implements CreateActivityPresenter,
        Listener.OnGetActivitySuggestionsFinishedListener, Listener.OnGetNearbyFinishedListener, Listener.OnPostActivityFinishedListener {
    private final static int CODE_MAPS = 239;
    private final static int CODE_SEARCH_PLACE = 294;

    private CreateActivityView view;
    private ActivitySuggestionInteractor activitySuggestionInteractor;
    private LocationService locationService;
    private MyLatLng location;
    private boolean clickSuggest, isTourGuide;
    private String tourStartId;

    private ActivitySuggestion selectedActivitySuggestion;
    private Nearby selectedPlaceSuggestion;

    private PlaceInteractor placeInteractor;
    private UserInteractor userInteractor;
    private ActivityInteractor activityInteractor;


    public CreateActivityPresenterImpl(CreateActivityView view) {
        this.view = view;
        activitySuggestionInteractor = new ActivitySuggestionInteractorImpl();
        placeInteractor = new PlaceInteractorImpl();
        userInteractor = new UserInteractorImpl();
        activityInteractor = new ActivityInteractorImpl();

        bindLocationService();
        view.showWaitForLocationDialog();
        view.hideLayoutPlaceAddress();
        view.hideLayoutPlaceSuggest();
    }

    private void bindLocationService() {
        Intent mIntent = new Intent(this.view.getContext(), LocationService.class);
        view.getContext().bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            locationService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService.LocalBinder mLocalBinder = (LocationService.LocalBinder) service;
            locationService = mLocalBinder.getServerInstance();
            location = locationService.getCurrentLocation();
            Log.e("onServiceConnected: ", location + "");
            view.closeWaitForLocationDialog();
        }
    };

    @Override
    public void onViewCreated(Bundle bundle) {
        tourStartId = bundle.getString("tourStartId");
        isTourGuide = bundle.getBoolean("isTourGuide");
        activitySuggestionInteractor.getActivitySuggestion(isTourGuide, this);
    }

    @Override
    public void onEditTextContentClicked() {
        view.hideLayoutPlaceAddress();
        view.setTextEditTextContent("");
        selectedPlaceSuggestion = null;
    }

    @Override
    public void onTextViewPlaceAddressClicked() {
        if (selectedPlaceSuggestion != null || selectedPlaceSuggestion.placeId != null)
            view.gotoMapsActivity(selectedPlaceSuggestion.placeId);
    }

    @Override
    public void onButtonPostClicked() {
        if (userInteractor.isLogged()) {
            String myId = userInteractor.getUserId();
            if (isTourGuide) {
                String content = selectedActivitySuggestion.value + " " + selectedPlaceSuggestion.name;
                String[] suggestTypesSplit = selectedActivitySuggestion.keywords.split(",");
                String placeType = null;
                for (String suggestType : suggestTypesSplit) {
                    if (placeType != null)
                        break;
                    for (String type : selectedPlaceSuggestion.types) {
                        if (suggestType.equals(type)) {
                            placeType = type;
                            break;
                        }
                    }
                }

                Activity activity = new Activity(myId, tourStartId, selectedPlaceSuggestion.placeId, placeType, content, selectedActivitySuggestion.type, location);
                activityInteractor.postActivity(activity, this);
                Toast.makeText(view.getContext(), "post", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemActivitySuggestClicked(ActivitySuggestion activitySuggestion) {
        selectedActivitySuggestion = activitySuggestion;
        clickSuggest = true;
        String[] keywordsSplit = activitySuggestion.keywords.split(",");
        if (location == null) return;
        LatLng currentLocation = location.getLatLng();
        if (currentLocation != null) {
            for (String keyword : keywordsSplit)
                placeInteractor.getNearby(keyword, currentLocation, view.getContext().getResources().getString(R.string.google_maps_key), this);
            view.hideSoftKeyboard();
        }
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
        if (clickSuggest) {
            clickSuggest = false;
            view.showNearby(nearbyList);
        } else {
            view.appendNearby(nearbyList);
        }
        view.showLayoutPlaceSuggest();


    }

    @Override
    public void onGetNearbyFail(Exception ex) {
    }

    @Override
    public void onPostActivitySuccess() {
        Toast.makeText(view.getContext(), "Successssss", Toast.LENGTH_SHORT).show();
        view.closeForResult(android.app.Activity.RESULT_OK);
    }

    @Override
    public void onPostActivityFail(Exception ex) {
        Toast.makeText(view.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();

    }
}
