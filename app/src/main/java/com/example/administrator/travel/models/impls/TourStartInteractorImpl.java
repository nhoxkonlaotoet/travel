package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.travel.models.bases.TourStartInteractor;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public class TourStartInteractorImpl implements TourStartInteractor {
    final static String TOUR_START_DATE_REF = "tour_start_date";

    @Override
    public void getTourStarts(String tourId, final Listener.OnGetTourStartsFinishedListener listener) {
        final List<TourStartDate> list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference startDateRef = database.getReference(TOUR_START_DATE_REF);
        startDateRef.orderByChild("tourId").equalTo(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    TourStartDate tourStartDate = Snapshot.getValue(TourStartDate.class);
                    if (tourStartDate.startDate < System.currentTimeMillis() || !tourStartDate.available)
                        continue;
                    tourStartDate.id = Snapshot.getKey();
                    list.add(tourStartDate);
                }
                listener.onGetTourStartsSuccess(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetTourStartsFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getTourStart(String tourStartId, Listener.OnGetTourStartFinishedListener listener) {

    }

    @Override
    public String getTourGuideId(String tourStartId, Context context) {
        if (context == null)
            return "";
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String tourGuideId = prefs.getString("participatingTourGuide" + tourStartId, "");
        return tourGuideId;
    }



}
