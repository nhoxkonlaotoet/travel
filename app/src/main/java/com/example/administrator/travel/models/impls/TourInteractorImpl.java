package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.example.administrator.travel.models.OnGetTourImagesFinishedListener;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Participant;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Admin on 4/1/2019.
 */

public class TourInteractorImpl implements TourInteractor {
    private final static String TOURS_REF = "tours";

    @Override
    public void getTours(final Listener.OnGetToursFinishedListener listener) {
        final List<Tour> listTour = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference(TOURS_REF);

        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot Snapshot1 : dataSnapshot.getChildren()) {
                    Tour tour = Snapshot1.getValue(Tour.class);
                    tour.id = Snapshot1.getKey();
                    listTour.add(tour);
                }

                listener.onGetToursSuccess(listTour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetToursFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getTours(String origin, Listener.OnGetToursFinishedListener listener) {
        getTours(origin, "", listener);
    }

    @Override
    public void getTours(final String origin, final String destination, final Listener.OnGetToursFinishedListener listener) {
        final List<Tour> listTour = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference(TOURS_REF);
        Query query;
        if ((!origin.equals("")) && destination.equals(""))// chi chon den xuat phat
            query = tourRef.orderByChild("origin").equalTo(origin);
        else // chi chon diem den hoac chon ca 2
            query = tourRef.orderByChild("destination/" + destination).equalTo(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0)
                    listener.onGetToursSuccess(listTour);
                for (DataSnapshot Snapshot1 : dataSnapshot.getChildren()) {
                    Tour tour = Snapshot1.getValue(Tour.class);
                    tour.id = Snapshot1.getKey();
                    if ((!origin.equals("")) && (!destination.equals("")))// neu chon ca 2
                    {
                        if (tour.origin.equals(origin))
                            listTour.add(tour);
                    } else
                        listTour.add(tour);
                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference toursRef = storage.getReference().child("tours/");
                // xử lý củ chuối
                final Boolean[] downloadedFlags = new Boolean[listTour.size()];
                for (int i = 0; i < listTour.size(); i++)
                    downloadedFlags[i] = false;

                final long HALF_MEGABYTE = 1024 * 512;
                for (int i = 0; i < listTour.size(); i++) {
                    final int I = i;
                    toursRef.child(listTour.get(i).id + "/0.jpg").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            listTour.get(I).image = bmp;
                            downloadedFlags[I] = true;
                            for (int j = 0; j < listTour.size(); j++)
                                if (downloadedFlags[j] == false)
                                    return;
                            listener.onGetToursSuccess(listTour);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            listener.onGetToursFail(exception);
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
    public void getFirstImage(final int pos, final String tourId, final Listener.OnGetFirstImageFinishedListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference toursRef = storage.getReference().child(TOURS_REF + "/");
        final long HALF_MEGABYTE =1024 * 1024;
        toursRef.child(tourId + "/0.jpg").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetFirstImageSuccess(pos, tourId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onGetFirstImageFail(exception);
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
    public void getTourImages(final String tourId, final Listener.OnGetTourImagesFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbtourRef = database.getReference().child("tours").child(tourId).child("numberofImages");
        dbtourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Integer number = dataSnapshot.getValue(Integer.class) - 1;
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference tourRef = storage.getReference().child("tours/" + tourId + "/");
                final Bitmap[] bitmaps = new Bitmap[number];
                final long ONE_MEGABYTE = 1024 * 1024;

                final Boolean[] downloadedFlags = new Boolean[number];
                for (int i = 0; i < number; i++)
                    downloadedFlags[i] = false;

                for (int i = 0; i < number; i++) {
                    final Integer j = i;
                    tourRef.child(i + ".jpg").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps[j] = bmp;
                            downloadedFlags[j] = true;
                            for (int k = 0; k < number; k++)
                                if (downloadedFlags[k] == false)
                                    return;
                            listener.onGetTourImagesSuccess(bitmaps);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            listener.onGetTourImagesFail(exception);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetTourImagesFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getTourImage(final int pos, String tourId, final Listener.OnGetTourImageFinishedListener listener) {

        final long HALF_MEGABYTE = 1024 * 512 ;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference tourRef = storage.getReference().child("tours/" + tourId + "/");
        tourRef.child(pos + ".jpg").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetTourImageSuccess(pos, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                listener.onGetTourImageFail(exception);
            }
        });
    }

    @Override
    public void getTourImageTitle(final int pos, String tourId, final Listener.OnGetTourImageTitleFinishedListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference forestRef = storageRef.child("tours/"+tourId+"/"+pos+".jpg");
        forestRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String title=storageMetadata.getCustomMetadata("title");
                listener.onGetTourImageTitleSuccess(pos,title);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onGetTourImageTitleFail(exception);
            }
        });
    }

}

