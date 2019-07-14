package com.example.administrator.travel.models.impls;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.FriendInteractor;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendInteractorImpl implements FriendInteractor {
    private final static String FRIENDS_REF="friends";
    @Override
    public void sendAddFriendRequest(String myId, String friendId, final Listener.OnSendAddFriendRequestFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference friendsRef = database.getReference(FRIENDS_REF);
        String key = friendsRef.push().getKey();
        HashMap friendMap = new HashMap();
        friendMap.put(myId,true);
        friendMap.put(friendId,false);
        friendsRef.child(key).setValue(friendMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onSendAddFriendRequestSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onSendAddFriendRequestFail(e);
            }
        });
    }

    @Override
    public void acceptFriendRequest(String addFriendRequestId, String myId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference friendsRef = database.getReference(FRIENDS_REF);
        friendsRef.child(addFriendRequestId).child(myId).setValue(true);
    }

    @Override
    public void removeFriend(String addFriendRequestId, String myId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference friendsRef = database.getReference(FRIENDS_REF);
        friendsRef.child(addFriendRequestId).removeValue();
    }

    @Override
    public void getFriends(final String userId, final Listener.OnGetFriendsFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference friendsRef = database.getReference(FRIENDS_REF);
        Query findMyFriendQuery = friendsRef.orderByChild(userId).equalTo(true);
        findMyFriendQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> friendIdList =new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot userSnapshot: snapshot.getChildren()){
                        if(!userSnapshot.getKey().equals(userId) && (Boolean)userSnapshot.getValue()){
                            friendIdList.add(userSnapshot.getKey());
                        }
                    }
                }
                listener.onGetFriendsSuccess(friendIdList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetFriendsFail(databaseError.toException());
            }
        });
    }
}
