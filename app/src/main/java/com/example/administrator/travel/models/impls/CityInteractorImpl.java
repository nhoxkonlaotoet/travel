package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.bases.CityInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/1/2019.
 */

public class CityInteractorImpl implements CityInteractor {
    @Override
    public void getCities(final Listener.OnGetCitiesFinishedListener listener) {
        final List<City> lstCity = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference citiesRef = database.getReference("cities");
        citiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    City city = ds.getValue(City.class);
                    city.id = ds.getKey();
                    lstCity.add(city);
                }
                listener.onGetCitiesSuccess(lstCity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetCitiesFail(databaseError.toException());
            }
        });
    }
}
