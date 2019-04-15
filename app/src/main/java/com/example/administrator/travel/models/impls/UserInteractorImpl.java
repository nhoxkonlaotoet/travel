package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.listeners.Listener;
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
 * Created by Admin on 4/3/2019.
 */

public class UserInteractorImpl implements UserInteractor {
    @Override
    public String getUserId(Context context) {
        if(context==null)
            return "none";
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID","");
        return userId;
    }
    @Override
    public boolean isCompany(Context context) {
        if(context==null)
            return Boolean.parseBoolean(null);
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID","");

        return false;
    }

    @Override
    public void getUserName(final String userId, final Listener.OnGetUserNameFinishedListener listener, final int pos) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");
        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                listener.onGetUserNameSuccess(userId,name,pos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getUserAvatar(final String userId, final Listener.OnGetUserAvatarFinishedListener listener, final int pos) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference avatarRef = storage.getReference();
        final long HALF_MEGABYTE = 1024 * 512;
        avatarRef.child("default_image.png").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetUserAvatarFinishedListener(userId, bmp,pos);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
