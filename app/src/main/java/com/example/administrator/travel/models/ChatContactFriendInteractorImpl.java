package com.example.administrator.travel.models;

import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 12/17/2018.
 */

public class ChatContactFriendInteractorImpl implements ChatContactFriendInteractor{

    String AuthID;
    List<String> listKeyFrs = new ArrayList<>();
    List<String> listUserGr = new ArrayList<>();

    @Override
    public void getFriendsInteractor(final ChatContactFriendListener listener, SharedPreferences sharedPreferences) {
//
        AuthID =  sharedPreferences.getString("AuthID","none");
        listKeyFrs.clear();
        listUserGr.clear();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    int flag = 0;
                    for (DataSnapshot snapshotPre : snapshot.getChildren()){
                        if(snapshotPre.getKey().equals(AuthID) && snapshotPre.getValue(Boolean.class))
                            flag = 1;
                    }
                    if (flag == 1){
                        for (DataSnapshot snapshotPre : snapshot.getChildren()){
                            if(!snapshotPre.getKey().equals(AuthID) && snapshotPre.getValue(Boolean.class)){
                                listKeyFrs.add(snapshotPre.getKey());
                                listUserGr.add(snapshot.getKey());
                                listener.onResultGetFriendsListener(listKeyFrs,listUserGr);
                            }
                        }
                    }
                }//end for
            }//end onDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
