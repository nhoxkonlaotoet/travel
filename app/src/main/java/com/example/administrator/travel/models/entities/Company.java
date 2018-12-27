package com.example.administrator.travel.models.entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Administrator on 22/12/2018.
 */
@IgnoreExtraProperties
public class Company {
    public String id;
    public String companyName;
    public String phoneNumber;
    public String website;
    public MyLatLng location;
    public String address;
    public Company(){}

    public Company(String companyName, String phoneNumber, String website, MyLatLng location,String address) {
        this.id = id;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.location = location;
        this.address=address;
    }


}
