
package com.example.administrator.travel.models.entities.place.detail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class OpeningHours extends RealmObject {

    @SerializedName("open_now")
    @Expose
    public Boolean openNow;
    @SerializedName("periods")
    @Expose
    public RealmList<Period> periods = null;
    @SerializedName("weekday_text")
    @Expose
    public RealmList<String> weekdayText = null;

}
