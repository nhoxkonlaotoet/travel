package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.TourStartInteractor;
import com.example.administrator.travel.models.entities.AddTourStartDateRequest;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    public void getTourStart(String tourStartId, final Listener.OnGetTourStartFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourStartsRef = database.getReference(TOUR_START_DATE_REF);
        tourStartsRef.child(tourStartId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TourStartDate tourStartDate = dataSnapshot.getValue(TourStartDate.class);
                tourStartDate.id = dataSnapshot.getKey();
                listener.onGetTourStartSuccess(tourStartDate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetTourStartFail(databaseError.toException());
            }
        });
    }

    @Override
    public String getTourGuideId(String tourStartId, Context context) {
        if (context == null)
            return "";
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String tourGuideId = prefs.getString("participatingTourGuide" + tourStartId, "");
        return tourGuideId;
    }
    @Override
    public void addTourStartRequest(String tourId, AddTourStartDateRequest addTourStartDateRequest, final Listener.OnAddTourStartDateRequest listener)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference addTourStartsRef = database.getReference("add_tour_start_request").child(tourId);
        String key = addTourStartsRef.push().getKey();
        addTourStartsRef.child(key).setValue(addTourStartDateRequest.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onAddTourStartDateSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onAddTourStartDateFail(e);
            }
        });
    }


}
