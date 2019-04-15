package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.bases.CompanyContactInteractor;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Admin on 4/11/2019.
 */

public class CompanyContactInteractorImpl implements CompanyContactInteractor {
    @Override
    public void getCompanyContact(final String companyId, final Listener.OnGetCompanyContactFinishedListener listener)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference companiesRef = database.getReference("companies");
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