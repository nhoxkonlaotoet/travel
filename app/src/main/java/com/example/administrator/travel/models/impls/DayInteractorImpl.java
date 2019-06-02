package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.bases.DayInteractor;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/5/2019.
 */

public class DayInteractorImpl implements DayInteractor {
    @Override
    public void getDays(String tourId, final Listener.OnGetDaysFinishedListener listener)
    {
        final List<Day> lstDay = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference daysRef = database.getReference("days").child(tourId);
        daysRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Day day = dataSnapshot1.getValue(Day.class);
                    day.id=dataSnapshot1.getKey();
                    lstDay.add(day);
                }
                listener.onGetDaysSuccess(lstDay);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetDaysFail(databaseError.toException());
            }
        });

    }
}
