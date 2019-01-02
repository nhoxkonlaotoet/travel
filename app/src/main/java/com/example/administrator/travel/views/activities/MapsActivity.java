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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.presenters.MapPresenter;
import com.example.administrator.travel.views.MapView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
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
    Button btnPrevious, btnNow, btnNext;
    RelativeLayout btnChooseMyLocation;
    Location myLocation;
    LatLng destination, myClick;
    private GoogleMap mMap;
    MapPresenter presenter;
    Place placeResult;
    List<PolylineOptions> polylinePaths = new ArrayList<>();
    ProgressDialog progressDialog;
    List<Schedule> lstSchedule = new ArrayList<>();
    List<Participant> lstParticipant =new ArrayList<>();
    PlaceAutocompleteFragment placeAutocompleteFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btnChooseMyLocation=findViewById(R.id.btnChooseMyLocation);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnNow = findViewById(R.id.btnNow);
        placeAutocompleteFragment =
                (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutocompleteFragment.setHint("Tìm kiếm");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();

        setAutocompletePlaceSlectec();
        presenter = new MapPresenter(this);
        presenter.onViewLoad(bundle);
    }
    void setAutocompletePlaceSlectec(){
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Toast.makeText(MapsActivity.this, place + "", Toast.LENGTH_SHORT).show();
                presenter.onAutoCompleteSelected(place);
            }

            @Override
            public void onError(Status status) {

            }
        });
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
    public void btnChooseMyLocation_Click(View btn){
        presenter.btnChooseMyLocationClicked();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

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
    public void addPeopleLocations(List<Participant> lstParticipant) {
        this.lstParticipant=lstParticipant;
        mapRefesh();
    }


    @Override
    public void moveCamera(LatLng location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
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
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
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
        if(placeResult!=null)
            mMap.addMarker(new MarkerOptions().position(placeResult.getLatLng())
            .title(placeResult.getAddress().toString()));
        if(destination!=null)
            mMap.addMarker(new MarkerOptions()
                    .position(destination));
        for(Schedule schedule : lstSchedule){
            mMap.addMarker(new MarkerOptions()
                    .position(schedule.latLng.getLatLng())
                    .title(schedule.content));
            Log.e( "mapRefesh: ", schedule.toString());
        }
        for(Participant participant : lstParticipant)
        {
            mMap.addMarker(new MarkerOptions()
                    .position(participant.latLng.getLatLng())
                    .title(participant.userId));
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

    @Override
    public void hideControlBtns() {
        btnPrevious.setVisibility(View.INVISIBLE);
        btnNow.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showControlBtns() {
        btnPrevious.setVisibility(View.VISIBLE);
        btnNow.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnNow() {
        btnNow.setVisibility(View.GONE);
    }

    @Override
    public void showBtnNow() {
        btnNow.setVisibility(View.VISIBLE);
    }

    @Override
    public void addPlaceResult(Place place) {
        this.placeResult=place;
        mapRefesh();
    }

    @Override
    public void hideBtnChooseMyLocation() {
        btnChooseMyLocation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBtnChooseMyLocation() {
        btnChooseMyLocation.setVisibility(View.VISIBLE);
    }
}