
package com.example.administrator.travel.models.entities.place.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Period extends RealmObject {

    @SerializedName("close")
    @Expose
    public Close close;
    @SerializedName("open")
    @Expose
    public Open open;

}
