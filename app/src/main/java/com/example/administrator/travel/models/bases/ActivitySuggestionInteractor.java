package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.listeners.Listener;

public interface ActivitySuggestionInteractor {
    void getActivitySuggestion(Listener.OnGetActivitySuggestionsFinishedListener listener);
}
