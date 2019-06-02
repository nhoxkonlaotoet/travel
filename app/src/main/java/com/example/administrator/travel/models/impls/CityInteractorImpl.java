package com.example.administrator.travel.models.impls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.CityInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/1/2019.
 */

public class CityInteractorImpl implements CityInteractor {
    final static String CITIES_REF = "cities";

    @Override
    public void getCities(final Listener.OnGetCitiesFinishedListener listener) {
        final List<City> cityList = new ArrayList<>();
        final List<Long> numberOfTourList = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference citiesRef = database.getReference(CITIES_REF);
        citiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final long[] n = {0};
                final long citiesCount = dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final City city = ds.getValue(City.class);
                    city.id = ds.getKey();
                    DatabaseReference toursRef = database.getReference("tours");
                    Query query = toursRef.orderByChild("destination/" + city.id).equalTo(true);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Long numberOfTour = dataSnapshot.getChildrenCount();
                            boolean added = false;
                            for (int i = 0; i < cityList.size(); i++) {
                                if (numberOfTour > numberOfTourList.get(i)) {
                                    numberOfTourList.add(i, numberOfTour);
                                    cityList.add(i, city);
                                    added = true;
                                    break;
                                }
                            }
                            if (!added) {
                                cityList.add(city);
                                numberOfTourList.add(numberOfTour);
                            }
                            n[0]++;
                            if (n[0] == citiesCount)
                                listener.onGetCitiesSuccess(cityList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetCitiesFail(databaseError.toException());
            }
        });
    }


    @Override
    public void loadCityPhoto(final String cityId, final Listener.OnLoadCityPhotoFinishedListener listener) {
        final long HUNDRED_KILOBYTE = 1024 * 128;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference tourRef = storage.getReference().child(CITIES_REF + "/");
        tourRef.child(cityId + ".jpg").getBytes(HUNDRED_KILOBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onLoadCityPhotoSuccess(cityId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


}
