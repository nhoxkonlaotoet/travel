package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.SelectMyTourAdapter;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.SelectMyTourPresenter;
import com.example.administrator.travel.views.SelectMyTourView;
import com.example.administrator.travel.views.activities.HomeActivity;
import com.example.administrator.travel.views.activities.LoginActivity;
import com.example.administrator.travel.views.activities.ScanQRActivity;
import com.example.administrator.travel.views.activities.TourActivity;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMyTourFragment extends Fragment implements SelectMyTourView {
    ListView lstvSelectMyTour;
    LinearLayout btnScan;
    public static final Integer SCAN_QR_CODE = 100;
    SelectMyTourPresenter presenter;
    RelativeLayout layoutLogin,layoutMyTours;
    Button btnLogin;
    public SelectMyTourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_my_tour, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping();
        presenter = new SelectMyTourPresenter(this);
        setListviewItemClick();
        setBtnScanClick();
        setBtnLoginClick();
        presenter.onViewLoad();
    }



    @Override
    public void loadMyTours(){
    }
    public void mapping(){
        btnScan = getActivity().findViewById(R.id.btnScan);
        layoutLogin = getActivity().findViewById(R.id.layoutLogin);
        layoutMyTours = getActivity().findViewById(R.id.layoutMyTours);
        lstvSelectMyTour = getActivity().findViewById(R.id.lstvSelectMyTour);
        btnLogin = getActivity().findViewById(R.id.btnLogin);
    }
    public void setListviewItemClick(){
        lstvSelectMyTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onItemTourClicked(view);
            }
        });
    }
    public void setBtnScanClick()
    {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnScanQRClick();
            }
        });
    }
    public void setBtnLoginClick()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnLoginClicked();
            }
        });
    }
    @Override
    public void gotoCamera()
    {
        Intent intent = new Intent(getActivity(), ScanQRActivity.class);
        startActivityForResult(intent,SCAN_QR_CODE );
    }

    @Override
    public void gotoTourActivity(String tourId, String tourStartId){
        Intent intent = new Intent(getActivity(), TourActivity.class);
        intent.putExtra("mytour",true);
        intent.putExtra("tourId",tourId);
        intent.putExtra("tourStartId",tourStartId);
        startActivity(intent);
        Log.e( "gotoTourActivity: ", "_________________________");
    }

    @Override
    public void showLayoutLogin() {
        layoutLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutLogin() {
        layoutLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideLayoutMyTours() {
        layoutMyTours.setVisibility(View.GONE);
    }

    @Override
    public void showLayoutMyTours() {
        layoutMyTours.setVisibility(View.VISIBLE);
    }

    @Override
    public void gotoLoginActivity() {
        startActivity((new Intent(getActivity(), LoginActivity.class)));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SCAN_QR_CODE && resultCode ==getActivity().RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                //Toast.makeText(getActivity(), barcode.displayValue, Toast.LENGTH_LONG).show();

                presenter.onViewScanned(barcode.displayValue);
            }
        }
    }


    @Override
    public void showMyTours(List<Tour> lstTour, List<TourStartDate> lstTourStart) {
        lstvSelectMyTour.setAdapter(new SelectMyTourAdapter(getActivity(),lstTour,lstTourStart));
    }



}
