package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.travel.models.entities.ActivitySuggestion;
import com.example.administrator.travel.models.entities.Nearby;

public interface CreateActivityPresenter {
    void onViewCreated(Bundle bundle);

    void onEditTextContentClicked();

    void onTextViewPlaceAddressClicked();

    void onButtonPostClicked();

    void onItemActivitySuggestClicked(ActivitySuggestion activitySuggestion);

    void onItemPlaceSuggestClicked(Nearby nearby);

    void onEditTextContentTypingStoped();

    void onViewResult(int requestCode, int resultCode, Intent data);
}