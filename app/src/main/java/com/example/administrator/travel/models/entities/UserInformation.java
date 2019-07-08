package com.example.administrator.travel.models.entities;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

/**
 * Created by Henry on 12/10/2018.
 */

public class UserInformation {
    public String id;
    public String name;
    public Boolean isMale;
    public String mail;
    public String sdt;
    public String urlAvatar;
    public Bitmap avatar;
    public Integer dateOfBirth;
    public Integer monthOfBirth;
    public Integer yearOfBirth;

    public UserInformation() {

    }

    public UserInformation(String name, Boolean isMale, String mail, String sdt, String urlAvatar,
                           Integer dateOfBirth, Integer monthOfBirth, Integer yearOfBirth) {
        this.name = name;
        this.isMale = isMale;
        this.mail = mail;
        this.sdt = sdt;
        this.urlAvatar = urlAvatar;
        this.dateOfBirth = dateOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
    }

    public UserInformation(String name, String sex, String sdt) {
        this.name = name;
        this.isMale = isMale;
        this.sdt = sdt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public String getSex() {
        return isMale ? "Nam" : "Nữ";
    }

    public void setSex(String sex) {
        this.isMale = isMale;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        if (sdt.trim().equals(""))
            this.sdt = "none";
        else
            this.sdt = sdt;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
}
