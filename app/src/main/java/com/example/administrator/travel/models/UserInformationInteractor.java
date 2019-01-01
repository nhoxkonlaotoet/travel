package com.example.administrator.travel.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

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

/**
 * Created by Administrator on 01/01/2019.
 */

public class UserInformationInteractor {
    public void getUserInfor(String userId, final OnGetUserInforFinishedListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final UserInformation infor = dataSnapshot.getValue(UserInformation.class);
                infor.id=dataSnapshot.getKey();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference avatarRef = storage.getReference();
                final long HALF_MEGABYTE = 1024 * 512;
                avatarRef.child("default_image.png").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        infor.avatar=bmp;
                        listener.onGetUserInforSuccess(infor);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onGetUserInforSuccess(infor);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetUserInforFailure(databaseError.toException());
            }
        });
    }
    public void isTourGuide(final String tourStartId, Context context, final OnGetUserInforFinishedListener listener){
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        final String userId = prefs.getString("AuthID","");
        if(!userId.equals("none")){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("tour_start_date").child(tourStartId).child("tourGuide");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String tourGuideId = dataSnapshot.getValue(String.class);
                    if(userId.equals(tourGuideId))
                        listener.onCheckTourGuideTrue();
                    else
                        listener.onCheckTourGuideFalse(tourGuideId);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onCheckTourGuideFailure(databaseError.toException());
                }
            });
        }

    }
}
