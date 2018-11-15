package com.example.administrator.travel.models;

import android.util.Log;

import com.example.administrator.travel.models.entities.TourStartDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15/11/2018.
 */

public class TourStartInteractor {
    public void getStartDate(final String tourId, final OnGetTourStartDateFinishedListener listener)
    {
        final List<TourStartDate> list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference startDateRef = database.getReference("tour_start_date").child(tourId);
        startDateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Snapshot: dataSnapshot.getChildren())
                {
                    TourStartDate tourStartDate = Snapshot.getValue(TourStartDate.class);
                    tourStartDate.id = Snapshot.getKey();
                    list.add(tourStartDate);
                    listener.onSuccess(list);
                    Log.e("onDataChange",tourStartDate.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return;
    }
}
