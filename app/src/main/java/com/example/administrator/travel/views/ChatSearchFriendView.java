package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Henry on 12/14/2018.
 */

public interface ChatSearchFriendView {
    public void ShowListUserSearch(List<UserInformation> listUserSearch, List<String> UID);
    public void ShowFriendInvitation(List<UserInformation> listUserSearch, List<String> UID);
}
