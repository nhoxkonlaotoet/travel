package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.Route;

import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public interface OnFindDirectionFinishListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
