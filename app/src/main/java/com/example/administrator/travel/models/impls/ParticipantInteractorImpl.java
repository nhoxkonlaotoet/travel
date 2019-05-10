package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.OnGetShareLocationListenter;
import com.example.administrator.travel.models.OnSetShareLocationFinishedListener;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    public void rememberTour(String userId, String tourStartId, String tourId, Context context) {
        if (context == null)
            return;
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("participatingTour" + userId, tourId);
        editor.putString("participatingTourStart" + userId, tourStartId);
        editor.apply();
    }

    @Override
    public boolean isJoiningTour(String userId, Context context) {
        if (context == null)
            return false;
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String tourId = prefs.getString("participatingTour" + userId, "");
        String tourStartId = prefs.getString("participatingTourStart" + userId, "");
        if (!tourId.equals("") && !tourStartId.equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void checkJoiningTour(String userId, final Listener.OnCheckJoiningTourFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference participantsRef = database.getReference("participants");
        Query findJoingingTourQuery = participantsRef.orderByChild("userId").equalTo(userId);
        findJoingingTourQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Participant participant = new Participant();
                participant.joinedTime = 0L;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    long joinTime = snapshot.child("joinedTime").getValue(Long.class);
                    if (joinTime > participant.joinedTime) {
                        participant = snapshot.getValue(Participant.class);
                    }
                }
                if (participant.joinedTime != 0L) {
                    DatabaseReference tourStartRef = database.getReference("tour_start_date");
                    tourStartRef.child(participant.tourStartId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean tourFinished = dataSnapshot.child("finished").getValue(Boolean.class);
                            if (!tourFinished) {
                                TourStartDate tourStartDate = dataSnapshot.getValue(TourStartDate.class);
                                tourStartDate.id = dataSnapshot.getKey();
                                listener.onCheckJoiningTourTrue(tourStartDate.tourId, tourStartDate.id);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onCheckJoingTourFail(databaseError.toException());

                        }
                    });
                } else listener.onCheckJoiningTourFalse();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCheckJoingTourFail(databaseError.toException());
            }
        });
    }

    @Override
    public String getJoiningTourId(String userId, Context context) {
        if (context == null)
            return "";
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String tourId = prefs.getString("participatingTour" + userId, "");
        return tourId;

    }

    @Override
    public String getJoiningTourStartId(String userId, Context context) {
        if (context == null)
            return "";
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String tourStartId = prefs.getString("participatingTourStart" + userId, "");
        return tourStartId;

    }

    @Override
    public void updateLocation(String tourStartId, String userId, MyLatLng location) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference participantRef = database.getReference(PARTICIPANTS_REF);
        participantRef.child(tourStartId + "+" + userId).child("location").setValue(location);
        participantRef.child(tourStartId + "+" + userId).child("lastTimeShareLocation").setValue(ServerValue.TIMESTAMP);
    }

    @Override
    public void setTourFinishStream(String tourStartId, final Context context, final Listener.OnFinishTourFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourFinishRef = database.getReference(TOUR_START_DATE_REF)
                .child(tourStartId).child("finished");
        tourFinishRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isFinished = dataSnapshot.getValue(Boolean.class);
                if (isFinished) {
                    listener.onTourFinished();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void removeparticipatingTour(String userId, Context context) {
        if (context == null)
            return;
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("participatingTourStart" + userId, "");
        editor.putString("participatingTour" + userId, "");
        editor.apply();
    }

    @Override
    public boolean isShareLocation(String userId, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        boolean result = prefs.getBoolean("shareLocation" + userId, false);
        return result;
    }

    @Override
    public void checkShareLoction(String userId, String tourStartId, final Listener.OnCheckShareLocationFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference shareLocationRef = database.getReference("participants")
                .child(tourStartId + "+" + userId).child("shareLocation");
        shareLocationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    boolean shareLocation = dataSnapshot.getValue(Boolean.class);
                    listener.onCheckLocationSuccess(shareLocation);
                } else
                    listener.onCheckLocationFail(new NullPointerException());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCheckLocationFail(databaseError.toException());
            }
        });

    }

    @Override
    public void setShareLocation(final String userId, String tourStartId, final boolean shareLocation, final Context context, final Listener.OnSetShareLocationFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference shareLocationRef = database.getReference("participants")
                .child(tourStartId + "+" + userId).child("shareLocation");
        shareLocationRef.setValue(shareLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("shareLocation" + userId, shareLocation);
                editor.apply();
                listener.onSetShareLocationSuccess(shareLocation);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onSetShareLocationFail(e);
            }
        });
    }

    @Override
    public void getMyToursId(String userId, final Listener.OnGetMyTourIdsFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference participantsRef = database.getReference("participants");
        participantsRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> listMyTourIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("tourStartId").getValue().toString();
                    listMyTourIds.add(id);
                }
                listener.onGetMyTourIdsSuccess(listMyTourIds);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetMyTourIdsFail(databaseError.toException());
            }
        });
    }

    @Override
    public void setStreamPeopleLocationChange(String tourStartId, final Listener.OnGetPeopleLocationFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference participantRef = database.getReference(PARTICIPANTS_REF);
        Query query = participantRef.orderByChild("tourStartId").equalTo(tourStartId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Participant> participantList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Participant participant = ds.getValue(Participant.class);
                    if (participant.shareLocation)
                        participantList.add(participant);
                }
                listener.onGetPeopleLocationSuccess(participantList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetPeopleLocationFailure(databaseError.toException());

            }
        });
    }
}
