package com.example.administrator.travel.models;

import android.util.Log;

import com.example.administrator.travel.OnGetPeopleLocationFinishedListener;
import com.example.administrator.travel.models.entities.Participant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 03/01/2019.
 */

public class MapInteractor {
    public void getPeopleLocation(String tourStartId, final OnGetPeopleLocationFinishedListener listener){
        final List<Participant> lstParticipant = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("participants");
        Query query = ref.orderByChild("tourStartId").equalTo(tourStartId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Participant participant = ds.getValue(Participant.class);
                    if(participant.shareLocation)
                        lstParticipant.add(participant);
                }

                listener.onGetPeopleLocationSuccess(lstParticipant);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetPeopleLocationFailure(databaseError.toException());
            }
        });
    }
}
