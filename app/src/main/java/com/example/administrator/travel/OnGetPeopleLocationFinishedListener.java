package com.example.administrator.travel;

import com.example.administrator.travel.models.entities.Participant;

import java.util.List;

/**
 * Created by Administrator on 03/01/2019.
 */

public interface OnGetPeopleLocationFinishedListener {
    void onGetPeopleLocationSuccess(List<Participant> lstParticipant );
    void onGetPeopleLocationFailure(Exception ex);
}
