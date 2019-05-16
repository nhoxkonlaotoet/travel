package com.example.administrator.travel.views.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.NewsFeedAdapter;
import com.example.administrator.travel.adapter.SelectMyTourAdapter;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.SelectMyTourPresenter;
import com.example.administrator.travel.presenters.impls.SelectMyTourPresenterImpl;
import com.example.administrator.travel.views.bases.SelectMyTourView;
import com.example.administrator.travel.views.activities.LoginActivity;
import com.example.administrator.travel.views.activities.ScanQRActivity;
import com.example.administrator.travel.views.activities.TourActivity;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMyTourFragment extends Fragment implements SelectMyTourView,
        SelectMyTourAdapter.ItemClickListener, NewsFeedAdapter.ItemClickListener {
    RecyclerView recyclerViewMyTour;
    LinearLayout btnScan;
    public static final Integer SCAN_QR_CODE = 100, TOUR_CODE = 102, LOGIN_CODE = 103;
    SelectMyTourPresenter presenter;
    RelativeLayout layoutLogin, layoutMyTours;
    Button btnLogin;
    ProgressDialog waitDialog;
    SelectMyTourAdapter selectMyTourAdapter;
    NewsFeedAdapter newsFeedAdapter;

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
        setBtnScanClick();
        setBtnLoginClick();
        presenter.onViewCreated();
    }

    public void mapping() {
        btnScan = getActivity().findViewById(R.id.btnScan);
        layoutLogin = getActivity().findViewById(R.id.layoutLogin);
        layoutMyTours = getActivity().findViewById(R.id.layoutMyTours);
        recyclerViewMyTour = getActivity().findViewById(R.id.recyclerviewMyTour);
        btnLogin = getActivity().findViewById(R.id.btnLogin);
    }

    public void setBtnScanClick() {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnScanQRClick();
            }
        });
    }

    public void setBtnLoginClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnLoginClicked();
            }
        });
    }

    @Override
    public void gotoCamera() {
        Intent intent = new Intent(getActivity(), ScanQRActivity.class);
        startActivityForResult(intent, SCAN_QR_CODE);
    }

    @Override
    public void gotoTourActivity(String tourId, String tourStartId, String owner, boolean isCompany) {
        Intent intent = new Intent(getActivity(), TourActivity.class);
        intent.putExtra("mytour", true);
        intent.putExtra("tourId", tourId);
        if (tourStartId != null)
            intent.putExtra("tourStartId", tourStartId);
        if (owner != null)
            intent.putExtra("owner", owner);
        startActivityForResult(intent, TOUR_CODE);
        Log.e("gotoTourActivity: ", "_________________________");
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
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra("requestCode", LOGIN_CODE);
        startActivityForResult(intent, LOGIN_CODE);
    }

    @Override
    public void notifyInvalidScanString() {
        Toast.makeText(getActivity(), "Mã QR không đúng, vui lòng quét lại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyJoinTourFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_QR_CODE && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                //Toast.makeText(getActivity(), barcode.displayValue, Toast.LENGTH_LONG).show();

                presenter.onViewResult(barcode.displayValue);
            }
        }
        if (requestCode == LOGIN_CODE) {
            presenter.onViewCreated();
        }
        if (requestCode == TOUR_CODE && resultCode == getActivity().RESULT_OK) {
            presenter.onViewCreated();
        }
    }


    @Override
    public void showMyTours(int n) {
        selectMyTourAdapter = new SelectMyTourAdapter(getActivity(), n);
        selectMyTourAdapter.setClickListener(this);
        recyclerViewMyTour.setAdapter(selectMyTourAdapter);
    }

    @Override
    public void updateTourInfo(int pos, Tour tour, TourStartDate tourStartDate) {
        selectMyTourAdapter.updateTourInfo(pos, tour, tourStartDate);
    }

    @Override
    public void showWaitDialog() {
        waitDialog = ProgressDialog.show(this.getContext(), "Đang xử lý", "Vui lòng đợi...");
    }

    @Override
    public void dismissWaitDialog() {
        if (waitDialog.isShowing())
            waitDialog.dismiss();
    }

    @Override
    public void showMyTours(List<Tour> tourList) {
        newsFeedAdapter = new NewsFeedAdapter(getActivity(), tourList);
        newsFeedAdapter.setClickListener(this);
        recyclerViewMyTour.setAdapter(newsFeedAdapter);

    }

    @Override
    public void updateTourImage(int pos, String tourId, Bitmap image) {
        newsFeedAdapter.updateImage(pos, tourId, image);
    }

    @Override
    public void notifyGetMyTourFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }


    @Override
    public void onTourItemClick(View view, String tourId, String ownerId) {
        presenter.onMyOwnedTourItemClicked(tourId, ownerId);
    }

    @Override
    public void onMyTourItemClick(View view, String tourId, String tourStartId) {
        presenter.onMyTourItemClicked(tourId, tourStartId);
    }
}
