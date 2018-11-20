package com.example.administrator.travel.models;

import android.support.annotation.NonNull;

import com.example.administrator.travel.models.entities.TourBooking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Administrator on 18/11/2018.
 */

public class BookTourInteractor {
    public BookTourInteractor(){}
    public void bookTour(String tourStartId, TourBooking tourBooking, final OnBookTourFinishedListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourBookingRef = database.getReference("tour_booking").child(tourStartId);
        String key = tourBookingRef.push().getKey();
        tourBookingRef.child(key).setValue(tourBooking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure(e);
            }
        });
    }
}
