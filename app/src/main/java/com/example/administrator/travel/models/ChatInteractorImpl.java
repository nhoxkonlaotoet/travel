package com.example.administrator.travel.models;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.travel.models.entities.chat.Chats;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 12/19/2018.
 */

public class ChatInteractorImpl implements ChatInteractor {
    List<Chats> lstLastChat = new ArrayList<>();
    List<String> lstKeyFriend = new ArrayList<>();
    List<String> lstUserGroup = new ArrayList<>();
    String AuthID;

    @Override
    public void getChatInteractor(final ChatListener listener, SharedPreferences sharedPreferences) {
        AuthID = sharedPreferences.getString("AuthID","none");

        lstLastChat.clear();
        lstKeyFriend.clear();
        lstUserGroup.clear();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("friends").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals(AuthID)){
                        //add list LastChat
                        final String mKeyGroup = dataSnapshot.getKey();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        reference.child("the_last_chat").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                if(dataSnapshot.getKey().equals(mKeyGroup)){
                                    Chats chat = dataSnapshot.getValue(Chats.class);
                                    lstLastChat.add(chat);
                                    lstUserGroup.add(mKeyGroup);
                                    //add Key Friend
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();

                                    reference1.child("friends/"+mKeyGroup).addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            if(!dataSnapshot.getKey().equals(AuthID)){
                                                lstKeyFriend.add(dataSnapshot.getKey());
//
                                                listener.onResultGetChatListener(lstLastChat,lstKeyFriend,lstUserGroup);
                                            }
                                        }
                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }
                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }
                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.d("Error","Unable to get keyGroup Chat: " + databaseError.getMessage());
                                        }
                                    });
                                    //call listener
                                    listener.onResultGetChatListener(lstLastChat,lstKeyFriend,lstUserGroup);
                                }
                            }
                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }
                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }
                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("Error","Unable to get last Chat: " + databaseError.getMessage());
                            }
                        });

                        //add Key Group Chat
//                        lstUserGroup.add(dataSnapshot.getKey());
                        //call listener
//                        listener.onResultGetChatListener(lstLastChat,lstKeyFriend,lstUserGroup);
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error","Unable to get chat: " + databaseError.getMessage());

            }
        });

    }//end getChat
}
