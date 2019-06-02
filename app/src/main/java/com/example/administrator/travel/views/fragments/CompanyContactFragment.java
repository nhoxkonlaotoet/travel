package com.example.administrator.travel.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.bases.CompanyContactPresenter;
import com.example.administrator.travel.presenters.impls.CompanyContactPresenterImpl;
import com.example.administrator.travel.views.bases.CompanyContactView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyContactFragment extends Fragment implements CompanyContactView {
    final static Integer REQUEST_CODE = 99;
    CompanyContactPresenter presenter;
    TextView txtWebsite, txtPhonenNumber, txtAddress, txtCompanyName;
    RelativeLayout btnWebsite, btnPhoneNumber, btnAddress;
    public CompanyContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mappping();
        presenter=new CompanyContactPresenterImpl(this);
        setBtnWebsiteClick();
        setBtnPhoneNumberClick();
        setBtnAddressClick();
        Bundle bundle =getActivity().getIntent().getExtras();
        presenter.onViewCreated(bundle);
    }
    void mappping(){
        txtCompanyName=getActivity().findViewById(R.id.txtCompanyName);
        btnWebsite = getActivity().findViewById(R.id.btnWebsite);
        btnPhoneNumber=getActivity().findViewById(R.id.btnPhoneNumber);
        btnAddress=getActivity().findViewById(R.id.btnAddress);
        txtWebsite =getActivity().findViewById(R.id.txtWeblink);
        txtPhonenNumber = getActivity().findViewById(R.id.txtPhoneNumber);
        txtAddress = getActivity().findViewById(R.id.txtAddress);
    }

    void setBtnWebsiteClick(){
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonWebsiteClicked();
            }
        });
    }
    void setBtnPhoneNumberClick(){
        btnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonPhoneClicked();
            }
        });
    }
    void setBtnAddressClick(){
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonAddressClicked();
            }
        });
    }
    @Override
    public void showContact(Company company) {
        txtWebsite.setText(company.website);
        txtCompanyName.setText(company.companyName);
        txtPhonenNumber.setText(company.phoneNumber);
        txtAddress.setText(company.address);
    }

    @Override
    public void showContact(UserInformation user) {

        txtCompanyName.setText(user.name);
        txtPhonenNumber.setText(user.getSdt());
    }

    @Override
    public void notifyGetContactFailure(Exception ex) {
        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideBtnWebsite() {
        btnWebsite.setVisibility(View.GONE);
    }

    @Override
    public void hideBtnAddress() { btnAddress.setVisibility(View.GONE); }

    @Override
    public void hideBtnPhoneNumber() { btnPhoneNumber.setVisibility(View.GONE); }

    @Override
    public void gotoWebsite(Intent intent)
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET},REQUEST_CODE);
                return;
            }
        }
        startActivity(intent);
    }

    @Override
    public void gotoCall(Intent intent)
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE);
                return;
            }
        }
        startActivity(intent);
    }

    @Override
    public void gotoMap(Intent intent) {
        startActivity(intent);
    }
    @Override
    public Context getContext(){
        return getActivity();
    }
}
