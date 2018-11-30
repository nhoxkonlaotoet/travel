package com.example.administrator.travel.models;

import android.support.annotation.NonNull;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 18/11/2018.
 */

public class BookTourInteractor {
    public BookTourInteractor(){}

    //bookTour: kiem tra so nguoi tham gia tour + so nguoi da co > tong so nguoi cho phep hay không
    // nếu > hơn thì báo lỗi và return
    // ghi booktour vào csdl
    public void bookTour(final String tourId, final TourBooking tourBooking, final OnBookTourFinishedListener listener){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourStartDateRef = database.getReference("tour_start_date");
        tourStartDateRef.child(tourId).child(tourBooking.tourStartDateId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TourStartDate tourStartDate  = dataSnapshot.getValue(TourStartDate.class);
                Integer addPeople = tourBooking.adult+tourBooking.children;//+tourBooking.baby; em bé có tính là 1 người không?

                if(tourStartDate.peopleBooking+addPeople > tourStartDate.maxPeople) {
                    listener.onBookTourFailure(new Exception("Số người đăng ký đã đủ"));
                    return;
                }
                DatabaseReference tourBookingRef = database.getReference("tour_booking");
                String key = tourBooking.tourStartDateId+"+"+tourBooking.bookingTime+"+"+tourBooking.userId;
                tourBookingRef.child(key).setValue(tourBooking).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onBookTourSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onBookTourFailure(e);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onBookTourFailure(new Exception("Không thể kết nối tới hệ thống \r\n"+databaseError.toString()));
            }
        });



    }
    //code hoi ngu
    private void updatePeopleBooking(String tourId, final TourBooking tourBooking, final OnBookTourFinishedListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tourStartRef = database.getReference("tour_start_date")
                .child(tourId).child(tourBooking.tourStartDateId);
        tourStartRef.child("peopleBooking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer peopleBooking = dataSnapshot.getValue(Integer.class);
                Integer addPeople = tourBooking.adult+tourBooking.children+tourBooking.baby;

                tourStartRef.child("peopleBooking").setValue(peopleBooking + addPeople)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onBookTourSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        deleteBooking(tourBooking);
                        listener.onBookTourFailure(e);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void deleteBooking(TourBooking tourBooking){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourBookingRef = database.getReference("tour_booking");
        tourBookingRef.child(tourBooking.tourStartDateId+"+"+tourBooking.bookingTime+"+"+tourBooking.userId).removeValue();
    }
}