package com.example.administrator.travel.views.activities;

import android.Manifest;
import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.models.PlaceDetailTask;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.PlacePhoto;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.MapPresenter;
import com.example.administrator.travel.presenters.impls.MapPresenterImpl;
import com.example.administrator.travel.views.MapView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.administrator.travel.R;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements MapView, OnMapReadyCallback {
    private MapPresenter presenter;
    private GoogleMap mMap;
    private ProgressDialog progressDialog;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView imgvNavHeader;
    private TextView txtNavHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapping();
        setImgvNavHeaderClick();
        setAutocompletePlaceSlectec();
        presenter = new MapPresenterImpl(this);
        Bundle bundle = getIntent().getExtras();
        presenter.onViewCreated(bundle);


    }

    private void mapping() {
        txtNavHeader = findViewById(R.id.txtNavHeader);
        imgvNavHeader = findViewById(R.id.imgvNavHeader);
        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);
        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutocompleteFragment.setHint("Tìm kiếm");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setAutocompletePlaceSlectec() {
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

    private void setMapClick() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                presenter.onMapClicked(latLng);
            }
        });
    }

    private void setMarkerClick() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                presenter.onMarkerClicked(marker);
                return false;
            }
        });
    }

    private void setImgvNavHeaderClick() {
        imgvNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((BitmapDrawable) imgvNavHeader.getDrawable()).getBitmap() != null)
                    presenter.onImageViewNavHeaderClicked(((BitmapDrawable) imgvNavHeader.getDrawable()).getBitmap());
            }
        });
    }

    private void setMapPOIClick() {
        mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest pointOfInterest) {
                Toast.makeText(MapsActivity.this, pointOfInterest.placeId, Toast.LENGTH_SHORT).show();
            }
        });
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
        setMarkerClick();
        setMapPOIClick();
    }


    @Override
    public void moveCamera(LatLng location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }


    @Override
    public void mapRefesh() {
        mMap.clear();
        presenter.onMapRefreshed();
    }

    @Override
    public void addMarker(MarkerOptions markerOptions) {
        mMap.addMarker(markerOptions);
    }

    @Override
    public void addMarker(MarkerOptions markerOptions, Object tag) {
        mMap.addMarker(markerOptions).setTag(tag);
    }

    @Override
    public void addPolyline(PolylineOptions polylineOptions) {
        Polyline line = mMap.addPolyline(polylineOptions);
        line.setClickable(true);
    }

    @Override
    public void showDialog() {
        progressDialog = ProgressDialog.show(this, "",
                "Vui lòng chờ lấy vị trí", true);
    }

    @Override
    public void closeDialog() {
        if (progressDialog.isShowing())
            progressDialog.hide();
    }

    @Override
    public void openDrawerLayout() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawerLayout() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void setNavigationHeaderPhoto(int pos, Bitmap photo) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        imgvNavHeader.setImageBitmap(photo);
        imgvNavHeader.setTag(pos);
    }

    @Override
    public void setNavigationHeaderPhoto(String photoUrl) {
        Log.e("setNavigationPhoto: ", photoUrl);

        Picasso.with(this).load(photoUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                imgvNavHeader.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    @Override
    public void clearNavigationHeaderPhoto() {
        imgvNavHeader.setImageBitmap(null);
    }

    @Override
    public void setNavigationHeaderTitle(String title) {
        txtNavHeader.setText(title);
    }

    @Override
    public void clearNavigationHeaderTitile() {

    }

    @Override
    public void closeActivityForResult(LatLng location) {
        Intent intent = getIntent();
        intent.putExtra("chosenLocation", location.latitude + "," + location.longitude);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public Context getContext() {
        return this;
    }


}