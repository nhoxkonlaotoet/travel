package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Henry on 12/14/2018.
 */

public interface ChatSearchFriendListener {
    public void getListUserSearchListener(List<UserInformation> listUserSearch,List<String> UID);
    public void getFriendInvitationListener(List<UserInformation> listUserSearch,List<String> UID);
}
