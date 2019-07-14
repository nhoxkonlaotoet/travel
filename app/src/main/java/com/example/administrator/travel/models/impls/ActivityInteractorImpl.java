package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.ActivityInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/15/2019.
 */

public class ActivityInteractorImpl implements ActivityInteractor {
    final static String ACTIVITIES_REF = "activities";

    @Override
    public void getActivities(final String tourStartId, final Listener.OnGetActivitiesFinishedListener listener) {
        final List<Activity> activityArrayList = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference activitiesRef = database.getReference(ACTIVITIES_REF);
        activitiesRef.orderByChild("tourStartId").equalTo(tourStartId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Activity activity = ds.getValue(Activity.class);
                    activity.id = ds.getKey();
                    activityArrayList.add(activity);
                }
                listener.onGetActivitiesSuccess(activityArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetActivitiesFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void postActivity(Activity activity, final Listener.OnPostActivityFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference activitiesRef = database.getReference(ACTIVITIES_REF);
        final String key = activitiesRef.push().getKey();
        activitiesRef.child(key).setValue(activity.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onPostActivitySuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onPostActivityFail(e);
            }
        });
    }
    @Override
    public void likeActivity(String activityId, String userId, Boolean like){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference activitiesRef = database.getReference(ACTIVITIES_REF);
        activitiesRef.child(activityId).child("likes").child(userId).setValue(like);
    }
}

