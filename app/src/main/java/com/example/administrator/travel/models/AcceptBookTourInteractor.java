package com.example.administrator.travel.models;

import android.util.Log;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public class AcceptBookTourInteractor {
    public void getBookings(final String tourStartId, final OnAcceptBookingFinishListener listener){
        final List<TourBooking> lstBooking = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference tourstartRef = database.getReference("tour_start_date").child(tourStartId).child("finished");
        tourstartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean finished = dataSnapshot.getValue(Boolean.class);
                if(!finished){
                    DatabaseReference bookingRef = database.getReference("tour_booking");
                    Query query = bookingRef.orderByChild("tourStartDateId").equalTo(tourStartId);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                TourBooking tourBooking = ds.getValue(TourBooking.class);
                                lstBooking.add(tourBooking);
                            }
                            listener.onGetBookingsSuccess(lstBooking);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onGetBookingsFailure(databaseError.toException());
                        }
                    });
                }
                else
                {
                    listener.onGetBookingsFinishedTour();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void getUserInfor(String userId, final OnAcceptBookingFinishListener listener)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(userId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);
                user.id=dataSnapshot.getKey();
                listener.onGetUserInforSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
