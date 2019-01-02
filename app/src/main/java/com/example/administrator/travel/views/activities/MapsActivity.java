package com.example.administrator.travel.views.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.presenters.MapPresenter;
import com.example.administrator.travel.views.MapView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.administrator.travel.R;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener, MapView {
    GoogleApiClient googleApiClient;
   // Button btnPrevious, btnNow, btnNext;
    Location myLocation;
    LatLng destination, myClick;
    private GoogleMap mMap;
    MapPresenter presenter;
    List<PolylineOptions> polylinePaths = new ArrayList<>();
    ProgressDialog progressDialog;
    List<Schedule> lstSchedule = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();


        presenter = new MapPresenter(this);
        presenter.onViewLoad(bundle);
    }

    void setMapClick(){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
             presenter.onMapClick(latLng);
            }
        });
    }
    public void btnPrevious_Click(View btn){
        presenter.onBtnPreviousClicked();
    }
    public void btnNow_Click(View btn){
        presenter.onBtnNowClicked();
    }
    public void btnNext_Click(View btn){
        presenter.onBtnNextClicked();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        setMapClick();

    }

    @Override
    public void connectGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void stopGoogleApiClient() {
        if (googleApiClient != null)
            if (googleApiClient.isConnected())
                googleApiClient.disconnect();
    }

    @Override
    public void startLocationServices() {
        LocationRequest request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);

    }

    @Override
    public void showNearbys(List<Nearby> lstNearby) {

    }

    @Override
    public void addActivities() {

    }

    @Override
    public void moveCamera(LatLng location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    @Override
    public void addMyClickLocation(LatLng location) {
        myClick=location;
        mapRefesh();
        closeActivityForResult(location);
    }

    @Override
    public void addSchedule(List<Schedule> lstSchedule) {
       this.lstSchedule=lstSchedule;
        mapRefesh();
    }

    @Override
    public void addDirection(List<Route> lstRoute) {
        for (Route route : lstRoute) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(getResources().getColor(R.color.colorGreen)).
                    width(10);

            for (int i = 0; i < route.points.size(); i++) {
                polylineOptions.add(route.points.get(i));
            }
            polylinePaths.add(polylineOptions);
        }
        mapRefesh();
    }

    @Override
    public void addDestination(LatLng des) {
        destination=des;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        presenter.onViewConnectedGoogleApiClient();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        presenter.onViewLocationChanged(location);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        Log.e("onLocationChanged: ", location + "");
    }

    @Override
    public void mapRefesh() {
        mMap.clear();
        for (PolylineOptions polylineOptions : polylinePaths) {
            Polyline line = mMap.addPolyline(polylineOptions);
            line.setClickable(true);
        }
        if(myClick!=null)
        {
            mMap.addMarker(new MarkerOptions().position(myClick));
        }

        if(destination!=null)
            mMap.addMarker(new MarkerOptions()
                    .position(destination));
        for(Schedule schedule : lstSchedule){
            mMap.addMarker(new MarkerOptions()
                    .position(schedule.latLng.getLatLng())
                    .title(schedule.content));
            Log.e( "mapRefesh: ", schedule.toString());
        }

    }

    @Override
    public void showDialog() {
        progressDialog = ProgressDialog.show(this, "",
                "Vui lòng chờ lấy vị trí", true);
    }

    @Override
    public void closeDialog() {
        if(progressDialog.isShowing())
            progressDialog.hide();
    }

    @Override
    public void closeActivityForResult(LatLng location) {
        Intent intent = getIntent();
        intent.putExtra("chosenLocation",location.latitude+","+location.longitude);
        setResult(RESULT_OK, intent);
        finish();
    }
}