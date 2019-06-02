package com.example.administrator.travel.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.views.activities.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 18/11/2018.
 */
//
//public class BookTourInteractor {
//    public BookTourInteractor(){}
//    public void getTourBooking(String tourStartId, Context context, final Listener.OnBookTourFinishedListener listener){
//        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
//        final String userId = prefs.getString("AuthID","");
//        if(!userId.equals("none")){
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("tour_booking").child(tourStartId+"+"+userId);
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    TourBooking tourBooking = dataSnapshot.getValue(TourBooking.class);
//                    listener.onGetMyBookTourSuccess(tourBooking);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    listener.onGetMyBookTourFailure(databaseError.toException());
//                }
//            });
//
//        }
//    }
//    public void getPrices(String tourStartId, final Listener.OnBookTourFinishedListener listener){
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("tour_start_date").child(tourStartId);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                TourStartDate tourStartDate = dataSnapshot.getValue(TourStartDate.class);
//                tourStartDate.id=dataSnapshot.getKey();
//                listener.onGetPricesSuccess(tourStartDate);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                listener.onGetPricesFailure(databaseError.toException());
//            }
//        });
//    }
//    //bookTour: kiem tra so nguoi tham gia tour + so nguoi da co > tong so nguoi cho phep hay không
//    // nếu > hơn thì báo lỗi và return
//    // ghi booktour vào csdl
//    public void bookTour(Context context, final TourBooking tourBooking, final OnBookTourFinishedListener listener){
//        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
//        final String userId = prefs.getString("AuthID","");
//        if(!userId.equals("none")) {
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            final DatabaseReference timeRef = database.getReference("SystemTime");
//            timeRef.setValue(ServerValue.TIMESTAMP).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            tourBooking.bookingTime =dataSnapshot.getValue(Long.class);
//                            tourBooking.userId=userId;
//                            DatabaseReference tourStartDateRef = database.getReference("tour_start_date");
//                            tourStartDateRef.child(tourBooking.tourStartDateId).addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    TourStartDate tourStartDate = dataSnapshot.getValue(TourStartDate.class);
//                                    Integer addPeople = tourBooking.adult + tourBooking.children;//+tourBooking.baby; em bé có tính là 1 người không?
//
//                                    if (tourStartDate.peopleBooking + addPeople > tourStartDate.maxPeople) {
//                                        listener.onBookTourFailure(new Exception("Số người đăng ký đã đủ"));
//                                        return;
//                                    }
//                                    DatabaseReference tourBookingRef = database.getReference("tour_booking");
//                                    String key = tourBooking.tourStartDateId + "+" + userId;
//                                    tourBookingRef.child(key).setValue(tourBooking).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            listener.onBookTourSuccess();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            listener.onBookTourFailure(e);
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    listener.onBookTourFailure(new Exception( databaseError.toString()));
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            listener.onBookTourFailure(new Exception(databaseError.toString()));
//
//                        }
//                    });
//                }
//            });
//        }
//        else
//            listener.onBookTourFailure(new Exception("Bạn chưa đăng nhập"));
//
//    }
//    //code hoi ngu
//    private void updatePeopleBooking(String tourId, final TourBooking tourBooking, final OnBookTourFinishedListener listener){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference tourStartRef = database.getReference("tour_start_date")
//                .child(tourId).child(tourBooking.tourStartDateId);
//        tourStartRef.child("peopleBooking").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Integer peopleBooking = dataSnapshot.getValue(Integer.class);
//                Integer addPeople = tourBooking.adult+tourBooking.children+tourBooking.baby;
//
//                tourStartRef.child("peopleBooking").setValue(peopleBooking + addPeople)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        listener.onBookTourSuccess();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        deleteBooking(tourBooking);
//                        listener.onBookTourFailure(e);
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    void deleteBooking(TourBooking tourBooking){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference tourBookingRef = database.getReference("tour_booking");
//        tourBookingRef.child(tourBooking.tourStartDateId+"+"+tourBooking.bookingTime+"+"+tourBooking.userId).removeValue();
//    }
//}