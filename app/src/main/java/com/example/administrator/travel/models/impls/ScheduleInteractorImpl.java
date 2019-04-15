package com.example.administrator.travel.models.impls;

import android.util.Log;

import com.example.administrator.travel.models.bases.ScheduleInteractor;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/5/2019.
 */

public class ScheduleInteractorImpl implements ScheduleInteractor {
    @Override
    public void getSchedule(String tourId, String dayId, final Listener.OnGetScheduleFinishedListener listener) {
        final List<Schedule> lstSchedule = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference schedulesRef = database.getReference("schedules").child(tourId);
        Query query = schedulesRef.orderByChild("day").equalTo(dayId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Schedule schedule = dataSnapshot1.getValue(Schedule.class);
                    schedule.id=dataSnapshot1.getKey();
                    lstSchedule.add(schedule);
                }
                listener.onGetScheduleSuccess(lstSchedule);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetScheduleFail(databaseError.toException());
            }
        });
    }
}
