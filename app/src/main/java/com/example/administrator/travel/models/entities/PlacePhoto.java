package com.example.administrator.travel.models.entities;

/**
 * Created by Admin on 5/9/2019.
 */

public class PlacePhoto {
    public int width;
    public int height;
    public String photoRef;
    public String[] htmlAttributions;
    public PlacePhoto(){

    }
    public PlacePhoto(String photoRef, int width, int height, String[] htmlAttributions) {
        this.photoRef = photoRef;
        this.width = width;
        this.height = height;
        this.htmlAttributions = htmlAttributions;
    }
}
