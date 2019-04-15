package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.OnGetCompanyContactFinishedListener;
import com.example.administrator.travel.models.bases.TourStartDateInteractor;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public class TourStartInteractorImpl implements TourStartDateInteractor {
    @Override
    public void getTourStarts(String tourId, final Listener.OnGetTourStartFinishedListener listener) {
        final List<TourStartDate> list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference startDateRef = database.getReference("tour_start_date");
        startDateRef.orderByChild("tourId").equalTo(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Snapshot: dataSnapshot.getChildren())
                {
                    TourStartDate tourStartDate = Snapshot.getValue(TourStartDate.class);
                    if(tourStartDate.startDate< System.currentTimeMillis() || !tourStartDate.available)
                        continue;
                    tourStartDate.id = Snapshot.getKey();
                    list.add(tourStartDate);
                }
                listener.onGetTourStartSuccess(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetTourStartFail(databaseError.toException());
            }
        });
    }


}
