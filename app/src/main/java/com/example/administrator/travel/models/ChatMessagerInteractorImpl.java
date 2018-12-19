package com.example.administrator.travel.models;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.travel.models.entities.chat.Chats;
import com.example.administrator.travel.models.entities.chat.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Henry on 12/18/2018.
 */

public class ChatMessagerInteractorImpl implements ChatMessagerInteractor {
    String AuthID;
    String KeyGroup;

    @Override
    public void getMessageIntractor(final ChatMessagerListener listener, SharedPreferences sharedPreferences) {
        AuthID = sharedPreferences.getString("AuthID","none");
        KeyGroup = sharedPreferences.getString("KeyGroupChat","none");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("messagers/"+KeyGroup).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message chat = dataSnapshot.getValue(Message.class);
                listener.getMessageListener(chat);
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
                Log.d("ErrorCMInteractorImpl","Unable to get message: " + databaseError.getMessage());

            }
        });
    }//end getMessage

    @Override
    public void SendMessageInteractor(SharedPreferences sharedPreferences,String message) {
        AuthID = sharedPreferences.getString("AuthID","none");
        KeyGroup = sharedPreferences.getString("KeyGroupChat","none");

        long time  = new java.util.Date().getTime();

        Message addMess = new Message(AuthID,message,time,"text");

        Chats lastChat = new Chats(message,time);

        FirebaseDatabase.getInstance().getReference().child("messagers/"+KeyGroup)
                .push().setValue(addMess);

        FirebaseDatabase.getInstance().getReference().child("the_last_chat/"+KeyGroup)
                .setValue(lastChat);
    }
}
