package com.example.administrator.travel.views.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;

import com.example.administrator.travel.adapter.Pager;
import com.example.administrator.travel.adapter.SectionsPagerAdapter;
import com.example.administrator.travel.adapter.SlideTourImageAdapter;
import com.example.administrator.travel.models.OnTransmitMyLocationFinishedListener;
import com.google.android.gms.location.LocationListener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.TourPresenter;
import com.example.administrator.travel.views.TourView;
import com.example.administrator.travel.views.fragments.ContactFragment;
import com.example.administrator.travel.views.fragments.MapFragment;
import com.example.administrator.travel.views.fragments.NearbyFragment;
import com.example.administrator.travel.views.fragments.TourDetailFragment;
import com.example.administrator.travel.views.fragments.TourStartFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class TourActivity extends AppCompatActivity implements TourView,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {
    GoogleApiClient googleApiClient;
    public Location myLocation;
    Toolbar toolbar;
    ViewPager vpTourImage, vpContainer;
    TabLayout tablayoutTour;
    SectionsPagerAdapter mSectionsPagerAdapter;
    TourPresenter presenter;
    OnTransmitMyLocationFinishedListener listener;
    Location mylocation;
    Boolean isMytour=false, isCompany;
    Pager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e( "onCreate: ", "tour activity");
        setContentView(R.layout.activity_tour);

        toolbar = findViewById(R.id.toolbar);
        vpTourImage = findViewById(R.id.vpTourImage);
        toolbar.bringToFront();
        tablayoutTour = findViewById(R.id.tablayoutTour);
        vpContainer = findViewById(R.id.vpTabContainer);

        presenter = new TourPresenter(this);
        Bundle bundle = getIntent().getExtras();
        Log.e("onCreate: ", ""+bundle.getBoolean("isCompany"));
        isCompany = bundle.getBoolean("isCompany");
        presenter.onViewLoad(bundle.getString("tourId"), bundle.getBoolean("mytour"));

        // load n fragment bên cạnh
        vpContainer.setOffscreenPageLimit(1);
        //   tablayoutTour.setupWithViewPager(vpTabContainer);

        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        isMytour=bundle.getBoolean("mytour");


        int tabCount;
        tabCount=4;
        if(!isMytour)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable (R.color.transparent));
            actionBar.setDisplayShowTitleEnabled(false);

        }
        else {
            if(isCompany)
                tabCount = 3;
        }

        pager = new Pager(getSupportFragmentManager(), tabCount,isMytour,isCompany);
        vpContainer.setAdapter(pager);

        vpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayoutTour.setScrollPosition(position, 0, true);

                tablayoutTour.setSelected(true);

                Log.e("onPageSelected: ", position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tablayoutTour.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpContainer.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment.getClass().equals(NearbyFragment.class)) {

            listener = (OnTransmitMyLocationFinishedListener) fragment;
            presenter.onViewAttachFragment(myLocation);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void addTab(Boolean isMyTour) {

        if (isMyTour) {
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Chi tiết"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Hoạt động"));
            if(isCompany)
                tablayoutTour.addTab(tablayoutTour.newTab().setText("Duyệt mua"));
            else
                tablayoutTour.addTab(tablayoutTour.newTab().setText("Gần đây"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Đánh giá"));

            vpTourImage.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.toolbar);
            tablayoutTour.setLayoutParams(params);

        } else {

            tablayoutTour.addTab(tablayoutTour.newTab().setText("Chi tiết"));
            if(!isCompany)
                tablayoutTour.addTab(tablayoutTour.newTab().setText("Đặt tour"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Liên hệ"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Đánh giá"));
        }
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
    public void transmitLocationToFragment(Location location) {
     //   Log.e("act_tour: location",location+" "+(listener==null) );
        if(listener!=null)
            listener.onDataChanged(location);
    }

    @Override
    public void showImages(Bitmap[] images, Integer numberofImages) {

        SlideTourImageAdapter adapter = new SlideTourImageAdapter(images, numberofImages, this);
        vpTourImage.setAdapter(adapter);
    }

    @Override
    public void closebyTourFinished() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation=location;
        presenter.onLocationChanged(location);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
       presenter.onGoogleApiClientConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        presenter.onGoogleApiClientConnectFailed(connectionResult);
    }

    @Override
    protected void onStop() {
        presenter.onViewStop();
        super.onStop();
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
    public void notifyConnectFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Kết nối thất bại "+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopGoogleApiClient() {
        if (googleApiClient != null)
            if (googleApiClient.isConnected())
                googleApiClient.disconnect();
    }






}
