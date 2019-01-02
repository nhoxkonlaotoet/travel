package com.example.administrator.travel.models;

import android.util.Log;

import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourDetailInteractor {
    FirebaseDatabase database;
    DatabaseReference tourRef,daysRef,schedulesRef;
    public TourDetailInteractor(){
        database  = FirebaseDatabase.getInstance();
    }
    public void getInfor(String tourId, final OnGetTourDetailFinishedListener listener)
    {
        tourRef = database.getReference("tours").child(tourId);
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tour tour = dataSnapshot.getValue(Tour.class);
                listener.onGetInforSuccess(tour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled: ", "FIREBASE______________________________________");
            }
        });
    }
    public void getDays(String tourId, final OnGetTourDetailFinishedListener listener)
    {
        final List<Day> lstDay = new ArrayList<>();
        daysRef = database.getReference("days").child(tourId);
        daysRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                   // Log.e( "onDataChange: ", dataSnapshot1.getValue().toString());
                    Day day = dataSnapshot1.getValue(Day.class);
                    day.id=dataSnapshot1.getKey();
                    lstDay.add(day);
                }
                listener.onGetDaySuccess(lstDay);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetDayFailure(databaseError.toException());
            }
        });

    }
    public void getSchedule(String tourId, String dayId, final OnGetScheduleFinishedListener listener)
    {
        final List<Schedule> lstSchedule = new ArrayList<>();
        schedulesRef = database.getReference("schedules").child(tourId);
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
                listener.onGetScheduleFailure(databaseError.toException());
            }
        });
    }

}
