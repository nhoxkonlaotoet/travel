package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 22/12/2018.
 */

public class ContactCompanyInteractor {

    public ContactCompanyInteractor(){}
    public void getCompanyContact(final String userId, final OnGetCompanyContactFinishedListener listener)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference companiesRef = database.getReference("companies");
        companiesRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    DatabaseReference usersRef = database.getReference("users");
                    usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()==null)
                            {
                                listener.getCompanyContactFailure(new Exception("Không tìm được công ty/ chủ sở hữu tour"));
                            }
                            else
                            {
                                UserInformation user = dataSnapshot.getValue(UserInformation.class);
                                listener.getOnwerContactSuccess(user);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.getCompanyContactFailure(databaseError.toException());
                        }
                    });
                }
                else
                {
                    Company company = dataSnapshot.getValue(Company.class);
                    listener.getCompanyContactSuccess(company);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.getCompanyContactFailure(databaseError.toException());

            }
        });
    }

}
