package com.example.administrator.travel.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

/**
 * Created by Admin on 6/6/2019.
 */

public class TourBookingDetail implements Parcelable {
    public String touristName;
    public Integer dayOfBirth;
    public Integer monthOfBirth;
    public Integer yearOfBirth;
    public String touristEmail;
    public Boolean male;
    public Integer touristType; // 0:adult, 1:children, 2:baby
    public Integer price;

    public TourBookingDetail() {
    }

    public TourBookingDetail(String touristName, Integer dayOfBirth, Integer monthOfBirth, Integer yearOfBirth,
                             String touristEmail, Boolean male, Integer touristType, Integer price) {
        this.touristName = touristName;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        this.touristEmail = touristEmail;
        this.male = male;
        this.touristType = touristType;
        this.price = price;
    }


    protected TourBookingDetail(Parcel in) {
        touristName = in.readString();
        dayOfBirth = Integer.parseInt(in.readString());
        monthOfBirth = Integer.parseInt(in.readString());
        yearOfBirth = Integer.parseInt(in.readString());
        touristEmail = in.readString();
        male = Boolean.valueOf(in.readString());
        touristType = Integer.parseInt(in.readString());
        price = Integer.parseInt(in.readString());

    }

    public static final Creator<TourBookingDetail> CREATOR = new Creator<TourBookingDetail>() {
        @Override
        public TourBookingDetail createFromParcel(Parcel in) {
            return new TourBookingDetail(in);
        }

        @Override
        public TourBookingDetail[] newArray(int size) {
            return new TourBookingDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(touristName);
        parcel.writeString(String.valueOf(dayOfBirth));
        parcel.writeString(String.valueOf(monthOfBirth));
        parcel.writeString(String.valueOf(yearOfBirth));
        parcel.writeString(touristEmail);
        parcel.writeString(String.valueOf(male));
        parcel.writeString(String.valueOf(touristType));
        parcel.writeString(String.valueOf(price));
    }
}
