package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.listeners.Listener;

public interface ActivitySuggestionInteractor {
    void getActivitySuggestion(boolean isTourGuide, Listener.OnGetActivitySuggestionsFinishedListener listener);
}
