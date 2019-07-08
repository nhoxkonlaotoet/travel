package com.example.administrator.travel;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Admin on 4/14/2019.
 */

public class LocationService extends Service implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        LocationListener {
    GoogleApiClient googleApiClient;
    ParticipantInteractor participantInteractor;
    UserInteractor userInteractor;
    String tourStartId, userId;
    IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        participantInteractor = new ParticipantInteractorImpl();
        userInteractor = new UserInteractorImpl();

        userId = userInteractor.getUserId();
        tourStartId = participantInteractor.getJoiningTourStartId(userId, this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service start", Toast.LENGTH_SHORT).show();
        Log.e("onStartCommand: ", " service started");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public LocationService getServerInstance() {
            return LocationService.this;
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service destroy", Toast.LENGTH_SHORT).show();

        Log.e("onDestroy: ", "service destroy");
        //  myPlayer.stop();
        if (googleApiClient != null)
            if (googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                googleApiClient.disconnect();
            }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationServices();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        MyLatLng myLatLng = new MyLatLng(location.getLatitude(), location.getLongitude());
        LocationObservable.getInstance().updateLocation(location);
        if (!tourStartId.equals("") && !userId.equals("none") && participantInteractor.isShareLocation(userId, this))
            participantInteractor.updateLocation(tourStartId, userId, myLatLng);
        Toast.makeText(this, "location changed", Toast.LENGTH_LONG).show();
    }

    public void startLocationServices() {
        if (googleApiClient.isConnected()) {
            LocationRequest request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
        }
    }

    public MyLatLng getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (googleApiClient.isConnected()) {
               try {
                   Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                   MyLatLng result = new MyLatLng(location.getLatitude(), location.getLongitude());
                   return result;
               }catch (Exception ex){}
               return null;
            }
        }
        return null;
    }

    public static String className() {
        return "com.example.administrator.travel.LocationService";
    }
}
