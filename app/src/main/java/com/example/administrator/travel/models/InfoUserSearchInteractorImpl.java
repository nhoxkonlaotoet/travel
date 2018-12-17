package com.example.administrator.travel.models;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Henry on 12/15/2018.
 */

public class InfoUserSearchInteractorImpl implements InfoUserSearchInteractor {
    String AuthID;
    String UserIDAddFr;
    int friend;
    String userGroup;
    @Override
    public void TestFriendedInteractor(final InfoUserSearchListener listener, SharedPreferences sharedPreferences) {
        AuthID =  sharedPreferences.getString("AuthID","none");
        UserIDAddFr = sharedPreferences.getString("UserIDAddFr","none");
        friend = 0;
        if(!AuthID.equals(UserIDAddFr)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            databaseReference.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        int flag = 0;
                        //kiểm tra key có chứa AuthID k
                        for (DataSnapshot snapshotPre : snapshot.getChildren()) {
                            if (snapshotPre.getKey().equals(AuthID) && snapshotPre.getValue(Boolean.class)) {
                                flag = 1;
                            } else if (snapshotPre.getKey().equals(AuthID) && !snapshotPre.getValue(Boolean.class)) {
                                flag = 2;
                            }
                        }//end for pre
                        if (flag == 1 && friend == 0) {
                            for (DataSnapshot snapshotPre : snapshot.getChildren()) {
                                if (snapshotPre.getKey().equals(UserIDAddFr) && snapshotPre.getValue(Boolean.class)) {
                                    friend = 3;//Bạn bè
                                    userGroup = snapshot.getKey();
                                    listener.onResultTestFriendedListener(friend);
                                } else if (snapshotPre.getKey().equals(UserIDAddFr) && !snapshotPre.getValue(Boolean.class)) {
//                                Đã gửi kết bạn
                                    friend = 1;
                                    userGroup = snapshot.getKey();
                                    listener.onResultTestFriendedListener(friend);
                                }
                            }//end for pre
                        } else if (flag == 2 && friend == 0) {
                            for (DataSnapshot snapshotPre : snapshot.getChildren()) {
                                if (snapshotPre.getKey().equals(UserIDAddFr) && snapshotPre.getValue(Boolean.class)) {
//                                Chấp nhận kết bạn
                                    friend = 2;
                                    userGroup = snapshot.getKey();
                                    listener.onResultTestFriendedListener(friend);
                                }
                            }//end for pre
                        }
                    }//end for
                    listener.onResultTestFriendedListener(friend);
                }//end onDataChange

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onResultTestFriendedListener(friend);
                }
            });
        }else {//AuthID.equals(UserIDAddFr
            listener.onResultTestFriendedListener(3);// default friend
        }
    }//end TestFriendedInteractor

    @Override
    public void RequestAddFriendInteractor() {
        if(!AuthID.equals(UserIDAddFr)) {
            java.util.Map<String, java.lang.Boolean> map = new java.util.HashMap<>();
            map.put(AuthID, true);
            map.put(UserIDAddFr, false);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            userGroup = databaseReference.child("members").push().getKey();
            FirebaseDatabase.getInstance().getReference().child("members/" + userGroup).setValue(map);
        }
    }

    @Override
    public void CancelAddFriendInteractor() {
        if(!AuthID.equals(UserIDAddFr)) {
            FirebaseDatabase.getInstance().getReference().child("members/").child(userGroup).removeValue();
        }
    }

    @Override
    public void AcceptAddFriendInteractor() {
        if(!AuthID.equals(UserIDAddFr)) {
            FirebaseDatabase.getInstance().getReference().child("members/" + userGroup).child(AuthID).setValue(true);
        }
    }

    @Override
    public void UnFriendInteractor() {

        Log.d("user id","value "+UserIDAddFr + " Auth id "+AuthID+" start");
        if(!AuthID.equals(UserIDAddFr)) {
            FirebaseDatabase.getInstance().getReference().child("members/" + userGroup).child(AuthID).setValue(false);
        }

        Log.d("user id","value "+UserIDAddFr + " Auth id "+AuthID+" finish");
    }
}
