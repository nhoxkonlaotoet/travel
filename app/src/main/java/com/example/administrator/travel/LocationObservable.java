package com.example.administrator.travel;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Observable;

/**
 * Created by Admin on 4/15/2019.
 */

public class LocationObservable extends Observable {
    private static LocationObservable instance = new LocationObservable();

    public static LocationObservable getInstance() {
        return instance;
    }
    private LocationObservable() {
    }
    public void updateLocation(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
