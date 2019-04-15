package com.example.administrator.travel.views.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.SelectMyTourAdapter;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.SelectMyTourPresenter;
import com.example.administrator.travel.presenters.impls.SelectMyTourPresenterImpl;
import com.example.administrator.travel.views.SelectMyTourView;
import com.example.administrator.travel.views.activities.LoginActivity;
import com.example.administrator.travel.views.activities.ScanQRActivity;
import com.example.administrator.travel.views.activities.TourActivity;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMyTourFragment extends Fragment implements SelectMyTourView {
    ListView lstvSelectMyTour;
    LinearLayout btnScan;
    public static final Integer SCAN_QR_CODE = 100,TOUR_CODE=102, LOGIN_CODE=103;
    SelectMyTourPresenter presenter;
    RelativeLayout layoutLogin,layoutMyTours;
    Button btnLogin;
    ProgressDialog waitDialog;
    SelectMyTourAdapter adapter;
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
        presenter = new SelectMyTourPresenterImpl(this);
        setListviewItemClick();
        setBtnScanClick();
        setBtnLoginClick();
        presenter.onViewCreated();
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
    public void gotoTourActivity(String tourId, String tourStartId, boolean isCompany){
        Intent intent = new Intent(getActivity(), TourActivity.class);
        intent.putExtra("mytour",true);
        intent.putExtra("tourId",tourId);
        intent.putExtra("tourStartId",tourStartId);
        intent.putExtra("isCompany",isCompany);
        startActivityForResult(intent,TOUR_CODE);
        Log.e( "gotoTourActivity: ", "_________________________");
    }

    @Override
    public void showBtnScan() {
        btnScan.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnScan() {
        btnScan.setVisibility(View.INVISIBLE);
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
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        intent.putExtra("requestCode", LOGIN_CODE);
        startActivityForResult(intent,LOGIN_CODE);
    }

    @Override
    public void notifyInvalidScanString() {
        Toast.makeText(getActivity(), "Mã QR không đúng, vui lòng quét lại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyJoinTourFailure(Exception ex) {
        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SCAN_QR_CODE && resultCode ==getActivity().RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                //Toast.makeText(getActivity(), barcode.displayValue, Toast.LENGTH_LONG).show();

                presenter.onViewResult(barcode.displayValue);
            }
        }
        if(requestCode == LOGIN_CODE){
            presenter.onViewCreated();
        }
        if(requestCode== TOUR_CODE && resultCode == getActivity().RESULT_OK){
            presenter.onViewCreated();
        }
    }


    @Override
    public void showMyTours(int n) {
        adapter = new SelectMyTourAdapter(getActivity(),n);
        lstvSelectMyTour.setAdapter(adapter);
    }

    @Override
    public void updateTourInfo(int pos, Tour tour, TourStartDate tourStartDate) {
        adapter.updateTourInfo(pos,tour,tourStartDate);
    }

    @Override
    public void showWaitDialog(){
        waitDialog = ProgressDialog.show(this.getContext(), "Đang xử lý", "Vui lòng đợi...");
    }

    @Override
    public void dismissWaitDialog(){
        if(waitDialog.isShowing())
            waitDialog.dismiss();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
