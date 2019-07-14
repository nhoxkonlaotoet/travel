package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.asyncTasks.PlaceDetailTask;
import com.example.administrator.travel.models.asyncTasks.NearbyTask;
import com.example.administrator.travel.models.bases.PlaceInteractor;
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

public class PlaceInteractorImpl implements PlaceInteractor {

    @Override
    public void getNearby(String type, LatLng location, String apiKey, final Listener.OnGetNearbyFinishedListener listener) {
        String url = getNearbyUrl(location.latitude,location.longitude,type,null,apiKey);
        new NearbyTask(listener).execute(url);

    }

    @Override
    public void getNearby(String type, LatLng location, String pageToken, String apiKey, final Listener.OnGetNearbyFinishedListener listener) {
        String url = getNearbyUrl(location.latitude,location.longitude,type,pageToken,apiKey);
        new NearbyTask(listener).execute(url);
    }

    private String getNearbyUrl(Double latitude, Double longitude, String type, String pagetoken, String apiKey) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlaceUrl.append("&rankby=distance");
        googlePlaceUrl.append("&type=").append(type);
        googlePlaceUrl.append("&language=").append("vi");

        googlePlaceUrl.append("&key=").append(apiKey);
        if (pagetoken!=null && !pagetoken.equals("")) {
            googlePlaceUrl.append("&hasNextPage=true");
            googlePlaceUrl.append("&nextPage()=true");
            googlePlaceUrl.append("&pagetoken=").append(pagetoken);
        }
        return googlePlaceUrl.toString();
    }

    @Override
    public void getPlaceType(final Listener.OnGetPlaceTypeFinishedListener listener) {
        final List<NearbyType> lstPlacetype = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference placetypeRef = database.getReference("placetypes");
        placetypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsPlacetype : dataSnapshot.getChildren()) {
                    NearbyType placetype = dsPlacetype.getValue(NearbyType.class);
                    placetype.id = dsPlacetype.getKey();
                    lstPlacetype.add(placetype);
                }
                listener.onGetPlaceTypeSuccess(lstPlacetype);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetPlaceTypeFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getPlaceDetail(String placeId, String apiKey, final Listener.OnGetPlaceDetailFinishedListener listener) {
       new PlaceDetailTask(listener).execute(getPlaceDetailUrl(placeId,apiKey));

    }
    private String getPlaceDetailUrl(String placeId,  String apiKey) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        googlePlaceUrl.append("placeid=");
        googlePlaceUrl.append(placeId);
//        googlePlaceUrl.append("&fields=");
//        for (int i = 0; i < fileds.length; i++) {
//            googlePlaceUrl.append(fileds[i]);
//            if (i != fileds.length - 1)
//                googlePlaceUrl.append(",");
//        }
        googlePlaceUrl.append("&key=");
        googlePlaceUrl.append(apiKey);
        return googlePlaceUrl.toString();
    }

    public void likePlace(String placeId, String userId, Boolean like) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference placesRef = database.getReference("places");
        placesRef.child(placeId).child("likes").child(userId).setValue(like);
    }
}
