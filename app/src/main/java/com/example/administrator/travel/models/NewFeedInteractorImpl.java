package com.example.administrator.travel.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.entities.Tour;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class NewFeedInteractorImpl implements NewFeedInteractor {
    @Override
    public void getTour(final OnGetNewFeedItemFinishedListener listener) {
        final List<Tour> listTour= new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference("tours");
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                            listener.onSuccess(listTour);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }

                if(listTour.size()!=0) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return;
    }
}
