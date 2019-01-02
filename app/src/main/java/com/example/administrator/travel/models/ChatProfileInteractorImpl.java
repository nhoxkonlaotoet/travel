package com.example.administrator.travel.models;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by Henry
 */

public class ChatProfileInteractorImpl implements ChatProfileInteractor{

    String AuthID;

    @Override
    public void getUserProfileInteractor(final ChatProfileListener listener, SharedPreferences sharedPreferences) {
        AuthID =  sharedPreferences.getString("AuthID","none");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance ().getReference ();
        FirebaseUser user = FirebaseAuth.getInstance ().getCurrentUser ();

        mDatabase.child("users/"+AuthID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mName = dataSnapshot.child("name").getValue(String.class);
                String mSDT = dataSnapshot.child("sdt").getValue(String.class);
                String mSex = dataSnapshot.child("sex").getValue(String.class);
                String mEmail = dataSnapshot.child("mail").getValue(String.class);
                String mURL = dataSnapshot.child("urlAvatar").getValue(String.class);

                listener.onResultGetUserProfileListener(mName,mSDT,mSex,mEmail,mURL);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
