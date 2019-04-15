package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Admin on 4/14/2019.
 */

public class ParticipantInteractorImpl implements ParticipantInteractor {
    final static String PARTICIPANTS_REF = "participants";
    final static String TOUR_START_DATE_REF = "tour_start_date";

    @Override
    public void joinTour(final String userId, final boolean isShareLocation, final String tourStartId,
                         final Listener.OnJoinTourFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourStartRef = database.getReference(TOUR_START_DATE_REF).child(tourStartId);
        tourStartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TourStartDate tourStartDate = dataSnapshot.getValue(TourStartDate.class);
                if (tourStartDate.finished) {
                    listener.onJoinTourFail(new Exception("Tour đã kết thúc"));
                    return;
                }
                final String tourId = tourStartDate.tourId;
                if (tourId.equals("") || tourId == null) {
                    listener.onJoinTourFail(new Exception("1"));
                    return;
                }
                DatabaseReference participantsRef = database.getReference(PARTICIPANTS_REF);
                Participant participant = new Participant(userId, tourStartId, isShareLocation,
                        new MyLatLng(0, 0));
                participantsRef.child(tourStartId + "+" + userId).setValue(participant.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onJoinTourSuccess(tourId, tourStartId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onJoinTourFail(e);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void rememberTour(String tourStartId, String tourId, Context context, Listener.OnRememberTourFinishedListener listener) {
        if (context == null)
            return;
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID", "");
        if (userId.equals("none"))
            listener.onRememberTourFail(new Exception("Bạn chưa đăng nhập"));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("participatingTour" + userId, tourId);
        editor.putString("participatingTourStart" + userId, tourStartId);
        editor.apply();
    }

    @Override
    public boolean isJoiningTour(Context context) {
        if (context == null)
            return false;
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID", "");
        if (userId.equals("none"))
            return false;
        String tourId = prefs.getString("participatingTour" + userId, "");
        String tourStartId = prefs.getString("participatingTourStart" + userId, "");
        if (!tourId.equals("") && !tourStartId.equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public String getJoiningTourId(Context context) {
        if (context == null)
            return "";
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID", "");
        if (userId.equals("none"))
            return "";
        String tourId = prefs.getString("participatingTour" + userId, "");
        if (!tourId.equals("")) {
            return tourId;
        }
        return "";
    }

    @Override
    public String getJoiningTourStartId(Context context) {
        if (context == null)
            return "";
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID", "");
        if (userId.equals("none"))
            return "";
        String tourStartId = prefs.getString("participatingTourStart" + userId, "");
        if (!tourStartId.equals("")) {
            return tourStartId;
        }
        return "";
    }

    @Override
    public void updateLocation(String tourStartId, String userId, MyLatLng location) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference participantRef = database.getReference(PARTICIPANTS_REF);
        participantRef.child(tourStartId + "+" + userId).child("latLng").setValue(location);
    }

    @Override
    public void setTourFinishStream(String tourStartId, final String userId, final Context context, final Listener.OnFinishTourFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourFinishRef = database.getReference(TOUR_START_DATE_REF)
                .child(tourStartId).child("finished");
        tourFinishRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean isFinished = dataSnapshot.getValue(Boolean.class);
                if (isFinished) {
                    removeTour(userId, context);
                    listener.onTourFinished();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void removeTour(String userId, Context context) {
        if (context == null)
            return;
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("participatingTourStart" + userId, "");
        editor.putString("participatingTour" + userId, "");
        editor.apply();
    }
}
