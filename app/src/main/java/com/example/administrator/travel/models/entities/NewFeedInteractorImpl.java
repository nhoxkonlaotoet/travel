package com.example.administrator.travel.models.entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewFeedInteractorImpl implements NewFeedInteractor {

    @Override
    public void getTour(OnGetNewFeedItemFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference("tours");
        tourRef.add
    }
}
