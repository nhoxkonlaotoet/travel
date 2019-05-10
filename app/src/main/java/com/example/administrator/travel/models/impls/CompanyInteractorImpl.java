package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Admin on 4/3/2019.
 */

public class CompanyInteractorImpl implements CompanyInteractor {
    private final static String COMPANIES_REF="companies";
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
    public void getCompanyContact(String companyId, final Listener.OnGetCompanyContactFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference companiesRef = database.getReference(COMPANIES_REF);
        companiesRef.child(companyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    Company company = dataSnapshot.getValue(Company.class);
                    listener.onGetCompanyContactSuccess(company);
                }
                else
                    listener.onGetCompanyContactFail(new Exception("Không tồn tại công ty"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetCompanyContactFail(databaseError.toException());

            }
        });
    }

}
