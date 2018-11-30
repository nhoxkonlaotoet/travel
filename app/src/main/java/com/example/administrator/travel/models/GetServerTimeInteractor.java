package com.example.administrator.travel.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.OnGetServerTimeFinishedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 28/11/2018.
 */

public class GetServerTimeInteractor {

    public void getCurrentTime(final OnGetServerTimeFinishedListener listener)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference timeRef = database.getReference().child("SystemTime");
        timeRef.setValue(ServerValue.TIMESTAMP).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long time = dataSnapshot.getValue(Long.class);
                        listener.onGetTimeSuccess(time);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onGetTimeFailure(databaseError.toException());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onGetTimeFailure(e);
            }
        });
    }
}
