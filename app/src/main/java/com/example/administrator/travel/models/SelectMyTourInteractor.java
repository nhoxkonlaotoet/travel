package com.example.administrator.travel.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 21/12/2018.
 */

public class SelectMyTourInteractor {

    public void getMyTours(String userId, final OnGetMyToursFinishedListener listener)
    {
        Log.e( "getmytour ", userId);
        final List<TourStartDate> lstTourStartDate = new ArrayList<>();
        final List<Tour> lstTour = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference participantsRef =database.getReference("participants");
        participantsRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Long n=dataSnapshot.getChildrenCount();

                final Integer[] i = {0};
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    DatabaseReference tourStartRef = database.getReference("tour_start_date");
                    final String tourStartId = dataSnapshot1.child("tourStartId").getValue(String.class);

                    tourStartRef.child(tourStartId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            TourStartDate tourStartDate = dataSnapshot.getValue(TourStartDate.class);
                            tourStartDate.id=dataSnapshot.getKey();
                            lstTourStartDate.add(tourStartDate);

                            DatabaseReference tourRef = database.getReference("tours");
                            tourRef.child(tourStartDate.tourId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Tour tour = dataSnapshot.getValue(Tour.class);
                                    Log.e("model my tours: ", tour.toString());

                                    tour.id=dataSnapshot.getKey();
                                    if(!lstTour.contains(tour))
                                        lstTour.add(tour);
                                    i[0]++;
                                    if(i[0]==n+0) {
                                        Collections.reverse(lstTourStartDate);
                                        listener.onGetMyToursSuccess(lstTour,lstTourStartDate);
                                        return;
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    listener.onGetMyToursFailure(databaseError.toException());
                                    return;
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onGetMyToursFailure(databaseError.toException());
                            return;
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetMyToursFailure(databaseError.toException());
            }
        });
    }

    public void joinTour(final String tourStartId, Context context, final OnGetMyToursFinishedListener listener)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences prefs =context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        final String userId = prefs.getString("AuthID","");
        final boolean isShared = prefs.getBoolean("shareLocation" + userId, false);

        DatabaseReference timeRef = database.getReference("SystemTime");
        timeRef.setValue(ServerValue.TIMESTAMP);
        timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Long time = dataSnapshot.getValue(Long.class);
                DatabaseReference tourstartRef = database.getReference("tour_start_date").child(tourStartId);
                tourstartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e( "onDataChange: ", dataSnapshot.getValue()+"");
                        if(dataSnapshot.getValue()!=null && !dataSnapshot.getValue().equals("")) {
                            Participant participant = new Participant(userId, tourStartId, isShared,
                                    new MyLatLng(0, 0), time, 0L);
                            DatabaseReference participantsRef = database.getReference("participants");
                            participantsRef.child(tourStartId + "+" + userId).setValue(participant).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    listener.onJoinTourSuccess();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    listener.onJoinTourFailure(e);
                                }
                            });
                        }
                        else
                            listener.onJoinTourFailure(new Exception("1"));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onJoinTourFailure(databaseError.toException());

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onJoinTourFailure(databaseError.toException());
            }
        });

    }
    public void rememberTour(final String tourStartId, final Context context, final OnGetMyToursFinishedListener listener)
    {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference tourstartRef = database.getReference("tour_start_date").child(tourStartId).child("tourId");
            tourstartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String tourId = dataSnapshot.getValue(String.class);
                    try {

                        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                        String userId = prefs.getString("AuthID", "");
                        if(userId.equals("none"))
                            listener.onRememberTourFailure(new Exception("Bạn chưa đăng nhập"));
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("participatingTour"+userId, tourId);
                        editor.putString("participatingTourStart"+userId, tourStartId);
                        editor.apply();
                        listener.onRememberTourSuccess(tourId, tourStartId);
                    }
                    catch (Exception ex){
                        listener.onRememberTourFailure(ex);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onRememberTourFailure(databaseError.toException());
                }
            });

    }
    public void checkJoiningTour(Context context, final OnGetMyToursFinishedListener listener)
    {
            SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
            String userId = prefs.getString("AuthID", "");
            if (userId.equals("none"))
                listener.isJoiningTourFalse();
            final String tourId = prefs.getString("participatingTour" + userId, "");
            final String tourStartId = prefs.getString("participatingTourStart" + userId, "");
            Log.e("checkJoiningTour: ", tourId + " " + tourStartId);
            if (!tourId.equals("") && !tourStartId.equals("")) {
               listener.isJoiningTourTrue(tourId,tourStartId);
            }
            else
                listener.isJoiningTourFalse();
    }
    public void checkCompany(Context context, final OnGetMyToursFinishedListener listener)
    {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID","");
        if(!userId.equals("none"))
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("companies").child(userId);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null)
                        listener.onCheckCompanySuccess(true);
                    else
                        listener.onCheckCompanySuccess(false);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onCheckCompanyFailure(databaseError.toException());
                }
            });
        }
        else
            listener.onCheckCompanyFailure(new Exception("Bạn chưa đăng nhập"));
    }
    public void getCompanyTour(String companyId, final OnGetMyToursFinishedListener listener)
    {
        final List<TourStartDate> lstTourStartDate = new ArrayList<>();
        final List<Tour> lstTour = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference("tours");
        Query tourQuery = tourRef.orderByChild("owner").equalTo(companyId);
        tourQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long i=0;
                final long n=dataSnapshot.getChildrenCount();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    i++;
                    Tour tour = ds.getValue(Tour.class);
                    tour.id = ds.getKey();
                    lstTour.add(tour);
                    DatabaseReference tourStartRef = database.getReference("tour_start_date");
                    Query tourStartQuery = tourStartRef.orderByChild("tourId").equalTo(tour.id);
                    final long finalI = i;
                    tourStartQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds1: dataSnapshot.getChildren())
                            {
                                TourStartDate tourStartDate = ds1.getValue(TourStartDate.class);
                                tourStartDate.id=ds1.getKey();
                                lstTourStartDate.add(tourStartDate);
                            }
                            if(finalI ==n)
                                listener.onGetMyToursSuccess(lstTour,lstTourStartDate);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onGetMyToursFailure(databaseError.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetMyToursFailure(databaseError.toException());
            }
        });
    }
}
