package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.util.Log;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.NearbyTask;
import com.example.administrator.travel.models.bases.NearbyInteractor;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/25/2019.
 */

public class NearbyInteractorImpl implements NearbyInteractor{
    @Override
    public void getNearby(String type, LatLng location, String pageToken, String apiKey, Listener.OnGetNearbyFinishedListener listener) {
        NearbyTask nearbyTask = new NearbyTask(listener);
        String url = nearbyTask.getUrl(apiKey,location,type,pageToken);
        Log.e("getNearby: ", url);
        nearbyTask.execute(url);
    }

    @Override
    public void getPlaceType(final Listener.OnGetPlaceTypeFinishedListener listener) {
        final List<NearbyType> lstPlacetype = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference placetypeRef = database.getReference("placetypes");
        placetypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsPlacetype : dataSnapshot.getChildren())
                {
                    NearbyType placetype = dsPlacetype.getValue(NearbyType.class);
                    placetype.id = dsPlacetype.getKey();
                    lstPlacetype.add(placetype);
                    listener.onGetPlaceTypeSuccess(lstPlacetype);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetPlaceTypeFail(databaseError.toException());
            }
        });
    }
}
