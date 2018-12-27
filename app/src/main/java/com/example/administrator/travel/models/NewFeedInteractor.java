package com.example.administrator.travel.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewFeedInteractor  {
    public void getTours(final OnGetNewFeedItemFinishedListener listener) {
        final List<Tour> listTour= new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference("tours");
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0)
                    listener.onGetItemsSuccess(listTour);
                for (DataSnapshot Snapshot1 : dataSnapshot.getChildren()) {
                    Tour tour = Snapshot1.getValue(Tour.class);
                    tour.id = Snapshot1.getKey();
                    listTour.add(tour);
                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference toursRef = storage.getReference().child("tours/");
                // xử lý củ chuối
                final Boolean[] downloadedFlags = new Boolean[listTour.size()];
                for(int i=0;i<listTour.size();i++)
                    downloadedFlags[i]=false;

                final long HALF_MEGABYTE = 1024 * 512;
                for(int i=0;i<listTour.size();i++) {
                    final int I = i;
                    toursRef.child(listTour.get(i).id+"/0.jpg").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            listTour.get(I).image=bmp;
                            //Log.e("onSuccess: ", listTour.get(I).image.getByteCount()+ "");
                            downloadedFlags[I]=true;
                            for(int j=0;j<listTour.size();j++)
                                if(downloadedFlags[j]==false)
                                    return;
                            listener.onGetItemsSuccess(listTour);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            listener.onGetCitesFailure(exception);
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetItemsFailure(databaseError.toException());
            }
        });

        return;
    }
    // co 3 truong hop: origin!="" & destination="", orgin="" & destination!="", orgin="" & destination=""
    public void getTours(final String origin, final String destination, final OnGetNewFeedItemFinishedListener listener){
        Log.e( "gettours: ", "origin="+origin+", destination="+destination);
        final List<Tour> listTour= new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference("tours");
        Query query;
        if((!origin.equals("")) && destination.equals(""))// chi chon den xuat phat
            query = tourRef.orderByChild("origin").equalTo(origin);
        else // chi chon diem den hoac chon ca 2
            query = tourRef.orderByChild("destination/"+destination).equalTo(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0)
                    listener.onGetItemsSuccess(listTour);
                for (DataSnapshot Snapshot1 : dataSnapshot.getChildren()) {
                    Tour tour = Snapshot1.getValue(Tour.class);
                    tour.id = Snapshot1.getKey();
                    if((!origin.equals("")) && (!destination.equals("")))// neu chon ca 2
                    {
                        if (tour.origin.equals(origin))
                            listTour.add(tour);
                    }
                    else
                        listTour.add(tour);
                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference toursRef = storage.getReference().child("tours/");
                // xử lý củ chuối
                final Boolean[] downloadedFlags = new Boolean[listTour.size()];
                for(int i=0;i<listTour.size();i++)
                    downloadedFlags[i]=false;

                final long HALF_MEGABYTE = 1024 * 512;
                for(int i=0;i<listTour.size();i++) {
                    final int I = i;
                    toursRef.child(listTour.get(i).id+"/0.jpg").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            listTour.get(I).image=bmp;
                            //Log.e("onSuccess: ", listTour.get(I).image.getByteCount()+ "");
                            downloadedFlags[I]=true;
                            for(int j=0;j<listTour.size();j++)
                                if(downloadedFlags[j]==false)
                                    return;
                            listener.onGetItemsSuccess(listTour);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            listener.onGetCitesFailure(exception);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public void getCities(final OnGetNewFeedItemFinishedListener listener){
        final List<City> lstCity = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference citiesRef = database.getReference("cities");
        citiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    City city = ds.getValue(City.class);
                    city.id=ds.getKey();
                    lstCity.add(city);
                }
                 listener.onGetCitiesSuccess(lstCity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetCitesFailure(databaseError.toException());
            }
        });
    }
}
