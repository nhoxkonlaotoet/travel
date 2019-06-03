package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/3/2019.
 */

public class CompanyInteractorImpl implements CompanyInteractor {
    private final static String COMPANIES_REF="companies";
    @Override
    public void getCompanies(final Listener.OnGetCompaniesFinishedListener listener){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference companiesRef = database.getReference(COMPANIES_REF);
        companiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Company> companyList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Company company = new Company();
                    company.id=snapshot.getKey();
                    company.address = snapshot.child("address").getValue(String.class);
                    company.companyName = snapshot.child("companyName").getValue(String.class);
                    company.shortName = snapshot.child("shortName").getValue(String.class);
                    company.location = snapshot.child("location").getValue(MyLatLng.class);
                    company.phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                    company.website = snapshot.child("phoneNumber").getValue(String.class);
                    companyList.add(company);
                }
                listener.onGetCompaniesSuccess(companyList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetCompaniesFail(databaseError.toException());
            }
        });
    }

    @Override
    public void checkIsCompany(String userId, final Listener.OnCheckIsCompanyFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference companiesRef = database.getReference(COMPANIES_REF);
        companiesRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                    listener.onCheckIsCompanySuccess(true);
                else
                    listener.onCheckIsCompanySuccess(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCheckIsCompanyFail(databaseError.toException());
            }
        });
    }

    @Override
    public boolean isCompany(String userId, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        boolean isCompany=prefs.getBoolean("isCompany"+userId,false);
        return isCompany;
    }

    @Override
    public void setIsCompany(String userId, boolean isCompany, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isCompany"+userId,isCompany);
        editor.apply();
    }

    @Override
    public void getCompany(String companyId, final Listener.OnGetCompanyFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference companiesRef = database.getReference(COMPANIES_REF);
        companiesRef.child(companyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    Company company = dataSnapshot.getValue(Company.class);
                    listener.onGetCompanySuccess(company);
                }
                else
                    listener.onGetCompanyFail(new Exception("Không tồn tại công ty"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetCompanyFail(databaseError.toException());

            }
        });
    }

    @Override
    public void getCompanyPhoto(final String companyId, final Listener.OnGetCompanyLogoFinishedListener listener) {
        final long HALF_MEGABYTE = 1024 * 512;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference companiesRef = storage.getReference().child("companies/");
        companiesRef.child(companyId + ".jpg").getBytes(HALF_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetCompanyLogoSuccess(companyId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}
