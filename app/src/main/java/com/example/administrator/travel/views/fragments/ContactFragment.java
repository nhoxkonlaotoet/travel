package com.example.administrator.travel.views.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.ContactCompanyPresenter;
import com.example.administrator.travel.views.ContactCompanyView;
import com.example.administrator.travel.views.activities.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactCompanyView {
    final static Integer REQUEST_CODE = 99;
    ContactCompanyPresenter presenter;
    TextView txtWebsite, txtPhonenNumber, txtAddress, txtOwnerName;
    RelativeLayout btnWebsite, btnPhoneNumber, btnAddress;
    Company company;
    public ContactFragment() {
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
        presenter=new ContactCompanyPresenter(this);
        setBtnWebsiteClick();
        setBtnPhoneNumberClick();
        setBtnAddressClick();
        Load();
    }
    void mappping(){
        txtOwnerName=getActivity().findViewById(R.id.txtOwnerName);
        btnWebsite = getActivity().findViewById(R.id.btnWebsite);
        btnPhoneNumber=getActivity().findViewById(R.id.btnPhoneNumber);
        btnAddress=getActivity().findViewById(R.id.btnAddress);
        txtWebsite =getActivity().findViewById(R.id.txtWeblink);
        txtPhonenNumber = getActivity().findViewById(R.id.txtPhoneNumber);
        txtAddress = getActivity().findViewById(R.id.txtAddress);
    }
    void Load()
    {
        Bundle bundle =getActivity().getIntent().getExtras();
        String owner = bundle.getString("owner");
        Log.e( "onwer: ",owner );
        presenter.onViewLoad(owner);
    }
    void setBtnWebsiteClick(){
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnWebsiteClicked();
            }
        });
    }
    void setBtnPhoneNumberClick(){
        btnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnPhoneNumberClicked();
            }
        });
    }
    void setBtnAddressClick(){
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnAddressClicked();
            }
        });
    }
    @Override
    public void showContact(Company company) {
        txtOwnerName.setText(company.companyName);
        txtWebsite.setTag(company.website);
        txtPhonenNumber.setText(company.phoneNumber);
        txtAddress.setText(company.address);
        txtAddress.setTag(company.location.getLatLng());
    }

    @Override
    public void showContact(UserInformation user) {

        txtOwnerName.setText(user.name);
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
    public void gotoWebsite()
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txtWebsite.getTag().toString()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET},REQUEST_CODE);
                return;
            }
        }
        startActivity(browserIntent);
    }

    @Override
    public void gotoCall()
    {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE);
                return;
            }
        }
        String phonenumber= txtPhonenNumber.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==getActivity().RESULT_OK)
            Toast.makeText(getActivity(), "request success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoMap(LatLng location) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("action","nearby");
        String destination =location.latitude+","+location.longitude;
        intent.putExtra("destination", destination);
        startActivity(intent);
    }
}
