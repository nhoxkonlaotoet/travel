package com.example.administrator.travel.models.entities;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewFeedInteractorImpl implements NewFeedInteractor {
    public List<Tour> listTour= new ArrayList<>();
    @Override
    public List<Tour> getTour(final OnGetNewFeedItemFinishedListener listener) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference("tours");
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot1 : dataSnapshot.getChildren()) {
                //    Log.e("onDataChange: ",  Snapshot1+"");
                    listTour.add(Snapshot1.getValue(Tour.class));
                }
                for(Tour tour : listTour)
                {
                    Log.e("onDataChange: ", tour.toString());
                }
                if(listTour.size()!=0) {
                    listener.onSuccess(listTour);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }
}
