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
        final List<Activity> lstActivity = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference activitiesRef = database.getReference(ACTIVITIES_REF);
        activitiesRef.child(tourStartId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.e("get activity: ", ds.getValue() + "");
                    Activity activity = ds.getValue(Activity.class);
                    activity.id = ds.getKey();
                    lstActivity.add(activity);
                }
                listener.onGetActivitiesSuccess(lstActivity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetActivitiesFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void postActivity(final String tourStartId, String userId, Activity activity, final List<Bitmap> listImage, final Listener.OnPostActivityFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(ACTIVITIES_REF);
        final String key = ref.push().getKey();
        ref.child(tourStartId).child(key).setValue(activity.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageref = storage.getReference().child("activities/")
                        .child(tourStartId).child(key);
                if (listImage == null || listImage.size() == 0) {
                    listener.onPostActivitySuccess();
                    return;
                }
                final boolean[] flags = new boolean[listImage.size()];
                for (int i = 0; i < listImage.size(); i++) {
                    UploadTask uploadTask = storageref.child(i + ".png").putBytes(bitmapToBytes(listImage.get(i)));
                    final int finalI = i;
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            flags[finalI] = true;
                            for (int j = 0; j < listImage.size(); j++)
                                if (!flags[j])
                                    return;
                            listener.onPostActivitySuccess();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onPostActivityFail(e);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onPostActivityFail(e);
            }
        });
    }

    @Override
    public void getActivitiyPhoto(int pos, String tourStartId, final String activityId, final Listener.OnGetActivityPhotosFinishedListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference("activities").child(tourStartId).child(activityId).child(pos + ".png");
        long HALF_MEGABYTE = 1024 * 512;
        ref.getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetActivityPhotosSuccess(activityId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }


    @Override
    public byte[] bitmapToBytes(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
}

