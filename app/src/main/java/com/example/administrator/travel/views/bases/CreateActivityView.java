package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.models.entities.ActivitySuggestion;
import com.example.administrator.travel.models.entities.Nearby;

import java.util.List;

public interface CreateActivityView {
    void onFocusChange(View v, boolean hasFocus);

    void showActivitySuggestions(List<ActivitySuggestion> activitySuggestionList);


    void showNearby(List<Nearby> nearbyList);

    void appendNearby(List<Nearby> nearbyList);

    void setTextEditTextContent(String text);

    void showWaitForLocationDialog();

    void closeWaitForLocationDialog();

    void gotoMapsActivity(String placeId);

    void gotoPlaceSearchActivity(int searchCode);

    void showPlaceAddress(String address);

    void showLayoutPlaceAddress();

    void hideLayoutPlaceAddress();

    void showLayoutPlaceSuggest();

    void hideLayoutPlaceSuggest();

    void hideSoftKeyboard();

    void closeForResult(int resultCode);
    Context getContext();
}
