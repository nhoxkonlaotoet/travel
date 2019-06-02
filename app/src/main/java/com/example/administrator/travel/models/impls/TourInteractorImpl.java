package com.example.administrator.travel.models.impls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 4/1/2019.
 */

public class TourInteractorImpl implements TourInteractor {
    private final static String TOURS_REF = "tours";


    @Override
    public void getTour(String tourId, final Listener.OnGetTourFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference(TOURS_REF).child(tourId);
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tour tour = dataSnapshot.getValue(Tour.class);
                listener.onGetTourSuccess(tour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetTourFail(databaseError.toException());
            }
        });


    }

    @Override
    public void getMyOwnedTours(String companyId, final Listener.OnGetMyOwnedTourIdsFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference participantsRef = database.getReference(TOURS_REF);
        Query getOwnedTourQuery = participantsRef.orderByChild("owner").equalTo(companyId);
        getOwnedTourQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Tour> listMyOwnedTour = new ArrayList<>();
                for (DataSnapshot Snapshot1 : dataSnapshot.getChildren()) {
                    Tour tour = Snapshot1.getValue(Tour.class);
                    tour.id = Snapshot1.getKey();
                    listMyOwnedTour.add(tour);
                }
                listener.onGetMyOwnedToursSuccess(listMyOwnedTour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetMyOwnedToursFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getMyTourInfo(final int pos, String tourStartId, final Listener.OnGetMyTourInfoFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourStartRef = database.getReference("tour_start_date");
        tourStartRef.child(tourStartId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final TourStartDate tourStartDate = dataSnapshot.getValue(TourStartDate.class);
                tourStartDate.id = dataSnapshot.getKey();
                DatabaseReference tourRef = database.getReference("tours");
                tourRef.child(tourStartDate.tourId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Tour tour = dataSnapshot.getValue(Tour.class);
                        listener.onGetMyTourInfoSuccess(pos, tour, tourStartDate);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onGetMyTourInfoFail(databaseError.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetMyTourInfoFail(databaseError.toException());
            }
        });
    }


    @Override
    public void getTourImage(final int pos, final String tourId, final Listener.OnGetTourImageFinishedListener listener) {

        final long HALF_MEGABYTE = 1024 * 512;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference tourRef = storage.getReference().child("tours/" + tourId + "/");
        tourRef.child(pos + ".jpg").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetTourImageSuccess(pos, tourId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


    @Override
    public void getAboutToDepartTours(final Listener.OnGetAboutToDepartToursFinishedListener listener) {
        Date date = new Date();
        final long now = date.getTime();
        final long nextThreeDays = now + 86400000 * 3;
        final long nextFiveDays = now + 86400000 * 5;

        final List<Tour> tourList = new ArrayList<>();
        final HashMap<String, TourStartDate> tourStartMap = new HashMap<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourStartDateRef = database.getReference("tour_start_date");
        Query query = tourStartDateRef.orderByChild("startDate").startAt(nextThreeDays).endAt(nextFiveDays);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TourStartDate tourStartDate = snapshot.getValue(TourStartDate.class);
                    tourStartDate.id = snapshot.getKey();
                    if (tourStartMap.get(tourStartDate.tourId) == null
                            || tourStartMap.get(tourStartDate.tourId).startDate > tourStartDate.startDate) {
                        tourStartMap.remove(tourStartDate.tourId);
                        tourStartMap.put(tourStartDate.tourId, tourStartDate);
                    }
                }
                final int[] count = {0};
                for (final String tourId : tourStartMap.keySet()) {
                    DatabaseReference tourRef = database.getReference("tours");
                    tourRef.child(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Tour tour = dataSnapshot.getValue(Tour.class);
                            tour.id = tourId;
                            tourList.add(tour);
                            count[0]++;
                            if (count[0] == tourStartMap.size())
                                listener.onGetAboutToDepartToursSuccess(tourList, tourStartMap);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void getLikedTours(final Listener.OnGetLikedToursFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference toursRef = database.getReference(TOURS_REF);
        toursRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Tour> tourList = new LinkedList<>();
                HashMap<String, Double> ratingMap = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    boolean added = false;
                    Tour tour = snapshot.getValue(Tour.class);
                    tour.id = snapshot.getKey();

                    if (tour.ratings != null) {
                        Double tourRating = 0D;
                        for (Double rating : tour.ratings.values())
                            tourRating += rating;
                        tourRating /= tour.ratings.size();
                        if(tourRating<4D) {
                            added = true;
                        }
                        else {
                            ratingMap.put(tour.id, tourRating);
                            for (int i = 0; i < tourList.size(); i++)
                                if (ratingMap.get(tourList.get(i).id) != null
                                        && tourRating > ratingMap.get(tourList.get(i).id)) {
                                    tourList.add(i, tour);
                                    added = true;
                                    break;
                                }
                        }
                    }
                    if (!added) {
                        tourList.add(tour);
                    }

                }
                listener.onGetLikedToursSuccess(tourList, ratingMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetLikedToursFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getToursByDestination(String cityId, final Listener.OnGetToursFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference toursRef = database.getReference(TOURS_REF);
        Query findTourByDestinationQuery = toursRef.orderByChild("destination/"+cityId).equalTo(true);
        findTourByDestinationQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Tour> tourList = new ArrayList<>();
                HashMap<String,Double> ratingMap = new HashMap<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Tour tour = snapshot.getValue(Tour.class);
                    tour.id=snapshot.getKey();
                    Double tourRating = 0D;
                    for (Double rating : tour.ratings.values())
                        tourRating += rating;
                    ratingMap.put(tour.id,tourRating);
                    tourList.add(tour);
                }
                listener.onGetToursSuccess(tourList,ratingMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetToursFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getToursByOwner(String companyId, final Listener.OnGetToursFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference toursRef = database.getReference(TOURS_REF);
        Query findOwnTourQuery = toursRef.orderByChild("owner").equalTo(companyId);
        findOwnTourQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Tour> tourList = new ArrayList<>();
                HashMap<String,Double> ratingMap = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tour tour = snapshot.getValue(Tour.class);
                    tour.id = snapshot.getKey();
                    Double tourRating = 0D;
                    for (Double rating : tour.ratings.values())
                        tourRating += rating;
                    ratingMap.put(tour.id,tourRating);
                    tourList.add(tour);
                }
                listener.onGetToursSuccess(tourList,ratingMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetToursFail(databaseError.toException());
            }
        });
    }

}

