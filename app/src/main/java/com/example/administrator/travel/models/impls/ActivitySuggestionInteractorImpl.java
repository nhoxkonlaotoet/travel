package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.bases.ActivitySuggestionInteractor;
import com.example.administrator.travel.models.entities.ActivitySuggestion;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivitySuggestionInteractorImpl implements ActivitySuggestionInteractor {
    private final static String SUGGEST_ACTIVITIES_REF = "suggest_activities";

    @Override
    public void getActivitySuggestion(final Listener.OnGetActivitySuggestionsFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference suggestsRef = database.getReference(SUGGEST_ACTIVITIES_REF);

        suggestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<ActivitySuggestion> activitySuggestionList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ActivitySuggestion activitySuggestion = snapshot.getValue(ActivitySuggestion.class);
                    activitySuggestion.id = snapshot.getKey();
                    activitySuggestionList.add(activitySuggestion);
                }
                listener.onGetActivitySuggestionsSuccess(activitySuggestionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetActivitySuggestionsFail(databaseError.toException());
            }
        });
    }
}
