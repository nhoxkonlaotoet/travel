package com.example.administrator.travel.models.entities;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by Administrator on 30/10/2018.
 */
@IgnoreExtraProperties
public class Tour {
    public String id;
    public String name;
    public String description;
    public Boolean state;
    public Long adultCost;
    public Long childCost;
    public Long babyCost;
    public Tour(){}
    public Tour(String name, String description, Boolean state, Long adultCost, Long childCost, Long babyCost)
    {
        this.name=name;
        this.description=description;
        this.state=state;
        this.adultCost=adultCost;
        this.childCost=childCost;
        this.babyCost=babyCost;
    }
}
