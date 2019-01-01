package com.example.administrator.travel.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.UserInformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/01/2019.
 */

public class ActivityInteractor {
    public void getActivities(final String tourStartId, final OnGetActivityFinishedListener listener){
        final List<Activity> lstActivity = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference timeRef = database.getReference("SystemTime");
        timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Long currentTime = dataSnapshot.getValue(Long.class);
                DatabaseReference activitiesRef = database.getReference("activities");
                activitiesRef.child(tourStartId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            Log.e( "get activity: ",ds.getValue()+"" );
                            Activity activity = ds.getValue(Activity.class);
                            activity.id=ds.getKey();
                            lstActivity.add(activity);
                        }
                        listener.onGetActivitiesSuccess(lstActivity,currentTime);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onGetActivitiesFailure(databaseError.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getImage(String tourStartId, final String activityId, final int intdex, final OnGetActivityFinishedListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference("activities").child(tourStartId).child(activityId).child(intdex+".png");
        long HALF_MEGABYTE = 1024 * 512;
        ref.getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    listener.onGetActivityImagesSuccess(bmp, intdex, activityId);
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onGetActivityImageFailure(e);
            }
        });
    }

}
