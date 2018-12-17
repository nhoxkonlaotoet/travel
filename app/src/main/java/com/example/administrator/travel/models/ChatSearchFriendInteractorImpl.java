package com.example.administrator.travel.models;

import android.content.SharedPreferences;

import com.example.administrator.travel.models.entities.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 12/14/2018.
 */

public class ChatSearchFriendInteractorImpl implements ChatSearchFriendInteractor {

    DatabaseReference mDatabaseReference;
    String mSearchName ="";

    public List<UserInformation> listUserQuery= new ArrayList<>();
    public List<String> keyUser=new ArrayList<>();

    @Override
    public void getListFriendSearch(final ChatSearchFriendListener listener) {
        listUserQuery.clear();
        keyUser.clear();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

        Query query = mDatabaseReference.orderByChild("name").startAt(mSearchName)
                .endAt(mSearchName + "\uf8ff");//ex: <A>*.
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshotUser : dataSnapshot.getChildren()){
                    UserInformation userInformation = snapshotUser.getValue(UserInformation.class);
                    listUserQuery.add(userInformation);
                    keyUser.add(snapshotUser.getKey());
                }
                listener.getListUserSearchListener(listUserQuery,keyUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setSearchName(String searchName) {
        this.mSearchName = searchName;
    }

    @Override
    public void getFriendInvitation(final ChatSearchFriendListener listener,SharedPreferences sharedPreferences) {
        listUserQuery.clear();
        keyUser.clear();
        final String AuthID = sharedPreferences.getString("AuthID","none");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ListInv : dataSnapshot.getChildren()){
                    int flag = 0;
                    for (DataSnapshot ListInvPre : ListInv.getChildren()){
                        if(ListInvPre.getKey().equals(AuthID) && !ListInvPre.getValue(Boolean.class))
                            flag = 1;
                    }
                    if(flag == 1){
                        for (final DataSnapshot ListInvPre: ListInv.getChildren()){
                            if(!ListInvPre.getKey().equals(AuthID) && ListInvPre.getValue(Boolean.class)){
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                reference.child("users/"+ListInvPre.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        UserInformation mUserInformation = dataSnapshot.getValue(UserInformation.class);
                                        listUserQuery.add(mUserInformation);
                                        keyUser.add(ListInvPre.getKey());
//                                        call listener
                                        listener.getFriendInvitationListener(listUserQuery,keyUser);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
