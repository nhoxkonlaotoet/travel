
package com.example.administrator.travel.models.entities.place.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Review extends RealmObject {

    @SerializedName("author_name")
    @Expose
    public String authorName;
    @SerializedName("author_url")
    @Expose
    public String authorUrl;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("profile_photo_url")
    @Expose
    public String profilePhotoUrl;
    @SerializedName("rating")
    @Expose
    public Integer rating;
    @SerializedName("relative_time_description")
    @Expose
    public String relativeTimeDescription;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("time")
    @Expose
    public Integer time;

}
